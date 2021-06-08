<%--
  Created by IntelliJ IDEA.
  User: Lesia Yurchenko
  Date: 6/3/2021
  Time: 5:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.gmail.lesiiayurchenko.model.entity.Car.QualityClass" %>

<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>


<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Car List</title>
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
        border-radius: 1px 20px;
        display: inline-block;
        transition: all 0.7s ease 0s;
    }
    .menu-2 a {
        color: #f2f2f2;
        text-decoration: none;
    }
    .menu-2 li:hover {
        background-color: #ccc;
        border-radius: 20px 1px;
    }
    .menu-2 li:hover a {
        color: #0e25c0;
    }
</style>
<br>
<ul class="menu-2">
    <li><a href="${pageContext.request.contextPath}/customerbasis"><fmt:message key="label.OurCars" /></a></li>
    <li><a href="${pageContext.request.contextPath}/customerbookings"><fmt:message key="label.MyOrders" /></a></li>
    <li><a href="${pageContext.request.contextPath}/customeraccount"><fmt:message key="label.PersonalInformation" /></a></li>
    <li><a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.Logout" /></a></li>
    <fmt:message key="label.Language" />:
    <li><a href="?lang=en"><fmt:message key="label.en" /></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.ua" /></a></li>
</ul>
<br>


<h1 align="center">
    <fmt:message key="label.ThanksOrder"/><br/>
    <br>
    <fmt:message key="label.CheckProgress"/><br/>
    <br>
</h1>


</body>
</html>
