<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ include file="/WEB-INF/include/HeaderView.jsp"%>
<%@ include file="/WEB-INF/include/BreadcrumbView.jsp"%>
<%@ include file="/WEB-INF/include/SideMenuView.jsp"%>
<!-- Main Content Wrapper Start -->
<div class="col-9">
	<div class="table-content table-responsive">
		<table class="table text-center">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th class="text-left">Product</th>
					<th>price</th>
					<th>quantity</th>
					<th>total</th>
					<th>remove</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="product" items="${requestScope.productList}">
					<tr id="productRow${product.key.id}">
						<td class="product-thumbnail text-left"><img
							src="./productImage/${product.key.id}.JPG" alt="Product Thumnail"></td>
						<td class="product-name text-left wide-column">
							<h3>
								<a href="product-details.html">${product.key.name}</a>
							</h3>
						</td>
						<td class="product-price"><span class="product-price-wrapper">
								<span class="money" id="price${product.key.id}">${product.key.price}</span>
						</span></td>
						<td class="product-quantity"><img width="20px"
							src="./img/-.png" id="imgMinus"
							onclick="minus(${product.key.id})" /> <span
							id="span${product.key.id}">${product.value}</span> <img
							width="20px" src="./img/+.png" id="imgPlus"
							onclick="plus(${product.key.id})" /></td>
						<td class="product-total-price"><span
							class="product-price-wrapper"> <span class="money"
								id="totalPrice${product.key.id}">${product.value*product.key.price}</span>
						</span></td>
						<td class="product-remove text-left"><input type="hidden"
							name="productToRemove" value="${product.key.id}" /> <input
							type="submit" value="remove"
							onclick="removeProduct(${product.key.id})"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="row no-gutters border-top pt--20 mt--20">
		<div class="col-sm-6 text-sm-right">
			<button type="submit" class="cart-form__btn">Clear Cart</button>
		</div>
	</div>
	<div class="cart-collaterals">
		<div class="cart-totals">
			<h5 class="font-size-14 font-bold mb--15">Cart totals</h5>
			<div class="cart-calculator__item order-total">
				<div class="cart-calculator__item--head">
					<span>Total</span>
				</div>
				<div class="cart-calculator__item--value">
					<span class="product-price-wrapper"> <span class="money"
						id="totalSum">${totalSumm}</span>
					</span>
				</div>
			</div>
		</div>
	</div>
	<a href="checkout.html"
		class="btn btn-size-md btn-shape-square btn-fullwidth"> Proceed To
		Checkout </a>
</div>
<script>
// script for numbers
	function plus(id) {
		var number = document.getElementById("span" + id).innerHTML;
		number++;
		document.getElementById("span" + id).innerHTML = number;
		showProduct(id)
	}
	function minus(id) {
		var number = document.getElementById("span" + id).innerHTML;
		if(number>1){
		number--;
		document.getElementById("span" + id).innerHTML = number;
		showProduct(id);
		}
		
	}
	function showProduct(id) {
		var number = document.getElementById("span" + id).innerHTML;
		$.ajax({
					url : "./cart",
					type : "POST",
					dataType : "html",
					data : {productToChange: id,
						numberOfGoods: number},
					success : function(responseJson) {
					var returnedData = JSON.parse(responseJson);
				document.getElementById("numberGoods").innerHTML = returnedData["numberOfGoods"];
				document.getElementById("totalSum").innerHTML = returnedData["totalCartSum"];
				document.getElementById("totalPrice" + id).innerHTML = number * document.getElementById("price" + id).innerHTML;
					},
					error : function(response) { // Данные не отправлены
						document.getElementById(result_form).innerHTML = "Ошибка. Данные не отправленны.";
					}
				});
				
	}
	function removeProduct(id) {
		document.getElementById("productRow" +id ).innerHTML ="";
		$.ajax({
					url : "./cart",
					type : "POST",
					dataType : "html",
					data : {productToRemove: id},
					success : function(responseJson) {
						var returnedData = JSON.parse(responseJson);
				document.getElementById("numberGoods").innerHTML = returnedData["numberOfGoods"];
				document.getElementById("totalSum").innerHTML = returnedData["totalCartSum"];
					},
					error : function(response) { // Данные не отправлены
						document.getElementById(result_form).innerHTML = "Ошибка. Данные не отправленны.";
					}
				});
	}
</script>
<!--  close div of SideMenuView -->
</div>
</div>
</div>
</div>
</div>
<!-- Main Content Wrapper Start -->

<%@ include file="/WEB-INF/include/FooterView.jsp"%>