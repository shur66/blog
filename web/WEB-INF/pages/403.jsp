<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
  <body>
    <h1>HTTP Status 403 - Нет доступа</h1>
    <c:choose>
      <c:when test="${empty username}">
       <h2>У вас нет доступа!</h2>
      </c:when>
      <c:otherwise>
        <h2>Пользователь : <b>${username}</b> <br/>У вас нет доступа!</h2>
      </c:otherwise>
    </c:choose>
  </body>
</html>