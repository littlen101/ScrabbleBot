import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class WordGenerator {
	private byte length;
	private Node root;
	private Node current;
	private Trie dictionary;
	
	public WordGenerator(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		dictionary = (Trie) ois.readObject();
		root = dictionary.getRoot();
		current = root;
		length = 0;
	}

	public WordGenerator(String file) {
		dictionary = new Trie(file);
		root = dictionary.getRoot();
		current = root;
	}
	
	public boolean hasParent() {
		return current.getParent() != null;
	}
	
	public boolean hasLetter(char l) {
		return current.getChildren().containsKey(l);
	}
	
	public boolean goToLetter (char l) {
		length += 1;
		current = current.getChildren().get(l);
		return isWord();
	}
	
	public boolean isWord () {
		return (current.isEnd() && length > 1);
	}
	
	public char goBackLetter() {
		length -= 1;
		char value = current.getletter();
		current = current.getParent();
		return value;
	}
	
	public char useRandomLetter() {
		char letter = '-';
		for(Character c : current.getChildren().keySet()) {
			if((letter == '-') || (current.getChildren().get(c).getChildren().size()) >
					(current.getChildren().get(letter).getChildren().size()))
				letter = c;
		}
		if(hasLetter(letter)) goToLetter(letter);
		return letter;
	}
	
	public boolean wordContains(char c) {
		Node trav = current;
		while(trav != null) {
			if(trav.getletter() == c) return true;
			trav = trav.getParent();
		}
		return false;
	}
	
	public boolean isRoot() {
		return current == root;
	}
	
	public byte getLength() {
		return length;
	}
	
	public void reset () {
		current = root;
		length = 0;
	}

	public ArrayList<Character> getWord(){
		ArrayList<Character> word = new ArrayList<>();
		Node traverse = current;
		while(traverse != null) {
			word.add(traverse.getletter());
			traverse = traverse.getParent();
		}
		word.trimToSize();
		return word;
	}
}
