package mySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConectionHolder {
	private static Connection conn;

	private String connString;
	
	public String getConnString() {
		return connString;
	}

	public void setConnString(String connString) {
		this.connString = connString;
	}

	public  Connection getConnection() {
		if (conn == null)
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				//conn = DriverManager.getConnection("jdbc:mysql://localhost/iteashop?" + "user=root&password=");
				conn = DriverManager.getConnection(connString);
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

	public void closeConnection() {
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
