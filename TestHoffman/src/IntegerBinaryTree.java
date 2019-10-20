
import java.util.LinkedList;

public abstract class IntegerBinaryTree<V>  extends BinaryTree<Integer, V> {
	/**
	 * Serial version ID to ensure that the imported serialized object is the correct one 
	 */
	private static final long serialVersionUID = 1L;
	public IntegerBinaryTree (Integer k, V v) {	this(new Node<>(k,v));	}
	public IntegerBinaryTree (Node<Integer, V> root) {super(root);}
	public IntegerBinaryTree () { super(); }

	public abstract IntegerBinaryTree<V> join (IntegerBinaryTree<V> rightChild);
	
	@Override
	public String toString() {
		StringBuilder tree = new StringBuilder();
		LinkedList<Node<Integer, V>> queue = new LinkedList<>();
		queue.addLast(root);
		int level = 0;
		int goThrough = 1;
		while(!queue.isEmpty()) {
			tree.append((level++) + " ");
			int childCount = 0;
			for (int i = 0; i < goThrough; i++) {
				Node<Integer, V> current = queue.removeFirst();
				tree.append(current + " ");
				if (current.hasLeft()) {childCount++; queue.addLast(current.leftChild);}
				if (current.hasRight()) {childCount++; queue.addLast(current.rightChild);}
			}
			goThrough = childCount;
			if(queue.size() > 1) tree.append("\n");
		}
		
		return tree.toString();
	}
}

