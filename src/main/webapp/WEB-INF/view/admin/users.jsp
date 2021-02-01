<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html;charset=UTF-8"  pageEncoding="utf-8"%>
<%@include file="../general/navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Log in with your account</title>
</head>

<body>
<div class="container">
    <h2 class = "text-info">Users list</h2>
    <table class="table table-light table-hover">
        <thead style="background-color:skyblue">
        <tr class = "text-white">
        <th>ID</th>
        <th>UserName</th>
        <th>Roles</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Position</th>
            <th>Action</th>
        </tr>
        </thead>
        <c:forEach items="${allUsers}" var="user">
        <tbody>
            <tr class = "text-info">
                <td>${user.key.id}</td>
                <td>${user.key.username}</td>
                <td>
                    <c:forEach items="${user.key.roles}" var="role">${role.name}; </c:forEach>
                </td>
                <td>${user.value.name}</td>
                <td>${user.value.surname}</td>
                <td>${user.value.position}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin" method="post">
                        <input type="hidden" name="userId" value="${user.key.id}"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </tbody>
        </c:forEach>
    </table>
</div>
</body>
</html>

