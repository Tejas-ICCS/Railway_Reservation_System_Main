package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	private static DatabaseConnection instance;
	private Connection connection;

	private static final String URL = "jdbc:mysql://localhost:3306/Railway_Reservation_System?useSSL=false&serverTimezone=UTC";
	private static final String USERNAME = "Tejas";
	private static final String PASSWORD = "Tejas172304@";

	private DatabaseConnection() {
		openConnection();
	}

	private void openConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			if (connection != null) {
				System.out.println("Connection Created....");
			} else {
				System.out.println("Connection Failed....");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DatabaseConnection getInstance() {
		if (instance == null) {
			synchronized (DatabaseConnection.class) {
				if (instance == null) {
					instance = new DatabaseConnection();
				}
			}
		}
		return instance;
	}

	public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				openConnection();
				System.out.println("Reconnected to Database....");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}
