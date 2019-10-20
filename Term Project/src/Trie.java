import java.util.HashMap;

class Node {

    public Node (Node parent, char value)  {
    	this.parent = parent;
        children = new HashMap<>();
        letter = value;
        end = false;
    }

    public HashMap <Character, Node> getChildren() {   
        return children;  
    }
    
    public Node getParent()
    {
    	return parent;
    }
    public char getletter() {                          
        return letter;     
    }

    public void end(boolean value) {
        end = value;
    }

    public boolean isEnd() {                       
        return end;    
    }

    private HashMap <Character, Node> children;
    private char letter;
    private boolean end;
    private Node parent;
}

class Trie {

    public Trie() {
        root = new Node(null,(char)0);
    }
    
    //Edit in-case Trie needs to build off of file
    public Trie (String file) {}

    public void insert(String word)  {
        int length = word.length();
        Node base = root;
 
        for (int level = 0; level < length; level++) {
            HashMap <Character, Node> child = base.getChildren();
            char value = word.charAt(level);

            if (child.containsKey(value)) {
                base = child.get(value);
            }
            
            else {
                Node temp = new Node(base, value);
                child.put(value, temp);
                base = temp;
            }
        }
        base.end(true);
    }
    
    public Node getRoot () {return root;}
    private Node root;
}
