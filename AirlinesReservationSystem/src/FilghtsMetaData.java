import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class FilghtsMetaData {
	public static void main(String[] args) throws Exception {
		String url = "jdbc:mysql://127.0.0.1:3306/airlines";
		String username = "root";
		String password = "0306";
		String Query = "Select * from flights";
		Connection con = DriverManager.getConnection(url, username, password);
		Statement st = con.createStatement();
		ResultSet rt = st.executeQuery(Query);
		ResultSetMetaData rsmd = rt.getMetaData();
		int columncount = rsmd.getColumnCount();
		System.out.println("Number of columns" + columncount);

		for (int i = 1; i <= columncount; i++) {
			System.out.println("column" + i + ":" + rsmd.getColumnName(i) + "(" + rsmd.getColumnTypeName(i) + ")");
		}

	}
}
