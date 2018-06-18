<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
    <link href="<spring:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<spring:url value='/css/global.css'/>" rel="stylesheet">
</head>
<body>
<jsp:include page="head.jsp"/>
<div class="container">
    <div>
        <form:form ng-submit="submit()" id="main" ng-app="blogApp" nv-model="form" name="main" ng-controller="main" class="form-horizontal" action="/post"
                   method="post">
            <input type="text" style="display: none" ng-model="id" name="post.id"/>
            <input type="text" style="display: none" ng-model="action" name="action"/>

            <h3>Посты</h3>

            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Пост</th>
                    <th scope="col">Пользователь</th>
                    <th scope="col">Дата</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="post in posts">
                    <th scope="row">{{$index+1}}</th>
                    <td><a ng-href="/postcomment?id={{post.id}}">{{post.caption}}</a></td>
                    <td><a ng-href="/userinfo?user_id={{post.user_id}}&goback=/main">{{post.user_name}}</a></td>
                    <td>{{post.date}}</td>
                    <td>
                        <button ng-show="post.owner" type="button" class="btn btn-primary btn-rounded btn-sm my-0"
                                ng-click="update(post.id)">Изменить
                        </button>
                        <button ng-show="post.owner" type="submit" class="btn btn-primary btn-rounded btn-sm my-0"
                                ng-click="delete(post.id)" ng-disabled="block">Удалить
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
            <c:if test="${not empty error}">
                <div class="panel-error">
                    Ошибка: ${error}<br>
                </div>
            </c:if>
            <hr/>
            <div class="float-md-right">
                <button type="button" id="save" ng-click="create()" class="btn btn-default">Создать пост</button>
            </div>
        </form:form>
    </div>
</div>

<script src="<spring:url value='/js/angularjs.js'/>"></script>
<script src="<spring:url value='/js/angular-route.js'/>"></script>
<script>
    var app = angular.module('blogApp', ['ngRoute']);
    app.controller('main', ['$scope', '$window','$http', function ($scope, $window, $http) {
        <% String posts = (String)request.getAttribute("posts");%>
        $scope.posts = <%=posts%>;
        $scope.block = false;

        $scope.submit = function (){
            $scope.block = true;
        }
        $scope.delete = function (id) {
            $scope.block = true;
            var param = {"id": id, "${_csrf.parameterName}": "${_csrf.token}"};
            $http({method: 'DELETE'
                , url: '/post'
                , params:param}).then(
                    function(response){
                        if (response.data.result =="1"){
                            $scope.posts = response.data.posts;
                            $scope.block = false;
                        }else{
                            $scope.block = false;
                            alert(response.data.result);
                        }
                    },
                    function(response){
                        $scope.block = false;
                        alert("failure status:"+response.status);
                    });
        }
        $scope.update = function (id) {
            $window.location.href = '/post?action=update&id=' + id;
        }
        $scope.create = function () {
            $window.location.href = '/post?action=create&id=';
        }
    }]);

</script>
</body>
</html>
