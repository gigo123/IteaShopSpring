package mySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DaoFactory;
import dao.ProductDAO;
import models.Product;

public class MySQLProductDAO implements ProductDAO {

	private final static String SELECTID_QUERY = "SELECT * FROM products WHERE id = ?";
	private final static String SELECTCATEGORY_QUERY = "SELECT * FROM products WHERE category = ?";
	private final static String SELECTLIST_QUERY = "SELECT * FROM products";
	SQLConectionHolder conectionHolder;
	@Override
	public List<Product> getProductList() {
		List<Product> productList = new ArrayList<Product>();
		Statement selectStmt = null;
		ResultSet rs = null;
		Connection conn = conectionHolder.getConnection();
		try {
			selectStmt = conn.createStatement();
			rs = selectStmt.executeQuery(SELECTLIST_QUERY);
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getInt("price"));
				product.setCategory(rs.getInt("category"));
				productList.add(product);
			}
			conectionHolder.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return productList;
	}

	@Override
	public Product getProductById(long id) {
		ResultSet rs = null;
		PreparedStatement prepSt = null;
		Product product = null;
		Connection conn = conectionHolder.getConnection();
		try {
			prepSt = conn.prepareStatement(SELECTID_QUERY);
			prepSt.setLong(1, id);
			rs = prepSt.executeQuery();

			while (rs.next()) {
				product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getInt("price"));
				product.setCategory(rs.getInt("category"));
			}
			conectionHolder.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		return product;
	}

	@Override
	public List<Product> getProductByCategory(int category) {
		List<Product> productList = new ArrayList<Product>();
		ResultSet rs = null;
		PreparedStatement prepSt = null;
		Connection conn = conectionHolder.getConnection();
		try {
			prepSt = conn.prepareStatement(SELECTCATEGORY_QUERY);
			prepSt.setInt(1, category);
			rs = prepSt.executeQuery();

			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getInt("price"));
				product.setCategory(rs.getInt("category"));
				productList.add(product);
			}
			conectionHolder.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		return productList;
	}

	public SQLConectionHolder getConectionHolder() {
		return conectionHolder;
	}

	public void setConectionHolder(SQLConectionHolder conectionHolder) {
		this.conectionHolder = conectionHolder;
	}
}
