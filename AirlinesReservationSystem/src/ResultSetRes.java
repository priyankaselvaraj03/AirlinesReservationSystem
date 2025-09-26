import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ResultSetRes {
	
	public static void updateSeatNumber(int reservationId, String newSeatNumber) {
        // To make a ResultSet updatable, you must specify CONCUR_UPDATABLE [1.6.4, 1.6.2]
       
        try {
        	String url="jdbc:mysql://127.0.0.1:3306/airlines";
			String username="root";
			String password="0306";
			 String sql = "SELECT * FROM Reservations WHERE ReservationID = ?";
			 Connection con=DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pstmt.setInt(1, reservationId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                // Move to the first (and only) row
                if (rs.next()) {
                    // Update the column value in the ResultSet [1.6.1]
                    rs.updateString("SeatNumber", newSeatNumber);
                    // Propagate the change to the database [1.6.1]
                    rs.updateRow();
                    System.out.println("Reservation " + reservationId + " updated to seat " + newSeatNumber);
                } else {
                    System.out.println("Reservation with ID " + reservationId + " not found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public static void main(String[] args) {
	
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter Reservation ID");
	int rId=sc.nextInt();
	System.out.println("Enter SeatNo");
	String sno=sc.next();
	updateSeatNumber(rId, sno);
	
}

}
