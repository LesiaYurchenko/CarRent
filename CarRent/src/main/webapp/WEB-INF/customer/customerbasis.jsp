<%--
  Created by IntelliJ IDEA.
  User: Lesia Yurchenko
  Date: 5/31/2021
  Time: 3:33 PM
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
    <meta charset="UTF-8">
    <title>Countries</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">
</head>

<body>

<style type="text/css">
    .menu-1 {
        margin: 0;
        padding: 0;
        height: auto;
        list-style-type: none;
        text-align: center;
    }

    .menu-1 form {
        margin: 8px 10px 8px 0;
        padding: 2px 14px 4px;
        display: inline-block;
    }

    .menu-1 input:hover {
        background-color: #ccc;
        border-radius: 10px 1px;
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

    .menu-2 form {
        margin: 8px 10px 8px 0;
        padding: 2px 14px 4px;
        border: 2px solid #071eb9;
        border-radius: 1px 10px;
        display: inline-block;
        transition: all 0.7s ease 0s;
    }

    .menu-3 table {
        width: 80%;
        margin: auto;
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

<h1 align="center"><fmt:message key="label.OurCars" /></h1>

<ul class="menu-1">
    <form method="post" action="${pageContext.request.contextPath}/customerbasis">
        <input type="hidden" name="id" value="price"/>
        <input class="button" type="submit" name="sorter" value="<fmt:message key="label.SortByPrice" />">
    </form>

    <form method="post" action="${pageContext.request.contextPath}/customerbasis">
        <input type="hidden" name="id" value="qualityClass"/>
        <input class="button" type="submit" name="filter" value="<fmt:message key="label.FilterByQualityClass" />">
        <select class="selectpicker" name="qualityClass">
            <c:forEach items="<%=QualityClass.values()%>" var="qualityClass">
                <option value="${qualityClass.name()}">${qualityClass.toString()}</option>
            </c:forEach>
        </select><br/>
    </form>
</ul>

<main class="menu-3">
    <br>
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th><fmt:message key="label.Model" /></th>
            <th><fmt:message key="label.QualityClass" /></th>
            <th><fmt:message key="label.Price" /></th>
            <th><fmt:message key="label.ADD" /></th>
            <th><fmt:message key="label.DELETE" /></th>
        </tr>
        <c:forEach var="i" items="${cars}">
        <tr>
            <td>${i.model}</td>
            <td>${i.qualityClass}</td>
            <td>${i.price}</td>
            <td align="center">
                <form action="${pageContext.request.contextPath}/customerbook" method="post">
                    <input type="hidden" name="id" value="${i.id}"/>
                    <input type="hidden" name="act" value="Add Car"/>
                    <input class="button" type="submit" name="action" value="<fmt:message key="label.AddCar" />"/>
                </form>
            </td>
            <td align="center">
                <form action="${pageContext.request.contextPath}/customerbook" method="post">
                    <input type="hidden" name="id" value="${i.id}"/>
                    <input type="hidden" name="act" value="Delete Car"/>
                    <input class="button" type="submit" name="action" value="<fmt:message key="label.DeleteCar" />"/>
                </form>
            </td>
            </c:forEach>
    </table>
    <br>
    <nav aria-label="Navigation for cars">
        <ul class="pagination justify-content-center">
            <c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/customerbasis?currentPage=${currentPage-1}"><fmt:message key="label.Previous" /></a>
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
                                                 href="${pageContext.request.contextPath}/customerbasis?currentPage=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/customerbasis?currentPage=${currentPage+1}"><fmt:message key="label.Next" /></a>
                </li>
            </c:if>
        </ul>
    </nav>
    <br>
    <form class="menu-1" action="${pageContext.request.contextPath}/customerbook" method="post">
        <input type="hidden" name="act" value="Book"/>
        <input class="button" type="submit" name="action" value="<fmt:message key="label.Book" />"/>
    </form>
</main>

</body>
</html>








