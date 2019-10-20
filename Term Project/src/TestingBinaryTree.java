import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;


public class TestingBinaryTree {
	public static void main(String[] args) throws IOException {
		IntegerBinaryTree<Character> tree = new IntegerBinaryTree<>(10,'N');
		IntegerBinaryTree<Character> second = new IntegerBinaryTree<>(5,'i');
		System.out.println(tree);
		System.out.println(second);
		IntegerBinaryTree<Character> joint = tree.join(second);
		
		System.out.println(joint);
		
		@SuppressWarnings("rawtypes")
		ArrayList breadthTree = (ArrayList) joint.breadthfirst();
		for(int i = 0; i < breadthTree.size(); i++) {
			System.out.println(breadthTree.get(i));
		}
		
		byte test = (byte) 0B010;
		System.out.println(test);
		 test = (byte) (test << 1); 
		 System.out.println(Integer.toBinaryString(test));
		 test += 0B1;
		 System.out.println(Integer.toBinaryString(test));
		 test += 0B1;
		 System.out.println(Integer.toBinaryString(test));
		 
		 BitSet bi = new BitSet();
		 bi.set(1);
		 bi.set(4);
		 byte[] testArr = bi.toByteArray();
		 System.out.println(Arrays.toString(testArr));
		 System.out.println(bi.length());
		 bi.clear(4);
		 System.out.println(bi.length());
		 
		 char[] t = {'a', 'a', 'a', 'a',};
		 
		 System.out.println(String.valueOf(t));
		 
		 //Important ----------
		 HoffmanEncoding enigma = new HoffmanEncoding("./sample.txt");
		 

		 boolean[] result = enigma.encode("gavin");
		 System.out.println(Arrays.toString(result));
		 try {
			System.out.println(enigma.decode(result));
		} catch (NoTreeException e) {
			e.printStackTrace();
		}
		
		//Important --------------------
		System.out.println("Serializing now");
		FileOutputStream fos = new FileOutputStream("./hoffmantree.src");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(enigma);
		oos.close();
		System.out.println("Process complete");
	}
}
