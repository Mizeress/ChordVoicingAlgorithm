package chordVoicingAlgorithm;

import java.util.List;

import chordVoicingAlgorithm.guitar.Chord;
import chordVoicingAlgorithm.guitar.Fretboard;
import chordVoicingAlgorithm.guitar.FretNote;

public class Main {

	public static void main(String[] args) {
		
		Fretboard fretboard = new Fretboard(24);
		
		System.out.println(fretboard);
		
		ChordVoicingAlgorithm cva = new ChordVoicingAlgorithm(new String[] {"A", "E"}, fretboard, 3);
		
		System.out.println();
		
		displayVoicings(cva.voicings);
		

	}
	
	private static void displayVoicings(List<Chord> voicings) {
		
		System.out.println("Found " + voicings.size() + " voicings");
		
		System.out.println();
		
		for(Chord chord : voicings) {
			System.out.print("Chord: ");
			for(FretNote note : chord.getChord()) {
				System.out.print(note + " ");
			}
			System.out.println();
		}
	}
	
	
	private static void displayNoteOccurences(List<FretNote> aOccurences) {
		for(FretNote note : aOccurences) {
			System.out.println("String: " + note.getStringNum() + " Fret: " + note.getFretNum());
		}
	}

}
