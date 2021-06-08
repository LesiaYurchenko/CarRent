<%--
  Created by IntelliJ IDEA.
  User: Lesia Yurchenko
  Date: 6/3/2021
  Time: 1:26 PM
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
    <title>Bookings</title>
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
    <li><a href="${pageContext.request.contextPath}/managernewbookings"><fmt:message key="label.NewBookings" /></a></li>
    <li><a href="${pageContext.request.contextPath}/managerusebookings"><fmt:message key="label.BookingsInUse" /></a></li>
    <li><a href="${pageContext.request.contextPath}/managerreturnedbookings"><fmt:message key="label.ReturnedBookings" /></a></li>
    <li><a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.Logout" /></a></li>
    <fmt:message key="label.Language" />:
    <li><a href="?lang=en"><fmt:message key="label.en" /></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.ua" /></a></li>
</ul>
<br>
<br>

<h1>
    <fmt:message key="label.ReturnedBookings" /><br/>
</h1>
<table width="80%" cellspacing="1" cellpadding="4" border="1">
    <tr>
        <th><fmt:message key="label.Customer" /></th>
        <th><fmt:message key="label.Passport" /></th>
        <th><fmt:message key="label.Cars" /></th>
        <th><fmt:message key="label.LeaseTerm" /></th>
        <th><fmt:message key="label.Driver" /></th>
        <th><fmt:message key="label.Status" /></th>
        <th><fmt:message key="label.Damage" /></th>
        <th><fmt:message key="label.DamagePaid" /></th>
    </tr>
    <c:forEach var="i" items="${returnedBookings}">
    <tr>
        <td><fmt:message key="label.login" />: ${i.account.login}<br/>
            <fmt:message key="label.email" />: ${i.account.email}<br/>
            <fmt:message key="label.blocked" />: ${i.account.blocked}</td>
        <td>${i.passport}</td>
        <td><c:forEach var="c" items="${i.cars}">
            <fmt:message key="label.Model" />: ${c.model}<br/>
            <fmt:message key="label.QualityClass" />: ${c.qualityClass}<br/>
            <fmt:message key="label.Price" />: ${c.price}<br><br>
        </c:forEach></td>
        <td>${i.leaseTerm}</td>
        <td align="center">${i.driver}</td>
        <td>${i.status}</td>
        <td align="center">${i.damage}</td>
        <td align="center">${i.damagePaid}</td>
        </c:forEach>
</table>
<br>
<br>

</body>
</html>

