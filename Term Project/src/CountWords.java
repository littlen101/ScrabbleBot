import java.util.Scanner;

public class CountWords {
	public static int getWordCount(Scanner fl) {
		int count = 0;
		while(fl.hasNext()) {
			fl.next();
			count++;
		}
		
		return count;
	}
}
