<%--
  Created by IntelliJ IDEA.
  User: Lesia Yurchenko
  Date: 5/24/2021
  Time: 5:37 PM
  To change this template use File | Settings | File Templates.
--%>

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
  <title>Error Page</title>
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

    .menu-1 input {
        margin: 8px 10px 8px 0;
        padding: 2px 14px 4px;
        background-color: #4960fb;
        color: #f2f2f2;
        border: 2px solid #071eb9;
        border-radius: 1px 10px;
        transition: all 0.7s ease 0s;
    }

    .menu-1 input:hover {
        background-color: #ccc;
        border-radius: 10px 1px;
    }
</style>
<br>
<br>
<br>
<br>
<br>
<br>
<h2 align="center">
  <fmt:message key="label.Error" />
</h2>
<br>
<br>
<form class="menu-1">
    <input type="button" value="<fmt:message key="label.Back"/>" onClick='location.href="${pageContext.request.contextPath}/exception"'>
  </form>

</body>
</html>




