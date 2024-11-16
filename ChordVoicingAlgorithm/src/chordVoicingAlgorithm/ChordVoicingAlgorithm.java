package chordVoicingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chordVoicingAlgorithm.guitar.Chord;
import chordVoicingAlgorithm.guitar.Fretboard;
import chordVoicingAlgorithm.guitar.FretNote;

public class ChordVoicingAlgorithm {

	String[] chord;
	
	Fretboard fretboard;
	
	int maxDistance; 
	
	public List<Chord> voicings;
	
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
		List<List<FretNote>> noteOccurences = new ArrayList<>();
		
		for(String note : chord) {
			noteOccurences.add(fretboard.findNoteOccurences(note));
		}
		
		
		recursiveFindVoicing(noteOccurences);
		
	}
	
	//Used to track the note stack during recursion
	private class ChordVoicingRecursion {
		FretNote note;
		ChordVoicingRecursion previous;
		
		public ChordVoicingRecursion(FretNote note) {
			this.note = note;
			previous = null;
		}
		
		public ChordVoicingRecursion(FretNote note, ChordVoicingRecursion previous) {
			this.note = note;
			this.previous = previous;
		}
	}
	
	/**
	 * The base case of a recursive method to find chord voicings
	 * @param noteOccurences All occurences of the notes in the desired chord
	 */
	private void recursiveFindVoicing(List<List<FretNote>> noteOccurences) {
		int depth = 0;
		
		for(FretNote note : noteOccurences.get(depth)) {
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
	private void recursiveFindVoicing(List<List<FretNote>> noteOccurences, ChordVoicingRecursion previous, int depth) {

		for(FretNote note : noteOccurences.get(depth)) {
			ChordVoicingRecursion cvr = new ChordVoicingRecursion(note, previous);
			if(depth < chord.length - 1) {
				recursiveFindVoicing(noteOccurences, cvr, depth + 1);
			} else {
				List<FretNote> chordVoicing = new ArrayList<>();
				while(cvr != null) {
					chordVoicing.add(cvr.note);
					cvr = cvr.previous;
				}
				
				Chord chord = new Chord(chordVoicing);
				
				if(isValidVoicing(chord, maxDistance)) {
					voicings.add(chord);
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
	private static boolean isValidVoicing(Chord notes, int maxDistance) {
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
	private static boolean difStrings(Chord notes) {		
		Map<Integer, Integer> noteNonDuplicate = new HashMap<>();
		
		for(FretNote note : notes.getChord()) {
			noteNonDuplicate.put(note.getStringNum(), note.getFretNum());
		}
		
		return noteNonDuplicate.size() == notes.getChord().size();
		
	}
	
	/**
	 * Find the maximum number of frets between a group of notes
	 * @param notes
	 * @return
	 */
	private static int maxStretch(Chord notes) {
		List<Integer> fretNums = new ArrayList<>();
		
		for(FretNote note : notes.getChord()) {
			fretNums.add(note.getFretNum());
		}
				
		int max = Collections.max(fretNums);
		
		int min = Collections.min(fretNums);
		
		return Math.abs(max - min);
	}
	
	
}
