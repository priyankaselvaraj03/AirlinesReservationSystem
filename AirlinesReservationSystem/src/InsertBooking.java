import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertBooking {
	public static void makeReservation(int flightId, int passengerId, String seatNumber) throws SQLException {
		String url = "jdbc:mysql://127.0.0.1:3306/airlines";
		String username="root";
		String password="0306";
		String insertReservationSQL = "INSERT INTO Reservations (FlightID, PassengerID, SeatNumber) VALUES (?, ?, ?)";
		String updateFlightSQL = "UPDATE Flights SET AvailableSeats = AvailableSeats - 1 WHERE FlightID = ?";
		Connection con = DriverManager.getConnection(url, username, password);
		try {
			
			con.setAutoCommit(false);

			// 1. Insert the new booking
			try (PreparedStatement pstmtReservation = con.prepareStatement(insertReservationSQL)) {
				pstmtReservation.setInt(1, flightId);
				pstmtReservation.setInt(2, passengerId);
				pstmtReservation.setString(3, seatNumber);
				pstmtReservation.executeUpdate();
			}

			// 2. Update the available seat count
			try (PreparedStatement pstmtFlight = con.prepareStatement(updateFlightSQL)) {
				pstmtFlight.setInt(1, flightId);
				pstmtFlight.executeUpdate();
			}

			// If both operations are successful, commit the transaction [1.4.4]
			con.commit();
			System.out.println("Reservation successful and transaction committed.");

		} catch (Exception e) {
			System.err.println("Transaction failed. Rolling back changes.");
			if (con != null) {
				try {
					// Rollback the transaction in case of an error [1.4.4]
					con.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.setAutoCommit(true); // Restore default behavior
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Flight Id");
		int flightId=sc.nextInt();
		System.out.println("Enter passenger Id");
		int passengerId=sc.nextInt();
		System.out.println("Enter seatNumber");
		String seatNumber=sc.next();
		makeReservation(flightId, passengerId, seatNumber);
		}
}
