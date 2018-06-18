<%@ page import="com.blog.model.User" %>
<%@ page import="java.net.URLEncoder" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-md  fixed-top navbar-dark bg-primary">
    <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/main">Главная</a>
            </li>
        </ul>
    </div>
    <div class="mx-auto order-0">
        <a class="navbar-brand mx-auto" href="/welcome">Коллективный блог</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".dual-collapse2">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>
    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
        <ul class="navbar-nav ml-auto">
            <sec:authorize access="!isAuthenticated()">
                <li class="nav-item">
                    <a class="nav-link" href="/sign">Регистрация</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/logout">Вход</a>
                </li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <% User user = (User)session.getAttribute("authUser");%>
                <li class="nav-item active">
                    <% String url = request.getAttribute("javax.servlet.forward.request_uri") + (( request.getQueryString() != null ) ? "?" + request.getQueryString() : "" );%>
                    <a class="nav-link" href="/userinfo?user_id=<%=user.getId()%>&goback=<%= url%>"><%=user.getName()%></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/logout">Выход</a>
                </li>
        </span>
            </sec:authorize>
        </ul>
    </div>
</nav>
