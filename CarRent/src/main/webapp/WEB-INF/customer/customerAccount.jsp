<%--
  Created by IntelliJ IDEA.
  User: Lesia Yurchenko
  Date: 6/2/2021
  Time: 7:54 PM
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
    <li><a href="${pageContext.request.contextPath}/customerBasis"><fmt:message key="label.OurCars" /></a></li>
    <li><a href="${pageContext.request.contextPath}/customerBookings"><fmt:message key="label.MyOrders" /></a></li>
    <li><a href="${pageContext.request.contextPath}/customerAccount"><fmt:message key="label.PersonalInformation" /></a></li>
    <li><a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.Logout" /></a></li>
    <fmt:message key="label.Language" />:
    <li><a href="?lang=en"><fmt:message key="label.en" /></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.ua" /></a></li>
</ul>
<br>

<h1>
    <fmt:message key="label.PersonalInformation" /> <br/>
</h1>
<br>
<br>
<h3>
    <fmt:message key="label.login" />: ${account.login}<br/>
    <br>
    <fmt:message key="label.password" />: ${account.password}<br/>
    <br>
    <fmt:message key="label.email" />: ${account.email}<br/>
    <br>
    <fmt:message key="label.blocked" />: ${account.blocked}<br/>
    <br>
    <br>
</h3>


</body>
</html>
