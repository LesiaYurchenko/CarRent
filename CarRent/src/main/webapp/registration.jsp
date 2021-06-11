<%--
  Created by IntelliJ IDEA.
  User: Lesia Yurchenko
  Date: 6/5/2021
  Time: 3:46 PM
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
    .menu {
        margin: 0;
        padding: 0;
        height: auto;
        list-style-type: none;
        text-align: center;
    }

    .menu form {
        margin: 0 auto;
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

<ul class="menu-2">
    <li><a href="${pageContext.request.contextPath}/main"><fmt:message key="label.Main"/></a></li>
    <li><a href="${pageContext.request.contextPath}/login.jsp"><fmt:message key="label.Login"/></a></li>
    <fmt:message key="label.Language"/>
    <li><a href="?lang=en"><fmt:message key="label.en"/></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.ua"/></a></li>
</ul>
<br>
<br>
<h1 align="center"><fmt:message key="label.RegisterPlease"/></h1><br/>
<br>
<form class="menu" method="post" action="${pageContext.request.contextPath}/registration">

    <label for="1"><fmt:message key="label.login"/></label>
    <input type="text" id="1" name="login" required pattern="[a-zA-Zа-яА-ЯёЁїЇіІєЄ0-9]{3,10}"
           title="<fmt:message key="label.WrongLogin"/>"
           oninvalid="this.setCustomValidity('<fmt:message key="label.WrongLogin"/>')" oninput="setCustomValidity('')"
           placeholder="<fmt:message key="label.login" />"><br/>
    <label for="2"><fmt:message key="label.password"/></label>
    <input type="password" id="2" name="password" required
           title="<fmt:message key="label.WrongPassword"/>"
           oninvalid="this.setCustomValidity('<fmt:message key="label.WrongPassword"/>')" oninput="setCustomValidity('')"
           pattern="[a-zA-Zа-яА-ЯёЁїЇіІєЄ0-9]{5,10}"
           placeholder="<fmt:message key="label.password" />"><br/>
    <label for="3"><fmt:message key="label.email"/></label>
    <input type="email" id="3" name="email" required
                   title="<fmt:message key="label.WrongEmail"/>"
                   oninvalid="this.setCustomValidity('<fmt:message key="label.WrongEmail"/>')"
                   oninput="setCustomValidity('<fmt:message key="label.WrongEmail"/>')"
                   placeholder="<fmt:message key="label.email" />"><br/>
    <br>
    <input class="button" type="submit" name="register" value="<fmt:message key="label.Registration" />"><br/>
</form>

</body>
</html>