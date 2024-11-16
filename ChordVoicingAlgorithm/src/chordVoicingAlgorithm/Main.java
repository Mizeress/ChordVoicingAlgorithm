package chordVoicingAlgorithm;

import java.util.List;

import chordVoicingAlgorithm.guitar.ChordVoicingAlgorithm;
import chordVoicingAlgorithm.guitar.Fretboard;

public class Main {

	public static void main(String[] args) {
		
		Fretboard fretboard = new Fretboard(24);
		
		System.out.println(fretboard);
		
		List<List<Integer>> aOccurences = fretboard.findNoteOccurences("A");
		
		System.out.println("A Occurences");
		displayNoteOccurences(aOccurences);
		
		ChordVoicingAlgorithm cva = new ChordVoicingAlgorithm(new String[] {"A", "E", "C#"}, fretboard, 5);
		
		displayVoicings(cva.voicings);
		

	}
	
	private static void displayVoicings(List<List<List<Integer>>> voicings) {
		for(List<List<Integer>> chord : voicings) {
			System.out.print("Chord: ");
			for(List<Integer> note : chord) {
				System.out.print(note + " ");
			}
			System.out.println();
		}
	}
	
	
	private static void displayNoteOccurences(List<List<Integer>> occurences) {
		for(List<Integer> note : occurences) {
			System.out.println("String: " + note.get(0) + " Fret: " + note.get(1));
		}
	}

}
