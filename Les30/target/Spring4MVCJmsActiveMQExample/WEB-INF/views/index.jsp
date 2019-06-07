<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Chat System</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>

	<div class="generic-container">
	<div class="well lead">Добро пожаловать в чат!</div>
			<span class="well floatRight">
                <c:choose>
                   <c:when test="${user.username == null}">
                        <a href="<c:url value='/login' />">Войти в систему</a>
                   </c:when>
                   <c:otherwise>
                        <a href="<c:url value='/logout' />">Выйти из системы</a>
                   </c:otherwise>
                </c:choose>
			</span>
	</div>

</body>
</html>