package chordVoicingAlgorithm.guitar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A data representation of an instruments fretboard
 */
public class Fretboard {
	private int numFrets;
	private String[] tuning;
	
	private final static String[] notes = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
	
	private String[][] fretboard; 
	
	
	/**
	 * @param numFrets the number of frets your guitar has
	 * @param String Array representing the tuning of your guitar. E.G standard = {"E", "A", "D", "G", "B", "E"}
	 */
	public Fretboard(int numFrets, String[] tuning) {
		this.setNumFrets(numFrets);
		
		this.tuning = tuning;
		
		this.tuning = new String[] {"E", "A", "D", "G", "B", "E"};
		
		
		fretboard = populateFretboard(tuning, numFrets);
		
	}
	
	/**
	 * Creates a standard tuning fretboard with specified number of frets
	 * @param numFrets Number of frets your guitar has
	 */
	public Fretboard(int numFrets) {
		this.setNumFrets(numFrets);
		
		this.tuning = new String[] {"E", "A", "D", "G", "B", "E"};
		
		fretboard = populateFretboard(tuning, numFrets);
		
	}
	

	private static String[][] populateFretboard(String[] tuning, int numFrets) {
		String[][] fretboard = new String[6][numFrets];
		
		int currentIndex = -1;
		
		for (int i = 0; i < 6; i++) {
			currentIndex = Arrays.binarySearch(notes, tuning[i]);
			for (int j = 0; j < numFrets; j++) {
				fretboard[i][j] = notes[currentIndex];
				
				currentIndex++;
				
				if(currentIndex >= notes.length) {
					currentIndex = 0;
				}
			}
		}
		
		return fretboard;
	}
	
	/**
	 * Find all occurences of note on the fretboard
	 * @param note The note to search for
	 * @return a list of all notes found in the form of {stringIndex, fretNumber}
	 */
	public List<FretNote> findNoteOccurences(String note) {
		
		List<FretNote> foundNotes = new ArrayList<>();
		
		for(int i = 0; i < fretboard.length; i++) {
			for (int j = 0; j < fretboard[i].length; j++) {
				if(fretboard[i][j].equals(note)) {
					foundNotes.add(new FretNote(i, j));
				}
			}
		}
		
		return foundNotes;
	}
	
	/**
	 * Determine the number of frets between two notes
	 * @param note1
	 * @param note2
	 * @return distance between note1 and note2
	 */
	public static int noteDistance(int[] note1, int[] note2) {
		return Math.abs(note1[1] - note2[1]);
	}
	
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		
		for (int i = 0; i < fretboard.length; i++) {
			for (int j = 0; j < fretboard[i].length; j++) {
				string.append(fretboard[i][j]);
			}
			string.append("\n");
		}
		
		return string.toString();
	}

	public int getNumFrets() {
		return numFrets;
	}

	public void setNumFrets(int numFrets) {
		this.numFrets = numFrets;
	}
	
	public String[] getTuning() {
		return tuning;
	}

	public void setTuning(String[] tuning) {
		this.tuning = tuning;
	}

	public String[][] getFretboard() {
		return fretboard;
	}

	public void setFretboard(String[][] fretboard) {
		this.fretboard = fretboard;
	}

	
}
