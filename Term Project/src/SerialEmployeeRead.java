import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SerialEmployeeRead {
	public static void main (String[] args) throws IOException, ClassNotFoundException {
		FileInputStream fin = new FileInputStream("./employee.src");
		ObjectInputStream ois = new ObjectInputStream(fin);

		Employee serialEmployee = (Employee) ois.readObject();
		ois.close();
		
		System.out.println(serialEmployee.id);
	}
}
