<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Chat</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>

 	<div class="generic-container">
		<div class="well lead">Чат</div>
		<table class="table table-hover table-striped">
			<thead>
				<tr>
					<th>Пользователь</th>
					<th>Сообщение</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orders}" var="entry">
				<tr>
					<td>${entry.value.from.username}</td>
					<td>${entry.value.message}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<span class="well floatRight">
			<a href="<c:url value='/message' />">Отправить сообщение</a>
			<br>
			<a href="<c:url value='/logout' />">Выйти из системы</a>
		</span>
	</div>
</body>
</html>