<%--
  Created by IntelliJ IDEA.
  User: Lesia Yurchenko
  Date: 6/3/2021
  Time: 12:57 PM
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
    <meta charset="UTF-8">
    <title>Countries</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">
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

    .menu-3 table {
        width: 80%;
        margin: auto;
    }

</style>

<ul class="menu-2">
    <li><a href="${pageContext.request.contextPath}/adminCustomers"><fmt:message key="label.Customers" /></a></li>
    <li><a href="${pageContext.request.contextPath}/adminManagers"><fmt:message key="label.Managers" /></a></li>
    <li><a href="${pageContext.request.contextPath}/adminCars"><fmt:message key="label.Cars" /></a></li>
    <li><a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.Logout" /></a></li>
    <fmt:message key="label.Language" />:
    <li><a href="?lang=en"><fmt:message key="label.en" /></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.ua" /></a></li>
</ul>

<main class="menu-3">
    <br>
    <br>
    <h1 align="center"><fmt:message key="label.Customers" /></h1>
    <br>
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th><fmt:message key="label.Login" /></th>
            <th><fmt:message key="label.Password" /></th>
            <th><fmt:message key="label.email" /></th>
            <th><fmt:message key="label.Role" /></th>
            <th><fmt:message key="label.Blocked" /></th>
            <th><fmt:message key="label.Block" /></th>
            <th><fmt:message key="label.Unblock" /></th>
        </tr>
        <c:forEach var="i" items="${customers}">
        <tr>
            <td>${i.login}</td>
            <td>${i.password}</td>
            <td>${i.email}</td>
            <td>${i.role}</td>
            <td align="center">${i.blocked}</td>
            <td align="center">
                <form method="post" action="${pageContext.request.contextPath}/adminChanges">
                    <input type="hidden" name="id" value="${i.id}"/>
                    <input type="hidden" name="act" value="Block Customer"/>
                    <input class="button" type="submit" name="action"
                           value="<fmt:message key="label.BlockCustomer" />">
                </form>
            </td>
            <td align="center">
                <form action="${pageContext.request.contextPath}/adminChanges" method="post">
                    <input type="hidden" name="id" value="${i.id}"/>
                    <input type="hidden" name="act" value="Unblock Customer"/>
                    <input class="button" type="submit" name="action"
                           value="<fmt:message key="label.UnblockCustomer" />">
                </form>
            </td>
            </c:forEach>
    </table>
    <nav aria-label="Navigation for customers">
        <ul class="pagination justify-content-center">
            <c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/adminCustomers?currentPage=${currentPage-1}"><fmt:message key="label.Previous" /></a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link">
                                ${i} <span class="sr-only">(current)</span></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/adminCustomers?currentPage=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/adminCustomers?currentPage=${currentPage+1}"><fmt:message key="label.Next" /></a>
                </li>
            </c:if>
        </ul>
    </nav>
</main>

</body>
</html>

