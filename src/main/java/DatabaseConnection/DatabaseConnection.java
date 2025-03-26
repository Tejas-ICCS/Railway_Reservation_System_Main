package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	private  Connection connection;
	public DatabaseConnection() {
		String url = "jdbc:mysql://localhost:3306/railway_reservation_system";
		String password = "Tejas172304@";
		String username = "root";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url,username,password);
			if(connection == null) {
				System.out.print("Connection Failed....");
			}
			else {
				System.out.print("Connection Created....");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
