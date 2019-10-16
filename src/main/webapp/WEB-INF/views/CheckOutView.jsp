<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ include file="/WEB-INF/include/HeaderView.jsp"%>
<%@ include file="/WEB-INF/include/SideMenuView.jsp"%>
<c:if test="${login == true}">
	Congratulations on placing an order in our store. my team will contact you shortly
</c:if>
<c:if test="${login ==false}">
	For order processing you must be  <a href ="./login" >  loginIn</a>. 
	If you still don't have an account, please  <a href ="./register" >  create one </a>.
</c:if>
<!--  close div of SideMenuView -->
</div>
</div>
</div>
</div>
</div>
<%@ include file="/WEB-INF/include/FooterView.jsp"%>
