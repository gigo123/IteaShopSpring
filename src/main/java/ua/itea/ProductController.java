package ua.itea;

import java.util.List;

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
import dao.ProductDAO;
import models.Product;
import mySql.MySQLDAOFactory;

@Controller
@RequestMapping("/product")
public class ProductController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getProductList() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		return getWorker(session, "");
	}

	@RequestMapping(method = RequestMethod.GET, params = { "category" })
	public ModelAndView getProductListCategory(@RequestParam("category") String category) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		return getWorker(session, category);
	}

	private ModelAndView getWorker(HttpSession session, String category) {
		List<Product> products;
		ModelAndView model = new ModelAndView("ProductsView");
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		ProductDAO pd=  (ProductDAO) context.getBean("ProductDAO");
		if (category.equals("")) {
			products = pd.getProductList();
		} else {
			int intCategory = Integer.parseInt(category);
			products = pd.getProductByCategory(intCategory);
		}
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
		model.addObject("productList", products);
		model.addObject("page", "product");
		return model;
	}

}
