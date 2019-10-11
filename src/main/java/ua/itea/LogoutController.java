package ua.itea;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/logout")
public class LogoutController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView logOut() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); 
		if (session.getAttribute("login") != null) {
			session.invalidate();
			session = attr.getRequest().getSession(true);
		}
		return new ModelAndView("redirect:/");
	}

}
