package ua.itea;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/info")
public class InfoController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getInfo() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		ModelAndView model = new ModelAndView("InfoView");
		if (session.getAttribute("cart_number") != null) {
			model.addObject("items", session.getAttribute("cart_number"));
		} else {
			model.addObject("items", 0);
		}
		return model;
	}

}
