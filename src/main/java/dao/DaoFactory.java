package dao;


public  abstract class DaoFactory {
	public DaoFactory() {
		
	}
 public abstract ProductDAO getProductDAO();
 public abstract UserDAO getUserDAO();

}
