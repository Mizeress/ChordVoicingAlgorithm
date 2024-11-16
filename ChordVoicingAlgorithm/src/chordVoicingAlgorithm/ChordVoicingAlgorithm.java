package chordVoicingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chordVoicingAlgorithm.guitar.Fretboard;

public class ChordVoicingAlgorithm {

	String[] chord;
	
	Fretboard fretboard;
	
	int maxDistance; 
	
	public List<List<List<Integer>>> voicings;
	
	/**
	 * Searches for voicings of chord on given fretboard.
	 * @param chord A string array (max length 6) representing the chord you wish to find voicings of. E.G the standard open A Maj voicing = {"A", "E", "A", "C#", "E"}
	 * @param fretboard The fretboard the algorithm searches
	 * @param maxDistance The number of frets apart a note in a chord can be
	 */
	public ChordVoicingAlgorithm(String[] chord, Fretboard fretboard, int maxDistance) {
		this.chord = chord;
		this.fretboard = fretboard;
		this.maxDistance = maxDistance; 
		
		voicings = new ArrayList<>();
		
		findVoicings(chord, fretboard, maxDistance);
	}
	
	 
	void findVoicings(String[] chord, Fretboard fretboard, int maxDistance) {
		List<List<List<Integer>>> noteOccurences = new ArrayList<>();
		
		for(String note : chord) {
			noteOccurences.add(fretboard.findNoteOccurences(note));
		}
		
		
		recursiveFindVoicing(noteOccurences);
		
	}
	
	//Used to track the note stack during recursion
	class ChordVoicingRecursion {
		List<Integer> note;
		ChordVoicingRecursion previous;
		
		public ChordVoicingRecursion(List<Integer> note) {
			this.note = note;
			previous = null;
		}
		
		public ChordVoicingRecursion(List<Integer> note, ChordVoicingRecursion previous) {
			this.note = note;
			this.previous = previous;
		}
		
		
	}
	
	/**
	 * The base case of a recursive method to find chord voicings
	 * @param noteOccurences All occurences of the notes in the desired chord
	 */
	private void recursiveFindVoicing(List<List<List<Integer>>> noteOccurences) {
		int depth = 0;
		
		for(List<Integer> note : noteOccurences.get(depth)) {
			ChordVoicingRecursion cvr = new ChordVoicingRecursion(note);
			
			recursiveFindVoicing(noteOccurences, cvr, depth + 1);
			
		}
		
	}
	
	/**
	 * A recursive method to find chord voicings
	 * @param noteOccurences All occurences of the notes that are contained in the desired chord
	 * @param previous The previous note in the recursion stack
	 * @param depth What level of recursion the method is at
	 */
	private void recursiveFindVoicing(List<List<List<Integer>>> noteOccurences, ChordVoicingRecursion previous, int depth) {

		for(List<Integer> note : noteOccurences.get(depth)) {
			ChordVoicingRecursion cvr = new ChordVoicingRecursion(note, previous);
			if(depth < chord.length - 1) {
				recursiveFindVoicing(noteOccurences, cvr, depth + 1);
			} else {
				List<List<Integer>> chordVoicing = new ArrayList<>();
				while(cvr != null) {
					chordVoicing.add(cvr.note);
					cvr = cvr.previous;
				}
				
				if(isValidVoicing(chordVoicing, maxDistance)) {
					voicings.add(chordVoicing);
				}
				
			}
		}
		
	}
	
	/**
	 * Determines if a voicing is valid based on given parameters
	 * @param notes The notes in the voicing
	 * @param maxDistance The maximum distance between notes that is considered valid
	 * @return Whether the voicing is valid
	 */
	private static boolean isValidVoicing(List<List<Integer>> notes, int maxDistance) {
		if(difStrings(notes) && maxStretch(notes) <= maxDistance) {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Determine if all notes in notes are on different strings
	 * @param notes List of notes
	 * @return whether all notes are on different strings
	 */
	private static boolean difStrings(List<List<Integer>> notes) {		
		Map<Integer, Integer> noteNonDuplicate = new HashMap<>();
		
		for(List<Integer> note : notes) {
			noteNonDuplicate.put(note.get(0), note.get(1));
		}
		
		return noteNonDuplicate.size() == notes.size();
		
	}
	
	/**
	 * Find the maximum number of frets between a group of notes
	 * @param notes
	 * @return
	 */
	private static int maxStretch(List<List<Integer>> notes) {
		List<Integer> fretNums = new ArrayList<>();
		
		for(List<Integer> note : notes) {
			fretNums.add(note.get(1));
		}
				
		int max = Collections.max(fretNums);
		
		int min = Collections.min(fretNums);
		
		return Math.abs(max - min);
	}
	
	
}
