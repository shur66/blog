<%@ page import="com.blog.form.PostCommentModel" %>
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
<div class="container">
    <div class="">
            <form:form id="comment" ng-app="blogApp" name="main" ng-controller="main" class="form-horizontal" action="/postcomment" method="post" modelAttribute="postCommentModel">
                <form:input cssStyle="display: none" ng-model="postId" path="post.id"/>
                <form:input cssStyle="display: none" ng-model="id" path="postComment.id"/>
                <form:input cssStyle="display: none" ng-model="action" path="action"/>
                <div>
                    <h1>Пост: ${postCommentModel.post.caption}</h1>
                </div>
                <hr/>
                <div >
                    <pre><h6>${postCommentModel.post.body}</h6></pre>
                </div>
                <label ng-repeat="tag in tags" ><span class="badge badge-primary">&#35;{{tag}}</span>&nbsp;</label>
                <hr/>
                <a href="/userinfo?user_id=${postCommentModel.post.user.id}&goback=/postcomment%3Fid=${postCommentModel.post.id}">${postCommentModel.post.user.name}</a>
                ${postCommentModel.dateFormat}
                <hr/>
                <table class="table table-striped" style="margin-top: 10px">
                    <tbody >
                    <tr ng-repeat="comment in comments">
                        <th style="width: 5%;" scope="row">{{$index+1}}</th>
                        <td style="width: 15%; text-align: center;"><a ng-href="/userinfo?user_id={{comment.user_id}}&goback=/postcomment%3Fid={{comment.post_id}}">{{comment.user_name}}</a><br/>{{comment.date}}</td>
                        <td>{{comment.comment}}</td>
                        <td style="width: 5%;">
                            <button ng-show="comment.owner" ng-disabled="block" type="button" class="btn btn-primary btn-rounded btn-sm my-0" ng-click="delete(comment.id)">Удалить</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <c:if test="${not empty postCommentModel.error}">
                    <div class="panel-error">
                        Ошибка: ${postCommentModel.error}<br>
                    </div>
                </c:if>
                <form:textarea ng-model="comment" id="comment" name="comment" path="postComment.comment" style="width: 100%;" rows="3"/>
                <hr/>
                <div class="float-md-right">
                    <button type="button" id="main" class="btn btn-default" ng-click="goMain()" >На главную</button>
                    <button type="button" id="but" ng-disabled="comment==undefined || comment=='' || block" class="btn btn-default" ng-click="create()" >Комментировать</button>
                </div>
            </form:form>

    </div>
</div>

<script src="<spring:url value='/js/angularjs.js'/>"></script>
<script src="<spring:url value='/js/angular-route.js'/>"></script>
<script>
    var app = angular.module('blogApp', ['ngRoute']);
    app.controller('main',['$scope', '$window', '$http',  function($scope, $window, $http) {
       <% PostCommentModel postCommentModel = (PostCommentModel)request.getAttribute("postCommentModel");%>
        $scope.comments = <%=postCommentModel.getCommentsJson()%>;
        $scope.tags = <%=postCommentModel.getTagsJson()%>;
        $scope.postId = <%=postCommentModel.getPost().getId()%>;
        $scope.block = false;
        /**
         * Удаление комментария
         */
        $scope.delete = function(id){
            $scope.block = true;
            var param = {"postComment.id": id, "action":"delete", "post.id":$scope.postId, "${_csrf.parameterName}": "${_csrf.token}"};
            $http({method: 'POST'
                 , url: '/postcomment'
                 , params:param}).then(
                    function(response){
                        if (response.data.result =="1"){
                            $scope.comments = response.data.comments;
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
        /**
        * Созддание комментария
         */
        $scope.create = function(){
            $scope.block = true;
            var param = {"postComment.comment": $scope.comment, "action":"create", "post.id":$scope.postId, "${_csrf.parameterName}": "${_csrf.token}" };
            $http({method: 'POST'
                , url: '/postcomment'
                , params:param}).then(
                    function(response){
                        if (response.data.result =="1"){
                            $scope.comment = "";
                            $scope.block = false;
                            $scope.comments = response.data.comments;
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
        $scope.goMain = function(){
            $window.location.href="/main";
        }
    }]);

</script>
</body>
</html>
