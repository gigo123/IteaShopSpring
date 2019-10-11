package ua.itea;

import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import dao.DaoFactory;
import dao.UserDAO;
import models.User;
import mySql.MySQLDAOFactory;

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
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		if (createUser) {
			createUser = false;
			return new ModelAndView("redirect:/");
		}
		ModelAndView model = new ModelAndView("RegisterView");
		if (error) {
			error = false;
			model.addObject("login", false);
			model.addObject("errorText", errorText.toString());
			if (session.getAttribute("login") != null) {
				model.addObject("login", true);
			}
		} else if (session.getAttribute("login") != null) {
			DaoFactory df = new MySQLDAOFactory();
			uersDAO = df.getUserDAO();
			user = uersDAO.getUserByLogin(session.getAttribute("login").toString());
			model.addObject("login", true);
		} else {
			user = new User();
			model.addObject("login", false);
		}
		if (session.getAttribute("cart_number") != null) {
			model.addObject("items", session.getAttribute("cart_number"));
		} else {
			model.addObject("items", 0);
		}
		model.addObject("page", "register");
		model.addObject("user", user);
		return model;
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
