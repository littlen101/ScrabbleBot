import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

public class BinaryTree <K extends Comparable<K>,V> implements Comparable<BinaryTree<K,V>>, Serializable{
	/**
	 * Serial version ID to ensure that the imported serialized object is the correct one 
	 */
	private static final long serialVersionUID = 1L;

	//------------- nested Node Class ---------
	static class Node <K extends Comparable<K>,V> implements Serializable{
		
		
		/**
		 * Serial version ID to ensure that the imported serialized object is the correct one 
		 */
		private static final long serialVersionUID = 2L;

		Node<K,V> parent;
		
		Node<K,V> leftChild;
		
		Node<K,V> rightChild;
		
		K key;
		
		V value;
		
		public Node(K k, V v) {
			key = k;
			value = v;
		}
		
		public Node(K k) { this(k, null); }
		
		public Node<K,V> getLeftChild () {return leftChild;}
		
		public Node<K,V> getRightChild () {return rightChild;}
		
		public Iterable<Node<K,V>> children () {
			List<Node<K,V>> c = new ArrayList<Node<K,V>>();
			if (leftChild != null) c.add(leftChild);
			if (rightChild != null) c.add(rightChild);
			
			return c;
		}
		
		public Node<K,V> getParent () {	return parent;}
		
		public boolean isLeaf () {return ((!hasLeft()) && (!hasRight()));}
		
		public void setLeft (Node<K,V> in) {
			leftChild = in;
			in.parent = this;
		}
		
		public void setRight (Node<K,V> in) {
			rightChild = in;
			in.parent = this;
		}
		
		public void setValue (V v) { value = v;	}
		
		public void setKey (K k) { key = k; }
		
		public boolean hasLeft() { return !(leftChild == null);}
		
		public boolean hasRight() { return !(rightChild == null);}
		
		@Override
		public String toString () {
			return String.format("(%s:%s)", (key != null)? key.toString(): "null", 
											(value !=  null)? value.toString() : "null");
		}
	} //----------- end nested Node class ------

	Node<K,V> root;
	
	
	public BinaryTree (K key, V value) {
		this(new Node<>(key, value));
	}
	
	public BinaryTree (Node<K,V> r) {
		root = r;
	}
	
	public BinaryTree () {}

	public Iterable<Node<K,V>> breadthfirst () {
		List<Node<K,V>> tree = new ArrayList<Node<K,V>>();
		Queue<Node<K,V>> queue = new LinkedTransferQueue<>();
		queue.add(root);
		while(!queue.isEmpty()) {
			Node<K,V> current = queue.poll();
			tree.add(current);
			for (Node<K,V> child : current.children()) {
				queue.add(child);
			}
		}
		
		return tree;
	}
	
	public void setRoot (Node<K,V> r) {	root = r; }

	@Override
	public int compareTo (BinaryTree<K,V> other) {
		return root.key.compareTo(other.root.key);
	}

}
