package chordVoicingAlgorithm.guitar;

import java.util.List;

public class Chord {
	private List<FretNote> chord;
	
	public Chord(List<FretNote> notes) {
		chord = notes;
	}
	
	/**
	 * 
	 * @return A fretNote list representation of the chord
	 */
	public List<FretNote> getChord() {
		return chord;
	}
}
