<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Send message</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>

 	<div class="generic-container">
	<div class="well lead">Введите сообщение</div>
 	<form:form method="POST" modelAttribute="message" class="form-horizontal">
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="message">Сообщение</label>
				<div class="col-md-7">
					<form:input type="text" path="message" id="message" class="form-control input-sm" required="required"/>
					<div class="has-error">
						<form:errors path="message" class="help-inline"/>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-actions floatRight">
				<input type="submit" value="Отправить сообщение" class="btn btn-primary btn-sm"/>
				<a href="<c:url value='/home' />">Отменить</a>
			</div>
		</div>
	</form:form>
	</div>
</body>
</html>