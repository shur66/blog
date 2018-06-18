<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Пользователь</title>
    <link href="<spring:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<spring:url value='/css/global.css'/>" rel="stylesheet">
</head>
<body>
<jsp:include page="head.jsp"/>
<div class="container col-sm-3">
    <div class="row row-offcanvas row-offcanvas-right">
            <form:form id="sign" ng-app="blogApp" name="main" ng-controller="userinfo" class="form-horizontal" modelAttribute="userInfoModel">
                <div class="form-group row">
                    <h3  class="col-sm-offset-1">Информация пользователя</h3>
                </div>
                <hr/>
                <table class="table table-striped ">
                    <tbody>
                        <tr>
                            <th scope="row">ФИО</th>
                            <td><c:out value="${userInfoModel.user.name}"/></td>
                        </tr>
                        <tr>
                            <th scope="row">Логин</th>
                            <td><c:out value="${userInfoModel.user.login}"/></td>
                        </tr>
                        <tr>
                            <th scope="row">Email</th>
                            <td><c:out value="${userInfoModel.user.email}"/></td>
                        </tr>
                        <tr>
                            <th scope="row">Постов</th>
                            <td><c:out value="${userInfoModel.user.posts.size()}"/></td>
                        </tr>
                    </tbody>
                </table>
                <c:if test="${not empty userInfoModel.error}">
                    <div class="panel-error">
                        Ошибка: ${userInfoModel.error}<br>
                    </div>
                </c:if>
                <hr/>
                <div class="float-md-right">
                    <button type="button" id="save" ng-click="goBack()" class="btn btn-default">Назад</button>
                </div>
            </form:form>

    </div>
</div>

<script src="<spring:url value='/js/angularjs.js'/>"></script>
<script src="<spring:url value='/js/angular-route.js'/>"></script>
<script>
    var app = angular.module('blogApp', ['ngRoute']);
    app.controller('userinfo',['$scope', '$window',  function($scope, $window) {
        $scope.goBack = function(id){
            $window.location.href="<c:out value="${userInfoModel.goBack}"/>";
        }
    }]);

</script>
</body>
</html>
