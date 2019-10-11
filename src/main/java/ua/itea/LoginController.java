package ua.itea;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(method = RequestMethod.GET, value = "/page2", params = { "var" })
	public String returnString(@RequestParam("var") String var, ModelMap model) {
		model.addAttribute("msg", "taras");
		return "HelloPage";
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
