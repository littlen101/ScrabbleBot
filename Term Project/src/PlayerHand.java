import java.util.HashMap;

public class PlayerHand {
	BoardWord initialWord;
	HashMap<Character, Tile> hand;
	byte wild;
	private byte counter = 0;
	
	public PlayerHand (BoardWord iword, HashMap<Character, Tile> ha, byte wcount) {
		initialWord = iword;
		hand = ha;
		wild = wcount;
	}
	
	public boolean hasWild () {return counter < wild;}
	public void useWild() {counter = (byte) (counter + 1);}
}
