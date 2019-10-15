package ua.itea;

import java.util.Date;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import dao.DaoFactory;
import dao.UserDAO;
import mySql.MySQLDAOFactory;

@Controller
@RequestMapping("/login")
public class LoginController {
	static boolean formBlocked = false;
	static long failTime = 0;
	static int errorCounter = 0;
	long timeBlockLeft = 0;
	String userName;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView loginGet() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		ModelAndView model;
		if (formBlocked) {
			checkUblock();
			if (timeBlockLeft > 0) {
				model = new ModelAndView("BlockedFormView");
				model.addObject("time", timeBlockLeft);
				return model;
			}
		}
		model = new ModelAndView("LoginView");
		model.addObject("attempt", errorCounter);
		model.addObject("page", "login");
		if (session.getAttribute("cart_number") != null) {
			model.addObject("items", session.getAttribute("cart_number"));
		} else {
			model.addObject("items", 0);
		}
		return model;
	}

	@RequestMapping(method = RequestMethod.POST, params = { "login", "password" })
	public ModelAndView loginPost(@RequestParam("login") String login, 
			@RequestParam("password") String password) {
		if (!login.equals("") && !password.equals("")) {
			if (!checkCredials(login, password)) {
				ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
						.currentRequestAttributes();
				HttpSession session = attr.getRequest().getSession(true); // true == allow create
				session.setAttribute("login", login);
				session.setAttribute("userName", userName);
				return new ModelAndView("redirect:/");
			}
		}
		return loginGet();
	}

	private boolean checkCredials(String login, String password) {
		boolean loginFailded = true;
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		UserDAO userDAO =  (UserDAO) context.getBean("UserDAO");
		boolean cLogin = userDAO.checkLoginPasswords(login, password);
		if (cLogin) {
			errorCounter = 0;
			loginFailded = false;
			userName = userDAO.getUserByLogin(login).getName();
		} else {
			errorCounter++;
			loginFailded = true;
			checkBlock();
		}
		return loginFailded;
	}

	private void checkBlock() {
		if (errorCounter > 2) {
			failTime = new Date().getTime();
			formBlocked = true;
		}
	}

	private void checkUblock() {
		if (failTime != 0) {
			timeBlockLeft = ((failTime + 60000) - new Date().getTime()) / 1000;
			if (timeBlockLeft <= 0) {
				failTime = 0;
				formBlocked = false;
				errorCounter = 0;
			}
		}
	}

}
