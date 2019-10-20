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
	
	public boolean hasLetter(char l) {
		return current.getChildren().containsKey(l);
	}
	
	public boolean goToLetter (char l) {
		length += 1;
		current = current.getChildren().get(l);
		return isWord();
	}
	
	public boolean isWord () {
		return current.isEnd();
	}
	
	public void goBackLetter() {
		length -= 1;
		current = current.getParent();
	}
	
	public boolean isRoot() {
		return current == root;
	}
	
	public byte getLength() {
		return length;
	}
	public ArrayList<Character> getWord(){
		ArrayList<Character> word = new ArrayList<>();
		while(!isRoot()) {
			word.add(current.getletter());
		}
		word.trimToSize();
		return word;
	}
}
