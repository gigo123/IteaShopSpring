package ua.itea;

import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import dao.UserDAO;
import models.User;


@Controller
@RequestMapping("/register")
public class RegisterController {
	private boolean error = false;
	private StringBuilder errorText;
	private User user;
	private UserDAO userDAO;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRegisterForm(HttpSession session) {
		ModelAndView model = new ModelAndView("RegisterView");
		if (error) {
			error = false;
			model.addObject("login", false);
			model.addObject("errorText", errorText.toString());
			if (session.getAttribute("login") != null) {
				model.addObject("login", true);
			}
		} else if (session.getAttribute("login") != null) {
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
			userDAO = (UserDAO) context.getBean("UserDAO");
			user = userDAO.getUserByLogin(session.getAttribute("login").toString());
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

	@RequestMapping(method = RequestMethod.POST, params = { "login", "password", "password2", "name", "region",
			"gender", "comment", "acceptOffer" })
	public ModelAndView registerFormPOST(@RequestParam("login") String login, @RequestParam("password") String password,
			@RequestParam("password2") String password2, @RequestParam("name") String name,
			@RequestParam("region") String region, @RequestParam("gender") String gender,
			@RequestParam("comment") String comment, 
			@RequestParam("acceptOffer") String acceptOffer ,HttpSession session) {
		error = false;
		errorText = new StringBuilder("<ul>");
		user = new User(login, password, name, region, convertGenderToBool(gender), comment);
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		userDAO = (UserDAO) context.getBean("UserDAO");
		checkErrors(session, password2, acceptOffer);
		if (!error) {
			if (session.getAttribute("login") != null) {
				if (!userDAO.updateUser(user, session.getAttribute("login").toString())) {
					error = true;
					errorText.append("<li>DataBase error</li>");
				}
			} else {
				if (!userDAO.insertUser(user)) {
					error = true;
					errorText.append("<li>DataBase error</li>");
				}
			}
			session.setAttribute("login", login);
			session.setAttribute("userName", name);
			if (!error) {
				return new ModelAndView("redirect:/");
			}
		}
		return getRegisterForm(session);

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
		if (userDAO.selectEmail(login)) {
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
