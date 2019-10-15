package mySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConectionHolder {
	private static Connection conn;
	private boolean error = false;
	private String errorMessage; 
	
	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

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
				error= false;
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
				error= true;
				errorMessage = "DataBase conection error";
				System.out.println("DataBase conection error");
			} catch (SQLException ex) {
				error= true;
				errorMessage = "SQLException: " + ex.getMessage();
				errorMessage +=" SQLState: " + ex.getSQLState();
				errorMessage +=" VendorError: " + ex.getErrorCode();
				System.out.println("SQL error");
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
