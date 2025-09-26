import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class InsertPassenger {
	
	public static void insertPassenger(String fname,String lname,String email) {
		
		try {
			String url="jdbc:mysql://127.0.0.1:3306/airlines";
			String username="root";
			String password="0306";
			String query="insert into Passengers(FirstName,LastName,Email) values(?,?,?)";
			Connection con=DriverManager.getConnection(url, username, password);
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,fname);
			pst.setString(2, lname);
			pst.setString(3, email);
			
			pst.executeUpdate();
			System.out.println("Passenger details updated ");
			con.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter First name");
		String fname=sc.next();
		System.out.println("Enter Last Name");
		String lname=sc.next();
		System.out.println("Enter email id");
		String email=sc.next();
		insertPassenger(fname, lname, email);
		
	}

}
