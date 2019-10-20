import java.io.Serializable;

public class Employee implements Serializable {

	/**
	 * The serial class ID for the employee 
	 */
	private static final long serialVersionUID = 1L;
	
	String name;
	
	byte id;
	
	String address;
	
	public Employee (String n, byte i, String a) {
		name = n;
		id = i;
		address = a;
	}
	
	public String getName () {
		return name;
	}
	
	public byte getID() {
		return id;
	}
	
	public String getAddress () {
		return address;
	}
	
	public void printByte () {
		System.out.println(id);
	}
}
