import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class DictHashCode {
	public static final HashMap<Character, Integer> ltov = new HashMap<Character, Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(' ', 0);
			put('A', 1);
			put('B', 3);
			put('C', 3);
			put('D', 2);
			put('E', 1);
			put('F', 4);
			put('G', 2);
			put('H', 4);
			put('I', 1);
			put('J', 8);
			put('K', 5);
			put('L', 1);
			put('M', 3);
			put('N', 1);
			put('O', 1);
			put('P', 3);
			put('Q', 10);
			put('R', 1);
			put('S', 1);
			put('T', 1);
			put('U', 1);
			put('V', 4);
			put('W', 4);
			put('X', 8);
			put('Y', 4);
			put('Z', 10);
			
		}
	};
	
	public static int wordValue(char[] str) {
		int sum = 0;
		for (char c : str) {
			sum += ltov.get(c);
		}
		return sum;
	}
	
	public static int highestLetter(char[] word) {
		int max = Integer.MIN_VALUE;
		
		for(char l : word) {
			max = Math.max(max, ltov.get(l));
		}
		
		return max;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner fl = new Scanner(new File(args[0]));
		//int size = CountWords.getWordCount(fl);
		fl = new Scanner(new File(args[0]));

		while(fl.hasNext()) {
			String str = fl.next();
			String copy = str.toUpperCase();
			System.out.printf("%s, %c, %d, %d%n", str, str.charAt(0), wordValue(copy.toCharArray()), highestLetter(copy.toCharArray()));
		}
		
		fl.close();
	}
}
