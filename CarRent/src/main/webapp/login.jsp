<%--
  Created by IntelliJ IDEA.
  User: Lesia Yurchenko
  Date: 5/24/2021
  Time: 5:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>


<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title>Login in system</title>

</head>
<body>
<style type="text/css">
    .menu {
        margin: 0;
        padding: 0;
        height: auto;
        list-style-type: none;
        text-align: center;
    }

    .menu-2 {
        margin: 0;
        padding: 0;
        height: auto;
        list-style-type: none;
        background-color: #6699ff;
        text-align: center;
    }

    .menu-2 li {
        margin: 8px 10px 8px 0;
        padding: 2px 14px 4px;
        background-color: #4960fb;
        border: 2px solid #071eb9;
        border-radius: 1px 10px;
        display: inline-block;
        transition: all 0.7s ease 0s;
    }

    .menu-2 a {
        color: #f2f2f2;
        text-decoration: none;
    }

    .menu-2 li:hover {
        background-color: #ccc;
        border-radius: 10px 1px;
    }

    .menu-2 li:hover a {
        color: #0e25c0;
    }

</style>

<br>

<ul class="menu-2">
    <li><a href="${pageContext.request.contextPath}/main"> <fmt:message key="label.Main" /></a></li>
    <li><a href="${pageContext.request.contextPath}/registration.jsp"> <fmt:message key="label.Registration" /></a></li>
    <fmt:message key="label.Language" />
    <li><a href="?lang=en"> <fmt:message key="label.en" /></a></li>
    <li><a href="?lang=ua"> <fmt:message key="label.ua" /></a></li>
</ul>
<br>
</style>
<br>
<br>
<h1 align="center"><fmt:message key="label.LoginPlease" /></h1><br/>
<br>
<form class="menu" method="post" action="${pageContext.request.contextPath}/login">
    <input type="text" name="login" required placeholder="<fmt:message key="label.login" />"><br/>
    <input type="password" name="pass" required placeholder="<fmt:message key="label.password" />"><br/><br/>
    <input class="button" type="submit" value="<fmt:message key="label.Login"/>">
</form>

</body>
</html>
