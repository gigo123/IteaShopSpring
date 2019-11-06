package ua.itea;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainPageController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView returnString(HttpSession session) {
		ModelAndView model = new ModelAndView("MainView");
		if (session.getAttribute("login") != null) {
			model.addObject("login", true);
			model.addObject("userName", session.getAttribute("userName"));
		}
		if (session.getAttribute("cart") != null) {
			if (session.getAttribute("cart_number") != null) {
				model.addObject("items", session.getAttribute("cart_number"));
			}
		} else {
			model.addObject("items", 0);
		}
		model.addObject("page", "main");
		return model;
	}

}
