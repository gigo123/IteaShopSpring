package dao;

import models.User;

public interface UserDAO {
	public User getUserByID(long id);
	public boolean checkLoginPasswords(String login, String password);
	public boolean  updateUser(User user, String login) ;
	public boolean selectEmail(String formEmail);
	public boolean insertUser(User user);
	public User getUserByLogin(String login) ;
}
