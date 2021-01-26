<%@include file="../general/navbar.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
</head>
<body>
<div class="container">

    <img class = "permission-denied" src="<c:url value = "/resources/img/pngegg.png"/>"/>
    <h3 align="center" class="text-info"><c:out value="${message}"/>:(</h3>
    <br>
    <h4 align="center"  class="text-info"> Please, go back.</h4>
</div>
</body>
</html>
