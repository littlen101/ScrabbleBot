import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerialEmployeeWrite {
	public static void main (String[] args) throws IOException {
		Employee toserial = new Employee ("Nick", (byte) 10, "Box 5933");
		
		FileOutputStream fout = new FileOutputStream("./employee.src");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		
		oos.writeObject(toserial);
		oos.close();
		
		System.out.println("Process Complete");
	}
}
