package mySql;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;

public class MySQLUserDAO implements dao.UserDAO {
	private final static String SELECT_QUERY = "SELECT * FROM users WHERE login = ?";
	private final static String INSERT_QUERY = "INSERT INTO users(login, password, name, region, gender, comment)"
			+ " VALUES(?, ?, ?, ?, ?, ?)";
	private final static String USER_UPDATE_QUERY = "UPDATE  users set  password= ?, name = ?, region = ?, gender = ? , comment = ?"
			+ " WHERE  login = ?";
	private final static String SALT = "miniMax";
	private SQLConectionHolder conectionHolder;
	private boolean sqlError= false;
	
	public boolean isSqlError() {
		return sqlError;
	}

	public void setSqlError(boolean sqlError) {
		this.sqlError = sqlError;
	}

	public SQLConectionHolder getConectionHolder() {
		return conectionHolder;
	}

	public void setConectionHolder(SQLConectionHolder conectionHolder) {
		this.conectionHolder = conectionHolder;
	}

	@Override
	public User getUserByID(long id) {
		// select from user where id = ?
		return null;
	}

	@Override
	public boolean checkLoginPasswords(String login, String password) {
		Connection conn = conectionHolder.getConnection();
		String pass_Query = "SELECT * FROM users WHERE login = ? AND  password = ?";
		ResultSet rs = null;
		PreparedStatement prepSt = null;
		if(!conectionHolder.isError()){
		try {
			prepSt = conn.prepareStatement(pass_Query);
			prepSt.setString(1, login);
			prepSt.setString(2, hash(password));
			rs = prepSt.executeQuery();
			while (rs.next()) {
				conectionHolder.closeConnection();
				return true;
			}
			conectionHolder.closeConnection();
		} catch (SQLException e) {
			sqlError=true;
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}
				rs = null;
				if (prepSt != null) {
					try {
						prepSt.close();
					} catch (SQLException sqlEx) {
					}
					prepSt = null;
				}
			}
		}
		}
		else {
			sqlError=true;
		}
		return false;

	}

	public boolean selectEmail(String formEmail) {
		ResultSet rs = null;
		PreparedStatement prepSt = null;
		Connection conn = conectionHolder.getConnection();
		if(!conectionHolder.isError()){
		try {
			prepSt = conn.prepareStatement(SELECT_QUERY);
			prepSt.setString(1, formEmail);
			rs = prepSt.executeQuery();
			while (rs.next()) {
				conectionHolder.closeConnection();
				return true;
			}
			conectionHolder.closeConnection();
		} catch (SQLException e) {
			sqlError=true;
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}
				rs = null;
				if (prepSt != null) {
					try {
						prepSt.close();
					} catch (SQLException sqlEx) {
					}
					prepSt = null;
				}
			}
		}
		}
		else {
			sqlError=true;
		}
		return false;
	}

	public User getUserByLogin(String login) {
		ResultSet rs = null;
		PreparedStatement prepSt = null;
		User user = null;
		Connection conn = conectionHolder.getConnection();
		if(!conectionHolder.isError()){
		try {
			prepSt = conn.prepareStatement(SELECT_QUERY);
			prepSt.setString(1, login);
			rs = prepSt.executeQuery();

			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setLogin(login);
				rs.getString("login");
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setRegion(rs.getString("region"));
				user.setGender(rs.getBoolean("gender"));
				user.setComment(rs.getString("comment"));
			}
			conectionHolder.closeConnection();
		} catch (SQLException e) {
			sqlError=true;
			e.printStackTrace();
		} finally {
			if (prepSt != null) {
				try {
					prepSt.close();
				} catch (SQLException sqlEx) {
				}
				prepSt = null;
			}
		}
		}
		else {
			sqlError=true;
		}
		return user;

	}

	public boolean insertUser(User user) {
		Connection conn = conectionHolder.getConnection();
		PreparedStatement prepSt = null;
		if(!conectionHolder.isError()){
		try {
			prepSt = conn.prepareStatement(INSERT_QUERY);
			prepSt.setString(1, user.getLogin());
			prepSt.setString(2, hash(user.getPassword()));
			prepSt.setString(3, user.getName());
			prepSt.setString(4, user.getRegion());
			prepSt.setBoolean(5, user.getGender());
			prepSt.setString(6, user.getComment());
			prepSt.execute();
			conectionHolder.closeConnection();
		} catch (SQLException e) {
			sqlError=true;
			e.printStackTrace();
			return false;
		} finally {
			if (prepSt != null) {
				try {
					prepSt.close();
				} catch (SQLException sqlEx) {
				}
				prepSt = null;
			}
		}
		return true;
		}
		else {
			sqlError=true;
			return false;
		}
		
		
	}

	public boolean updateUser(User user, String login) {
		Connection conn = conectionHolder.getConnection();
		PreparedStatement prepSt = null;
		if(!conectionHolder.isError()){
		try {
			prepSt = conn.prepareStatement(USER_UPDATE_QUERY);
			prepSt.setString(1, hash(user.getPassword()));
			prepSt.setString(2, user.getName());
			prepSt.setString(3, user.getRegion());
			prepSt.setBoolean(4, user.getGender());
			prepSt.setString(5, user.getComment());
			prepSt.setString(6, login);
			prepSt.execute();
			conectionHolder.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (prepSt != null) {
				try {
					prepSt.close();
				} catch (SQLException sqlEx) {
				}
				prepSt = null;
			}
		}
		return true;
	}
	else {
		sqlError=true;
		return false;
	}
	}

	private String hash(String password) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(StandardCharsets.UTF_8.encode(password + SALT));
			// System.out.println(String.format("%032x",new BigInteger(md5.digest())));
			return String.format("%032x", new BigInteger(md5.digest()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean getError() {
		return sqlError;
	}
}
