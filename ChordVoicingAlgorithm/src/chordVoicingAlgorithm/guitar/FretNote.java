package chordVoicingAlgorithm.guitar;

import java.util.Arrays;
import java.util.List;

public class FretNote {
	private List<Integer> note;
	
	public FretNote(int stringNum, int fretNum) {
		note = Arrays.asList(stringNum, fretNum);
	}
	
	public List<Integer> getNote() {
		return note;
	}
	
	public int getStringNum() {
		return note.get(0);
	}
	
	public int getFretNum() {
		return note.get(1);
	}
	
	@Override
	public String toString() {
		return "[" + getStringNum() + ", " + getFretNum() + "]";
	}
}
