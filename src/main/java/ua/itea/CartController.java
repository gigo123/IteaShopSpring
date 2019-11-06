package ua.itea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import dao.ProductDAO;
import models.Product;


@Controller
@RequestMapping("/cart")
public class CartController {
	HttpSession sessionInController;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getCartList(HttpSession session) {
		sessionInController = session;
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

	@RequestMapping(method = RequestMethod.POST, params = { "productToBuy", "numberOfGoods" })
	@ResponseBody
	public String productBuy(@RequestParam("productToBuy") String productToBuy,
			@RequestParam("numberOfGoods") String numberOfGoods,HttpSession session) {
		return cartMapProcessed("buy", Integer.parseInt(productToBuy), 
				Integer.parseInt(numberOfGoods) ,session);
	}

	@RequestMapping(method = RequestMethod.POST, params = { "productToChange", "numberOfGoods" })
	@ResponseBody
	public String productBuyCart(@RequestParam("productToChange") String productToChange,
			@RequestParam("numberOfGoods") String numberOfGoods, HttpSession session) {
		return cartMapProcessed("change", Integer.parseInt(productToChange), 
				Integer.parseInt(numberOfGoods), session);
	}

	@RequestMapping(method = RequestMethod.POST, params = { "productToRemove" })
	@ResponseBody
	public String productRemove(@RequestParam("productToRemove") String productToRemove , HttpSession session) {
		return cartMapProcessed("remove", Integer.parseInt(productToRemove), 0, session);
	}

	@SuppressWarnings("unchecked")
	private String cartMapProcessed(String type, long productId, int numberOfGoods , HttpSession session) {
		Map<Product, Integer> cartMap;
		if (session.getAttribute("cart") != null) {
			cartMap = (Map<Product, Integer>) session.getAttribute("cart");
		} else {
			cartMap = new HashMap<Product, Integer>();
		}
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		ProductDAO pd = (ProductDAO) context.getBean("ProductDAO");
		Product product = pd.getProductById(productId);
		if (!pd.getError()) {
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
			int count = productsCount(cartMap);
			session.setAttribute("cart_number", count);
			if (type.equals("buy")) {
				return String.valueOf(count);
			} else {
				return "{\"numberOfGoods\":\"" + session.getAttribute("cart_number").toString()
						+ "\",\"totalCartSum\":\"" + totalCartSum(session) + "\"}";
			}
		}
		return null;
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

	private int totalCartSum(HttpSession session) {
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
