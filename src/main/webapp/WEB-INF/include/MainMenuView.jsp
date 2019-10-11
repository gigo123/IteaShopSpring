<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<ul class="mainmenu">
	<li class="mainmenu__item menu-item-has-children"><a
		href="./product" class="mainmenu__link"> <span class="mm-text">Home</span>
	</a></li>
	<li class="mainmenu__item menu-item-has-children megamenu-holder">
		<a href="./product" class="mainmenu__link"> <span class="mm-text">Shop</span>
	</a></li>
	<!--my menu  -->
	<c:choose>
		<c:when test="${login}">
			<li class="mainmenu__item  megamenu-holder"><a href="./logout"
				class="mainmenu__link"> <span class="mm-text">logout</span>
			</a></li>

			<li class="mainmenu__item  megamenu-holder"><a href="./register"
				class="mainmenu__link"> <span class="mm-text"
					${ page!=null?page.equals("register")?"style='color: red; '": "":""}>Account</span>
			</a></li>
		</c:when>
		<c:otherwise>
			<li class="mainmenu__item  megamenu-holder"><a href="./login"
				class="mainmenu__link"> <span class="mm-text"
					${ page!=null?page.equals("login")?"style='color: red; '": "":""}>Login</span>
			</a></li>

			<li class="mainmenu__item  megamenu-holder"><a href="./register"
				class="mainmenu__link"> <span class="mm-text"
					${ page!=null?page.equals("register")?"style='color: red; '": "":""}>Register</span>
			</a></li>
		</c:otherwise>
	</c:choose>

	<li class="mainmenu__item"><a href="./cart"
		class="mainmenu__link" > <i class="la la-shopping-cart"></i> <span class="mm-text">Cart</span>
	</a></li>
	<li class="mainmenu__item"><a href="./product"
		class="mainmenu__link"> <span class="mm-text">Contact Us</span>
	</a></li>
		<!--my menu  -->
</ul>