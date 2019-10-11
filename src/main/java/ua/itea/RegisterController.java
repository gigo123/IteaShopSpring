package ua.itea;

import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.UserDAO;
import models.User;

@Controller
@RequestMapping("/register")
public class RegisterController {
	private boolean error = false;
	private boolean createUser = false;
	private StringBuilder errorText;
	private User user;
	private UserDAO uersDAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRegisterForm() {
		return null;
		
	}
	private boolean convertGenderToBool(String gender) {
		if (gender.equals("Male")) {
			return true;
		} else {
			return false;
		}

	}

	private void checkErrors(HttpSession session, String password2, String acceptOffer) {
		if (session.getAttribute("login") == null) {
			checkLogin(user.getLogin());
		}
		checkPassword(user.getPassword(), password2);
		if (user.getName().length() < 1) {
			error = true;
			errorText.append("<li> name field is empty</li>");
		}
		if (user.getComment().length() < 1) {
			error = true;
			errorText.append("<li> you not fill commetary</li>");
		}
		if (acceptOffer == null) {
			error = true;
			errorText.append("<li> you not accept offer </li>");
		}
	}

	private void checkLogin(String login) {
		if (login.length() < 1) {
			error = true;
			errorText.append("<li>Login field is empty</li>");
			return;
		}
		if (uersDAO.selectEmail(login)) {
			error = true;
			errorText.append("<li>this email is alredy in use</li>");
		}
		if (!Pattern.matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$", login)) {
			error = true;
			errorText.append("<li>you login is not valid email</li>");
		}
	}

	private void checkPassword(String password, String password2) {
		if (password.length() < 1 || password2.length() < 1) {
			error = true;
			errorText.append("<li>Password field is empty </li>");
			return;
		}

		if (!password.equals(password2)) {
			error = true;
			errorText.append("<li>Passwords must match </li>");
			return;
		}
		if (!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9]{8,}$", password)) {
			error = true;
			errorText.append("<li>Password must meet security settings </li>");
		}
	}

}
