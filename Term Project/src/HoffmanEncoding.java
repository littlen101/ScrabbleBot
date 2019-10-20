import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HoffmanEncoding extends IntegerBinaryTree<Character> {

	/**
	 * Serial version ID to ensure that the imported serialized object is the correct one 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<boolean[]> encodingTable;
	
	public static void createEncodingTable (ArrayList<Boolean> path,  Node<Integer,Character> current,
																		ArrayList<boolean[]> encodingTable){
		if (current.value != null){
			boolean[] capture = new boolean[path.size()];
			for(int i = 0; i < path.size(); i++) capture[i] = path.get(i);
			encodingTable.set((current.value - 'A'), capture);
		}
		
		if (current.hasLeft())  {path.add(false); 
								createEncodingTable (path, current.leftChild, encodingTable);
								 path.remove(path.size()-1);}
		if (current.hasRight()) {path.add(true); 
								createEncodingTable (path, current.rightChild, encodingTable);
								path.remove(path.size()-1);}
	}

	public static int[] generateFrequencyTable (String filePath) throws FileNotFoundException {
		Scanner file = new Scanner(new File(filePath));
		int[] frequency = new int [26];
		file.useDelimiter("");

		while(file.hasNext()) {
			char in = (char) (file.next().toUpperCase().charAt(0));
			if (in < 'A' || in > 'Z') continue;
			frequency[in - 'A']++;
		}
		
		file.close();
		return frequency;
	}


	public HoffmanEncoding (String filePath) throws FileNotFoundException {
		super();

		int[] frequnencyTable = generateFrequencyTable(filePath);
		PriorityQueue<IntegerBinaryTree<Character>> queue = new PriorityQueue<>();
			
		for(int i = 0; i < frequnencyTable.length; i++) {
			queue.add(new IntegerBinaryTree<Character>(frequnencyTable[i], (char) (i + 'A')));
		}
		
		while(queue.size() > 1) {
			IntegerBinaryTree<Character> first = queue.poll();
			IntegerBinaryTree<Character> second = queue.poll();
			queue.add(first.join(second));
		}
		
		this.setRoot(queue.poll().root);
		encodingTable = new ArrayList<boolean[]>(26);
		while(encodingTable.size() < 26) encodingTable.add(null);
		createEncodingTable(new ArrayList<Boolean>(), this.root, encodingTable);
					
	}

	public boolean[] encode (String str) {
		String upStr = str.toUpperCase();
		ArrayList<Boolean> cap = new ArrayList<>();
		for(int i = 0; i < str.length(); i++)
			for(boolean b : encodingTable.get(upStr.charAt(i) - 'A')) cap.add(b);

		boolean[] capture = new boolean[cap.size()];
		for (int i = 0; i < cap.size(); i++) capture[i] = cap.get(i);
		return capture;
	}
	
	public boolean[] encode (char c) {
		return encodingTable.get((int) (c - 'A'));
	}
	
	public String decode (boolean[] code) throws NoTreeException {
		ArrayList<Character> cap = new ArrayList<>();
		Node<Integer, Character> current = root;
		if (current.value != null) throw new NoTreeException();
		for (boolean bool : code) {
			current = (bool) ? current.rightChild: current.leftChild;
			if (current.isLeaf()) {cap.add(current.value); current = root;}
		}
		
		return  cap.stream().map(e->e.toString()).collect(Collectors.joining());
	}
	
}
