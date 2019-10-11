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
							<div class="cart-side-menu">
								<c:if test="${login}">
									Hello ${userName} <br />
								</c:if>
								you have <span id = "numberGoods">${items}</span> goods in cart
							</div>
							<a class="nav-link active" href="./product">Home</a> <a
								class="nav-link" href="./product" aria-controls="orders"
								aria-selected="true">Shop</a> <a class="nav-link"
								href="./product?category=1" aria-controls="downloads"
								aria-selected="true">Iphone</a> <a class="nav-link"
								href="./product?category=2" aria-controls="addresses"
								aria-selected="true">Ipad</a> <a class="nav-link"
								href="./product?category=3" aria-controls="accountdetails"
								aria-selected="true">Watch</a> <a class="nav-link" href="./cart">Cart</a>
							<a class="nav-link" href="./login">Login</a> <a class="nav-link"
								href="./register">Register</a>
						</div>
					</div>
				</div>