package mySql;

import dao.DaoFactory;
import dao.ProductDAO;
import dao.UserDAO;

public class MySQLDAOFactory extends DaoFactory{

	@Override
	public ProductDAO getProductDAO() {
		return new  MySQLProductDAO();
	}

	@Override
	public UserDAO getUserDAO() {		
	 return new  MySQLUserDAO();
	}


}
