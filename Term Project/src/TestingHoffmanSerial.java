import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

public class TestingHoffmanSerial {
	public static void main(String[] args) throws IOException, ClassNotFoundException, NoTreeException {
		FileInputStream fis = new FileInputStream("./hoffmantree.src");
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		HoffmanEncoding enigma = (HoffmanEncoding) ois.readObject();
		boolean[] result = enigma.encode("the");
		System.out.println(Arrays.toString(result));
		System.out.println(enigma.decode(result));
		
		ois.close();
	}
}
