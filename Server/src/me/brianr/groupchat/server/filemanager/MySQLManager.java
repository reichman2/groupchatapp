package me.brianr.groupchat.server.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manage SQL stuff.
 * <p>The database name must be `chatapp`</p>
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class MySQLManager {
	private static String address = "jdbc:mysql://127.0.0.1:3306/chatapp";
	
	// For some reason database-specific user doesn't work.
	private static String username = "root"; // chatapp
	private static String password = "";  // DerryTivs4567!
	private static Connection connection;
	
	/**
	 * This class should not be instantiated.
	 */
	private MySQLManager() {}
	
	
	/**
	 * Get a connection to the local mysql server.
	 * @return
	 */
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try {
				connection = DriverManager.getConnection(address, username, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Could not connect to the database. :(");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: Driver not found!");
			e.printStackTrace();
		}
		return connection;
	}
	
	
	// TESTER
	/*
	public static void main(String[] args) {
		Connection cnct = getConnection();
		PreparedStatement stmt;
		try {
			stmt = cnct.prepareStatement("SELECT * FROM `users`");
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				StringBuilder stb = new StringBuilder();
				stb.append("ID: " + results.getInt("id"));
				stb.append("\nName: " + results.getString("name"));
				stb.append("\nDisplayName: " + results.getString("displayname"));
				stb.append("\nRole: " + results.getString("role"));
				stb.append("\nState: " + results.getString("state"));
				
				System.out.println(stb.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/
}
