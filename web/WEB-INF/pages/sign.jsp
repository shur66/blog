<%@ page import="com.blog.form.SignModel" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <title>Регистрация пользователя</title>
    <link href="<spring:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<spring:url value='/css/global.css'/>" rel="stylesheet">
</head>
<body>
<jsp:include page="head.jsp"/>
<div class="container col-sm-6">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-xs-10 col-sm-10">
        <form:form id="sign" ng-app="blogApp" name="sign" ng-controller="sign" class="form-horizontal" method="post" action="/sign" modelAttribute="signModel" >
            <div class="form-group row">
                <h3  class="col-sm-offset-6">Регистрация пользователя</h3>
            </div>
            <div class="form-group row">
                <label for="name" class="col-sm-3 control-label">ФИО</label>
                <div class="col-sm-9">
                    <form:input type="text" class="form-control"  placeholder="ФИО"  ng-model="name" name="name" required="required" path="user.name"/>
                    <p style="color:red" ng-show="sign['user.name'].$dirty && sign['user.name'].$invalid && sign['user.name'].$error.required">Введите ФИО</p>
                </div>
            </div>
            <div class="form-group row">
                <label for="user.login" class="col-sm-3 control-label">Логин</label>
                <div class="col-sm-9">
                    <form:input type="text" ng-model="login" class="form-control" placeholder="Логин" required="required" path="user.login"/>
                    <p style="color:red" ng-show="sign['user.login'].$dirty && sign['user.login'].$invalid && sign['user.login'].$error.required">Введите логин</p>
                </div>
            </div>
            <div class="form-group row">
                <label for="user.password" class="col-sm-3 control-label">Пароль</label>
                <div class="col-sm-9">
                    <form:password ng-model="password" class="form-control" placeholder="Пароль" required="required" path="user.password" />
                    <p style="color:red" ng-show="sign['user.password'].$dirty && sign['user.password'].$invalid && sign['user.password'].$error.required">Введите пароль</p>
                </div>
            </div>
            <div class="form-group row">
                <label for="passwordConfirm" class="col-sm-3 control-label">Подтверждение</label>
                <div class="col-sm-9">
                    <form:password name="passwordConfirm" ng-model="passwordConfirm" id="passwordConfirm" class="form-control" placeholder="Подтверждение пароля" required="required" path="passwordConfirm"/>
                    <p style="color:red" ng-show="sign['passwordConfirm'].$dirty && sign['passwordConfirm'].$invalid && sign['passwordConfirm'].$error.required">Введите подтверждение пароля</p>
                    <p style="color:red" ng-show="sign['user.password'].$valid && sign['passwordConfirm'].$valid && !(passwordConfirm===password)">Пароли не совпадают</p>
                </div>
            </div>
            <div class="form-group row">
                <label for="email" class="col-sm-3 control-label">Email</label>
                <div class="col-sm-9">
                    <form:input type="email" class="form-control disNone" ng-model="email" id="email" name="email" value="" placeholder="Введите email" path="user.email" />
                    <p style="color:red" ng-show="sign['user.email'].$error.email">Ошибка формата email</p>
                </div>
            </div>
            <c:if test="${not empty signModel.error}">
                <div class="panel-error">
                    Ошибка: ${signModel.error}<br>
                </div>
            </c:if>
            <div class="float-md-right">
                <button type="submit" id="save" ng-disabled="sign.$invalid || !(passwordConfirm===password)" class="btn btn-default">Сохранить</button>
            </div>
        </form:form>
        </div>
    </div>
</div>
<script src="<spring:url value='/js/angularjs.js'/>"></script>
<script>
    var app = angular.module('blogApp', []);
    app.controller('sign', function($scope) {
        $scope.name ="${signModel.user.name}";
        $scope.login ="${signModel.user.login}";
        $scope.email ="${signModel.user.email}";
    });

</script>
</body>
</html>
