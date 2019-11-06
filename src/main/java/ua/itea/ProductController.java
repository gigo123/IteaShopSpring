package ua.itea;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import dao.ProductDAO;
import models.Product;


@Controller
@RequestMapping("/product")
public class ProductController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getProductList(HttpSession session) {
		return getWorker(session, "");
	}

	@RequestMapping(method = RequestMethod.GET, params = { "category" })
	public ModelAndView getProductListCategory(@RequestParam("category") String category,HttpSession session) {
		return getWorker(session, category);
	}

	private ModelAndView getWorker(HttpSession session, String category) {
		List<Product> products;
		ModelAndView model ;
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		ProductDAO pd=  (ProductDAO) context.getBean("ProductDAO");
		model = new ModelAndView("ProductsView");
		if (category.equals("")) {
			products = pd.getProductList();
			model.addObject("page", "product");
		} else {
			int intCategory = Integer.parseInt(category);
			products = pd.getProductByCategory(intCategory);
			model.addObject("page", category);
		}
		if(!pd.getError()) {
			
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
		return model;
		}
		else {
			model = new ModelAndView("SqlErrorView");
			return model;
		}
	}

}
