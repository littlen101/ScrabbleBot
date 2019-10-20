import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WordGeneratorTest {
	public static void main(String[] args) throws FileNotFoundException {
		WordGenerator generator = new WordGenerator("./legalWords.txt");
		Scanner stdin = new Scanner(System.in);
		while(stdin.hasNextLine()) {
			char letter = stdin.nextLine().toUpperCase().charAt(0);
			if(generator.hasLetter(letter))
				generator.goToLetter(letter);
			if(generator.isWord()) {
				ArrayList<Character> word = new ArrayList<>();
				word = generator.getWord();
				for(Character c : word) System.out.println(c);
				System.out.println(word.size());
				word.set(word.size() - 1, (char) 78);
				char valC = word.remove(word.size() - 1);
				byte val = (byte) valC;
				Collections.reverse(word);
				System.out.println(word.stream().map(i -> i.toString()).collect(Collectors.joining()));
				System.out.println(word.size());
				System.out.println(val);
			}
		}
		stdin.close();
	}
}
