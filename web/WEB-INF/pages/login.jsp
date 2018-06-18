<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <title>Авторизация</title>
    <link href="<spring:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<spring:url value='/css/signin.css'/>" rel="stylesheet">
    <link href="<spring:url value='/css/global.css'/>" rel="stylesheet">
</head>
<body>
    <jsp:include page="head.jsp"/>
    <div class="container">
        <form:form class="form-signin" action="/login" method="post">
            <h2 class="form-signin-heading">Авторизация</h2>
            <label for="login" class="sr-only">Логин</label>
            <input type="text" id="login" name="login" class="form-control" placeholder="Логин" required autofocus>
            <label for="password" class="sr-only">Пароль</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Пароль" required>
            <c:if test="${not empty error}">
                <div class="panel-error">
                    Ошибка: ${error}<br>
                </div>
            </c:if>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
        </form:form>
    </div>
</body>
</html>
