import java.util.List;


public class BoardWord {
	byte startX;
	byte startY;
	//(size)(Q 1,2,3,4)
	byte length;
	//(H = 1; V = 2;) (Q 1,2,3,4)
	byte direction;
	char[] word;
	
	public BoardWord (List<Character> list, byte x, byte y, byte len, byte di) {
		word = new char[list.size()];
		for (int i = 0; i < list.size(); i++) word[i] = list.get(i);
		
		startX = x;
		startY = y;
		length = len;
		direction = di;
	}
	
	public byte getStartingQuadrant() {
		return (byte) (direction % 10);
	}
	
	public byte getEndingQuadrant () {
		return (byte) (length % 10);
	}
	
	public byte getStartX () {
		return startX;
	}
	
	public byte getStartY () {
		return startY;
	}
	
	public byte getPosition(char c) {
		for(byte i = 0; i < word.length; i += 1) {
			if (c == word[i]) return i;
		}
		return 0;
	}
	
	public byte getDirection () {
		return (byte) (direction/10);
	}
	public byte getLength() {
		return (byte) (length / 10);
	}
	
	public boolean onBoarder () {
		return ((getStartingQuadrant()) == (getEndingQuadrant()));
	}
}
