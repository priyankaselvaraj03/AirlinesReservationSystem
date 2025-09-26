import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateFlight {
	public static void create() throws Exception {
		String url = "jdbc:mysql://127.0.0.1:3306/airlines";
		String username = "root";
		String password = "0306";
		String file_path = "E:\\Priyanka GT\\Backend\\flights.txt";
		String query = "INSERT INTO Flights (FlightNumber, DepartureAirport, ArrivalAirport, DepartureTime, AvailableSeats) VALUES (?, ?, ?, ?, ?)";
		Connection con = DriverManager.getConnection(url, username, password);
		try {
			
			con.setAutoCommit(false);
			try {
				PreparedStatement pst = con.prepareStatement(query);
				BufferedReader lineReader = new BufferedReader(new FileReader(file_path));
				String lineText;
				
				while ((lineText = lineReader.readLine()) != null) {
					String[] data = lineText.split(",");

					String flightNumber = data[0];
					String departureAirport = data[1];
					String arrivalAirport = data[2];
					String departureTimeStr = data[3];
					int availableSeats = Integer.parseInt(data[4]);

					// Convert String to Timestamp for the database
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime localDateTime = LocalDateTime.parse(departureTimeStr, formatter);
					Timestamp departureTime = Timestamp.valueOf(localDateTime);

					// Set parameters for the prepared statement
					pst.setString(1, flightNumber);
					pst.setString(2, departureAirport);
					pst.setString(3, arrivalAirport);
					pst.setTimestamp(4, departureTime);
					pst.setInt(5, availableSeats);

					// Add the statement to the batch
					pst.addBatch();
		

					// Execute the batch
					int[] result = pst.executeBatch();
					System.out.println("Batch executed successfully. Records inserted: " + result.length);

					// Commit the transaction
					con.commit();
					System.out.println("Transaction committed.");
				}
			} catch (Exception e) {
				System.err.println("Error during batch insert. Rolling back transaction.");
				e.printStackTrace();
				if (con != null) {
					try {
						con.rollback();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
		finally {
			con.setAutoCommit(true);
			con.close();
		}

	}

	public static void main(String[] args) throws Exception {
		create();
	}
}
