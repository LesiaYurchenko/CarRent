<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>


<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CarRent</title>
</head>

<body>

<style type="text/css">
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

    .menu-2 input {
        background-color: #4960fb;
        border: 2px solid #071eb9;
        border-radius: 1px 10px;
        display: inline-block;
        transition: all 0.7s ease 0s;
    }

    .menu-2 a, form, input {
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
    <li><a href="${pageContext.request.contextPath}/main"><fmt:message key="label.Main" /></a></li>
    <li><a href="${pageContext.request.contextPath}/login.jsp"><fmt:message key="label.Login" /></a></li>
    <li><a href="${pageContext.request.contextPath}/registration.jsp"><fmt:message key="label.Registration" /></a></li>
    <fmt:message key="label.Language" />
    <li><a href="?lang=en"><fmt:message key="label.en" /></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.ua" /></a></li>

</ul>

<br>
<br>

<br>
<h1 align="center">
    <fmt:message key="label.Welcome" /> <br/>
</h1>
</body>
</html>