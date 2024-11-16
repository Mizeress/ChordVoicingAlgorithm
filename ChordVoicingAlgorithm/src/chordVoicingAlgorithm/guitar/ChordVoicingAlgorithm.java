package chordVoicingAlgorithm.guitar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		voicings = findVoicings(chord, fretboard, maxDistance);
	}
	
	 
	static List<List<List<Integer>>> findVoicings(String[] chord, Fretboard fretboard, int maxDistance) {
		List<List<List<Integer>>> noteOccurences = new ArrayList<>();
		
		for(String note : chord) {
			noteOccurences.add(fretboard.findNoteOccurences(note));
		}
		
		
		
		List<List<List<Integer>>> voicings = new ArrayList<>();
		
		//Loop through each note type
		for(List<Integer> note1 : noteOccurences.get(0)) {
			
			for(List<Integer> note2 : noteOccurences.get(1)) {
				
				for(List<Integer> note3 : noteOccurences.get(2)) {
					
					if(difStrings(Arrays.asList(note1, note2, note3)) && maxStretch(Arrays.asList(note1, note2, note3)) <= maxDistance) {
						voicings.add(Arrays.asList(note1, note2, note3));
					}
				}
				
				
			}
			
		}
		
		return voicings; 
		
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
	
	
	//TODO write a recursive method to find a chord

	
}
