import java.io.Serializable;

public abstract class BinaryTree <K extends Comparable<K>,V> implements Comparable<BinaryTree<K,V>>, Serializable{
	/**
	 * Serial version ID to ensure that the imported serialized object is the correct one 
	 */
	private static final long serialVersionUID = 1L;

	Node<K,V> root;
	
	
	public BinaryTree (K key, V value) {
		this(new Node<>(key, value));
	}
	
	public BinaryTree (Node<K,V> r) {
		root = r;
	}
	
	public BinaryTree () {}

	public abstract Iterable<Node<K,V>> breadthfirst ();
	
	public void setRoot (Node<K,V> r) {	root = r; }

	@Override
	public int compareTo (BinaryTree<K,V> other) {
		return root.key.compareTo(other.root.key);
	}

}
