/*
package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	private  Connection connection;

	public DatabaseConnection() {
		String url = "jdbc:mysql://localhost:3306/Railway_Reservation_System";
		String password = "Tejas172304@";
		String username = "Tejas";

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
*/




package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	// Step 1: Private static variable to hold the single instance of the class
	private static DatabaseConnection instance;

	// The actual database connection
	private Connection connection;

	// Database connection parameters
	private static final String URL = "jdbc:mysql://localhost:3306/Railway_Reservation_System";
	private static final String USERNAME = "Tejas";
	private static final String PASSWORD = "Tejas172304@";

	// Step 2: Private constructor to prevent instantiation from outside
	public DatabaseConnection() {
		try {
			// Step 3: Initialize the connection
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

	// Step 4: Public static method to get the instance
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

	// Method to get the connection object
	public Connection getConnection() {
		return connection;
	}

	// Optional: A method to close the connection
	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Connection Closed....");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
