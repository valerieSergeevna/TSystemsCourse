<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@include file="../general/navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
</head>

<body>
<div class="container">
    <h2 class="text-info">Users list</h2>
    <table class="table table-light table-hover">
        <thead style="background-color:skyblue">
        <tr class="text-white">
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
            <tr class="text-info">
                <td>${user.key.id}</td>
                <td>${user.key.username}</td>
                <td>
                    <c:forEach items="${user.key.roles}" var="role">${role.name}; </c:forEach>
                </td>
                <td>${user.value.name}</td>
                <td>${user.value.surname}</td>
                <td>${user.value.position}</td>
                <td>
<%--                    <form action="" method="post">--%>
<%--                        <input type="hidden" name="userId" value="${user.key.id}"/>--%>
<%--                        <button type="submit"  class="btn btn-outline-danger">Delete</button>--%>
<%--                    </form>--%>
<%--                    <form action="/updateUser" method="get">--%>
<%--                        <input type="hidden" name="userId" value="${user.key.id}"/>--%>
<%--                        <button class="btn  btn-outline-info"  type="submit">Update</button>--%>
<%--                    </form>--%>
    <c:url var="updateButton" value="/updateUser">
        <c:param name="userId" value="${user.key.id}"></c:param>
    </c:url>
    <input type="button" value="Update" class="btn  btn-outline-info"
           onclick="window.location.href ='${updateButton}'"/>
    <c:url var="deleteButton" value="/deleteUser">
        <c:param name="userId" value="${user.key.id}"></c:param>
    </c:url>
    <input type="button" value="Delete" class="btn  btn-outline-danger"
           onclick="window.location.href ='${deleteButton}'"/>
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
    <button class="btn btn-outline-info" id="registrationButton" >New user registration</button>
    <div id = "registration" class="registration container" style="display: none;">
        <div>
            <form:form action = "/registration" method="POST" modelAttribute="userForm">
                <h2 class="text-info">Registration</h2>
                <div class="form-outline mb-4">
                    <label class="form-label" for="nameId">Name</label>
                    <input type="text"
                           name="name" class="form-control" id = "nameId" value="" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="SurnameId">Surname</label>
                    <input type="text"
                           name="surname" class="form-control" id="SurnameId" value="" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="PositionId">Position</label>
                    <input type="text"
                           name="position" class="form-control" id="PositionId" value="" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="RoleId">Role</label>
                    <select type="text" class="form-control"
                           name="role" id="RoleId" value="" required>
                        <option value="doctor">doctor</option>
                        <option value="nurse">nurse</option>
                        <option value="admin">admin</option>
                    </select>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="userNameId">Username</label>
                    <form:input type="text" id="userNameId" class="form-control" path="username" placeholder="Username"
                               ></form:input>
                    <form:errors class="form-control" path="username"></form:errors>
                        ${usernameError}
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="emailId">Email</label>
                    <form:input type="text" id="emailId" class="form-control" path="googleUsername" placeholder="Email"
                                ></form:input>
                    <form:errors class="form-control" path="googleUsername"></form:errors>
                        ${googleUsernameError}
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="passwordId">Password</label>
                    <form:input type="password"  class="form-control" id="passwordId" path="password" placeholder="Password"></form:input>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="passwordConfirmId">Password confirm</label>
                    <form:input class="form-control" type="password" id="passwordConfirmId" path="passwordConfirm"
                                placeholder="Confirm your password"></form:input>
                    <form:errors class="form-control" path="password"></form:errors>
                        ${passwordError}
                </div>
                <button class="btn btn-outline-success" type="submit" >Registration</button>
            </form:form>
        </div>
    </div>
</div>
<script type="text/javascript">
    <%@include file="/resources/scripts/appScript.js"%>
</script>
</body>
</html>

