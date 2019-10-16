package ua.itea;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/checkout")
public class CheckOutController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView displayCheckOut() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		ModelAndView model = new ModelAndView("CheckOutView");
		if (session.getAttribute("login") != null) {
			model.addObject("login", true);
		} else {
			model.addObject("login", false);
		}
		if (session.getAttribute("cart") != null) {
			if (session.getAttribute("cart_number") != null) {
				model.addObject("items", session.getAttribute("cart_number"));
			}
		} else {
			model.addObject("items", 0);
		}
		session.setAttribute("checkOut", true);
		return model;
	}

	
}
