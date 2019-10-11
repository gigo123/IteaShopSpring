package mySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConectionHolder {
	private static Connection conn;

	public static Connection getConnection() {
		if (conn == null)
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection("jdbc:mysql://localhost/iteashop?" + "user=root&password=");
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
				System.out.println(ex.getMessage());
			} catch (SQLException ex) {
				System.out.println("Failed");
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		return conn;

	};

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	};

}
