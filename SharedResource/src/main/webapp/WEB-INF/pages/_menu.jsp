<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="border: 1px solid #ccc;padding:5px;margin-bottom:20px;">

  <a href="${pageContext.request.contextPath}/welcome">Общий ресурс</a>

  | &nbsp;

   <a href="${pageContext.request.contextPath}/userInfo">График</a>

  | &nbsp;

  <a href="${pageContext.request.contextPath}/admin">Личный кабинет</a>

  <c:if test="${pageContext.request.userPrincipal.name != null}">

     | &nbsp;
     <a href="${pageContext.request.contextPath}/logout">Выйти</a>

  </c:if>

</div>