<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<%@ include file="/WEB-INF/include/HeaderView.jsp"%>
<%@ include file="/WEB-INF/include/BreadcrumbView.jsp"%>
<%@ include file="/WEB-INF/include/SideMenuView.jsp"%>
<div class="col-6">
	<div class="login-box">
	<c:if test="${attempt >0}">
			<div class="field">
				<h1>Wrong password or login</h1>
				wrong attempt ${attempt}
			</div>
		</c:if>
		<h3 class="heading__tertiary mb--30">Login</h3>
		<form class="form form--login" action="./login" method="post">
			<div class="form__group mb--20">
				<label class="form__label" for="username_email">Username or
					email address <span class="required">*</span>
				</label> <input type="text" class="form__input" id="username_email"
					name="login">
			</div>
			<div class="form__group mb--20">
				<label class="form__label" for="login_password">Password <span
					class="required">*</span></label> <input type="password"
					class="form__input" id="login_password" name="password">
			</div>
			<div class="d-flex align-items-center mb--20">
				<div class="form__group mr--30">
					<input type="submit" value="Log in" class="btn btn-size-sm">
				</div>
				<div class="form__group">
					<label class="form__label checkbox-label" for="store_session">
						<input type="checkbox" name="store_session" id="store_session">
						<span>Remember me</span>
					</label>
				</div>
			</div>
			<a class="forgot-pass" href="#">Lost your password?</a>
		</form>
	</div>
</div>
<!--  close div of SideMenuView -->
</div>
</div>
</div>
</div>
</div>
<%@ include file="/WEB-INF/include/FooterView.jsp"%>
