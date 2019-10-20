import java.io.Serializable;


public abstract class Node <K extends Comparable<K>,V> implements Serializable{
		
		
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
		
		public abstract Node<K,V> getLeftChild ();
		
		public abstract Node<K,V> getRightChild ();
		
		public abstract Iterable<Node<K,V>> children ();
		
		public abstract Node<K,V> getParent ();
		
		public abstract boolean isLeaf ();
		
		public abstract void setLeft (Node<K,V> in);
		
		public abstract void setRight (Node<K,V> in);
		
		public abstract void setValue (V v);
		
		public abstract void setKey (K k);
		
		public abstract boolean hasLeft();
		
		public abstract boolean hasRight();
		
		@Override
		public String toString () {
			return String.format("(%s:%s)", (key != null)? key.toString(): "null", 
											(value !=  null)? value.toString() : "null");
		}
	}