package ua.itea;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

	@RequestMapping(method = RequestMethod.GET)
	public String returnString( ModelMap model) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		if (formBlocked) {
			checkUblock();
			if (timeBlockLeft > 0) {
				model.addAttribute("time", timeBlockLeft);
				return "BlockedFormView";
			}
		}
		else {
			model.addAttribute("attempt", errorCounter);
			model.addAttribute("page", "login");
			if (session.getAttribute("cart_number") != null) {
				model.addAttribute("items", session.getAttribute("cart_number"));
			} else {
				model.addAttribute("items", 0);
			}
			return "LoginView";
		}
		return null;
	}

	private boolean checkCredials(String login, String password) {
		boolean loginFailded = true;
		DaoFactory df = new MySQLDAOFactory();
		UserDAO uersDAO = df.getUserDAO();
		if (uersDAO.checkLoginPasswords(login, password)) {
			errorCounter = 0;
			loginFailded = false;
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
