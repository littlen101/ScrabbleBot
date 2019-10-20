
public class Tile implements Comparable<Tile>{
	char character;
	byte charCount;
	byte compare;
	byte usedTile;
	
	public Tile (char ch, byte cmp) {
		character = ch;
		charCount = 1;
		usedTile = 0;
		compare = cmp;
	}
	
	public static byte getValue(char l) {
		byte value = 0;
		if((l == 'Q') || (l == 'Z')) {
			value = 10;
		} else if ((l == 'J') || (l == 'X')) {
			value = 8;
		} else if (l == 'K') {
			value = 5;
		} else if ((l == 'F') || (l == 'H') || (l == 'V') || (l == 'W') || (l == 'Y')) {
			value = 4;
		} else if ((l == 'B') || (l == 'C') || (l == 'M') || (l == 'P')) {
			value = 3;
		} else if ((l == 'D') || (l == 'G')) {
			value = 2;
		} else if ((l == ' ') || (l == '_')){
			//Do Nothing value is already zero
		}else {
			value = 1;
		}
		
		return value;
	}

	public boolean hasTiles() {
		return usedTile < charCount;
	}
	
	public void useCharacter () {
		usedTile += 1;
	}
	@Override
	public int compareTo(Tile other) {
		return other.compare - compare;
	}
}
