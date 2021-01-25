<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="general/navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<div class="container">
    <h2 class="text-info"> Procedures/Medicines list</h2>
    <table class="table table-light table-hover">
        <thead style="background-color:skyblue">
        <tr class="text-white">
            <th>Name</th>
            <th>Type</th>
        </tr>
        </thead>
        <c:forEach var="procedureMedicine" items="${allMedicineProcedure}">
            <tr class="text-info">
                <td>${procedureMedicine.name}</td>
                <td>${procedureMedicine.type}</td>
            </tr>
        </c:forEach>
        <br>
    </table>
</div>
</body>
</html>
