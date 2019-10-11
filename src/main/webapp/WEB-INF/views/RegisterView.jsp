<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<%@ include file="/WEB-INF/include/HeaderView.jsp"%>
<%@ include file="/WEB-INF/include/BreadcrumbView.jsp"%>
<%@ include file="/WEB-INF/include/SideMenuView.jsp"%>
<div class="col-6">
${errorText}
<form action="./register" method="post" class="form form--account">
	<div class="row mb--20">
		<div class="col-12">
			<div class="form__group">
				<label class="form__label" for="login">Login (email address)
					<span class="required">*</span></label> 
					<input type="email" name="login" id="login" class="form__input"
					required placeholder="enter login here" value="${user.login}" >
			</div>
		</div>
	</div>

	<div class="row mb--20">
		<div class="col-12">
			<div class="form__group">
				<label class="form__label" for="password">New password <span
					class="required">*</span></label> <input type="password" name="password"
					class="form__input" required id="password"
					placeholder="type password here"
					pattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9]{8,}">
			</div>
		</div>
	</div>
	<div class="row mb--20">
		<div class="col-12">
			<div class="form__group">
				<label class="form__label" for="password2">Confirm new
					password<span class="required">*</span>
				</label> <input type="password" name="password2" required id="password2"
					class="form__input" placeholder="type password here"
					pattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9]{8,}" >
			</div>
		</div>
	</div>
	<div class="row mb--20">
		<div class="col-12">
			<div class="form__group">
				<label class="form__label" for="name">Display name <span
					class="required">*</span></label> 
					<input type="text" name="name" id="name" value ="${user.name}" 
					class="form__input" required> <span class="form__notes"><em>This
						will be how your name will be displayed in the account section and
						in reviews</em></span>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-12">
			<div class="form__group">
				<label class="form__label">Select region </label> <select
					name="region">
					<option value="DNR"
						${user.region!=null?(user.region.equals("DNR")?"selected": ""): ""}>
						DNR</option>
					<option value="LNR"
						${user.region!=null?(user.region.equals("LNR")?"selected": ""): ""}>
						LNR</option>
					<option value="UKR"
						${user.region!=null?(user.region.equals("UKR")?"selected": ""): ""}>
						UKR</option>
				</select>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-12">
			<div class="form__group">
				<label class="form__label">Select gender </label>
				<div class="radioDiv">
					<input type="radio" class="radio" name="gender" value="Male"
						id="radio-1" required ${user.gender?"checked":""}>
					<label for="radio-1"> Male</label>
				</div>
				<div class="radioDiv">
					<input type="radio" class="radio" id="radio-2" name="gender"
						value="Female" required
							    ${user.gender?"":"checked"} />
					<label for="radio-2"> Female</label>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-12">
			<div class="form__group">
				<label class="form__label"> Leave some comment </label>
				<textarea name="comment" placeholder="leave comment here" required
					rows="5">
							${user.comment}
							</textarea>

			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-12">
			<div class="form__group">
				<label class="form__label"><input type="checkbox"
					name="acceptOffer" required />I accept offer</label>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-12">
			<div class="form__group">
				<input type="submit" value="Create account" class="btn btn-size-md">
			</div>
		</div>
	</div>
	</form>
</div>
	<!--  close div of SideMenuView -->
</div>
</div>
</div>
</div>
</div>
<%@ include file="/WEB-INF/include/FooterView.jsp"%>
	