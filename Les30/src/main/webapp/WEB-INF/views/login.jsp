<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login chat</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>

 	<div class="generic-container">
	<div class="well lead">Введите логин для чата</div>
 	<form:form method="POST" modelAttribute="user" class="form-horizontal">
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="user">Введите имя</label>
				<div class="col-md-7">
					<form:input type="text" path="username" id="username" class="form-control input-sm" required="required"/>
					<div class="has-error">
						<form:errors path="username" class="help-inline"/>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-actions floatRight">
				<input type="submit" value="Войти" class="btn btn-primary btn-sm"/>
			</div>
		</div>
	</form:form>
	<p>${error}</p>
	</div>
</body>
</html>