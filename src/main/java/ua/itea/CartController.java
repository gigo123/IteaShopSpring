package ua.itea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import dao.DaoFactory;
import dao.ProductDAO;
import models.Product;
import mySql.MySQLDAOFactory;

@Controller
@RequestMapping("/cart")
public class CartController {
	HttpSession session;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getCartList() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		session = attr.getRequest().getSession(true); // true == allow create
		ModelAndView model = new ModelAndView("CartView");
		if (session.getAttribute("login") != null) {
			model.addObject("login", true);
			model.addObject("userName", session.getAttribute("userName"));
		}
		if (session.getAttribute("cart") != null) {
			model.addObject("productList", session.getAttribute("cart"));
			if (session.getAttribute("cart_number") != null) {
				model.addObject("items", session.getAttribute("cart_number"));
				model.addObject("totalSumm", totalCartSum((Map<Product, Integer>) session.getAttribute("cart")));
			}
		} else {
			model.addObject("items", 0);
			model.addObject("totalSumm", 0);
		}
		model.addObject("page", "cart");
		return model;
	}
	@SuppressWarnings("unchecked")
	private void cartMapProcessed(String type, long productId, int numberOfGoods) {
		Map<Product, Integer> cartMap;
		if (session.getAttribute("cart") != null) {

			cartMap = (Map<Product, Integer>) session.getAttribute("cart");
		} else {
			cartMap = new HashMap<Product, Integer>();
		}
		DaoFactory df = new MySQLDAOFactory();
		ProductDAO pd = df.getProductDAO();
		Product product = pd.getProductById(productId);
		if (type.equals("buy")) {
			if (cartMap.containsKey(product)) {
				numberOfGoods = numberOfGoods + cartMap.get(product);
			}
			cartMap.put(product, numberOfGoods);
		}
		if (type.equals("remove")) {
			if (cartMap.containsKey(product)) {
				cartMap.remove(product);
			}
		}
		if (type.equals("change")) {
			cartMap.put(product, numberOfGoods);
		}
		session.setAttribute("cart", cartMap);
		session.setAttribute("cart_number", productsCount(cartMap));
		return;
	}

	private int productsCount(Map<Product, Integer> cartMap) {
		ArrayList<Integer> numbers = new ArrayList<Integer>(cartMap.values());
		int sum = 0;
		for (int n : numbers) {
			sum = sum + n;
		}
		return sum;
	}

	private int totalCartSum(Map<Product, Integer> cartMap) {
		int sum = 0;
		Iterator<Map.Entry<Product, Integer>> itr = cartMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Product, Integer> entry = itr.next();
			sum = sum + entry.getKey().getPrice() * entry.getValue();
		}
		return sum;
	}

	private int totalCartSum() {
		Map<Product, Integer> cartMap;
		int sum = 0;
		cartMap = (Map<Product, Integer>) session.getAttribute("cart");

		Iterator<Map.Entry<Product, Integer>> itr = cartMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Product, Integer> entry = itr.next();
			sum = sum + entry.getKey().getPrice() * entry.getValue();
		}

		return sum;
	}

}