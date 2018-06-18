<%@ page import="com.blog.form.PostModel" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Пост</title>
    <link href="<spring:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<spring:url value='/css/global.css'/>" rel="stylesheet">
</head>
<body>
<jsp:include page="head.jsp"/>
<div class="container col-sm-8">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-xs-12 col-sm-12">
            <form:form id="post" ng-app="blogApp" ng-submit="submit()" name="post" ng-controller="post" class="form-horizontal" method="post" action="/post" modelAttribute="postModel" >
                <form:hidden name="action" path="action"/>
                <form:hidden name="id" path="post.id"/>
                <form:hidden name="date" path="post.date"/>
                <div>
                    <h3><c:choose>
                            <c:when test="${postModel.action=='create'}">
                                Создание
                            </c:when>
                            <c:otherwise>
                                Изменение
                            </c:otherwise>
                        </c:choose>
                        поста
                    </h3>
                </div>
                <hr/>
                <div class="form-group row">
                    <label for="post.caption" class="col-sm-1 control-label">Заголовок</label>
                    <div class="col-sm-11">
                        <form:input class="form-control"  placeholder="Заголовок"  ng-model="caption" required="required" path="post.caption"/>
                        <p style="color:red" ng-show="post['post.caption'].$dirty && post['post.caption'].$invalid && post['post.caption'].$error.required">Введите заголовок</p>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="post.body" class="col-sm-1 control-label">Описание</label>
                    <div class="col-sm-11">
                        <form:textarea rows="3" ng-model="body" class="form-control" placeholder="Описание" required="required" path="post.body"/>
                        <p style="color:red" ng-show="post['post.body'].$dirty && post['post.body'].$invalid && post['post.body'].$error.required">Введите описание</p>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="post.tags" class="col-sm-1 control-label">Теги</label>
                    <div class="col-sm-11">
                        <form:input ng-model="tags" class="form-control" placeholder="Теги" required="required" path="post.tags"/>
                        <p style="color:red" ng-show="post['post.tags'].$dirty && post['post.tags'].$invalid && post['post.tags'].$error.required">Введите теги</p>
                    </div>
                </div>

                <c:if test="${not empty postModel.error}">
                    <div class="panel-error">
                        Ошибка: ${postModel.error}<br>
                    </div>
                </c:if>
                <hr/>
                <div class="float-md-right">
                    <button type="button" id="main" class="btn btn-default" ng-click="goMain()" >На главную</button>
                    <button type="submit" id="save" ng-disabled="post.$invalid || block" class="btn btn-default">Сохранить</button>
                </div>
            </form:form>
        </div>
    </div>
</div>
<script src="<spring:url value='/js/angularjs.js'/>"></script>
<script>
    var app = angular.module('blogApp', []);
    app.controller('post',['$scope', '$window', function($scope, $window) {
        $scope.caption ="${postModel.post.caption}";
        $scope.block = false;
        <% PostModel postModel = (PostModel)request.getAttribute("postModel");
           String body = (postModel!= null && postModel.getPost()!= null &&postModel.getPost().getBody()!=null)?postModel.getPost().getBody().replace("\n", "\\n").replace("\r", "\\r"):"";
        %>
        $scope.body = "<%=body%>";
        $scope.tags ="${postModel.post.tags}";
        $scope.goMain = function(){
            $window.location.href="/main";
        }
        $scope.submit = function(){
          $scope.block = true;
        }
    }]);

</script>
</body>
</html>
