<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<div class="main-content-wrapper">
	<div class="page-content-inner ptb--80 ptb-md--60 pb-sm--55">
		<div class="container">
			<div class="row">
				<div class="col-3">
					<div class="user-dashboard-tab flex-column flex-md-row">
						<div class="user-dashboard-tab__head nav flex-md-column"
							role="tablist" aria-orientation="vertical">
							<div>
								<ul class="breadcrumb">
									<li><a href="/">Home</a></li>
									<li class="current"><span>/${page}</span></li>
								</ul>
							</div>
							<div class="cart-side-menu">
								<c:if test="${login}">
									Hello ${userName} <br />
								</c:if>
								you have <span id="numberGoods">${items}</span> goods in cart
							</div>
							<a class="nav-link ${page!=null?page.equals("main")?"active": "":""}"
							href="./">Home</a> 
							<a class="nav-link ${page!=null?page.equals("product")?"active": "":""}"
							 href="./product" aria-selected="true">Shop</a> 
							<a class="nav-link ${page!=null?page.equals("1")?"active": "":""}"
							 href="./product?category=1" 
							aria-selected="true">Iphone</a> 
							<a class="nav-link ${page!=null?page.equals("2")?"active": "":""}"
							href="./product?category=2" 
							aria-selected="true">Ipad</a>
							<a class="nav-link ${page!=null?page.equals("3")?"active": "":""}"
							href="./product?category=3" 
							aria-selected="true">Watch</a> 
							<a class="nav-link ${page!=null?page.equals("cart")?"active": "":""}"
							href="./cart">Cart</a>
							<a class="nav-link ${page!=null?page.equals("login")?"active": "":""}"
							href="./login">Login</a> 
							<a class="nav-link  ${page!=null?page.equals("register")?"active": "":""}"
							href="./register">Register</a>
						</div>
					</div>
				</div>