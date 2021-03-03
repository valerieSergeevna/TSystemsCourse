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
    <div class="row mb-4">
        <div class="col">
            <div class="form-outline">
                <label class="form-label text-info" for="userNameInputId">Find by username</label>
                <input onkeyup="filterUsers('userNameInputId',1)" id="userNameInputId" class="form-control" name="userName"/>
            </div>
        </div>
        <div class="col">
            <div class="form-outline">
                <label class="form-label text-info" for="surnameInputId">Find by surname</label>
                <input onkeyup="filterUsers('surnameInputId',4)" id="surnameInputId" class="form-control" name="Surname"/>
            </div>
        </div>
    </div>
    <div class="row mb-4">
        <div class="col">
            <div class="form-outline">
                <label class="form-label text-info" for="roleInputId">Find by role</label>
                <select onchange="filterUsers('roleInputId',2)" class="form-control" id="roleInputId"
                        name="treatmentType">
                    <option value="ROLE_ADMIN">ROLE_ADMIN</option>
                    <option value="ROLE_NURSE">ROLE_NURSE</option>
                    <option value="ROLE_DOCTOR">ROLE_DOCTOR</option>
                </select>
            </div>
        </div>
        <div class="col">
            <div class="form-outline">
                <%--                    <label class="form-label"></label>--%>
                <%--                    <input type="submit" value="Find" style="float: left; margin-top:30px;"--%>
                <%--                           class="btn btn-xs btn-outline-info "/>--%>
            </div>
        </div>
    </div>
    <table id = "myTable" class="table table-light table-hover">
        <thead style="background-color:skyblue">
        <tr class="text-white">
            <th onclick="sortTable(0)">ID</th>
            <th onclick="sortTable(1)">UserName</th>
            <th onclick="sortTable(2)">Roles</th>
            <th onclick="sortTable(3)">Name</th>
            <th onclick="sortTable(4)">Surname</th>
            <th onclick="sortTable(5)">Position</th>
            <th>Action</th>
        </tr>
        </thead>
        <c:forEach items="${allUsers}" var="user">
            <tbody>
            <tr class="text-info">
                <td><input hidden value="${user.key.id}"> ${user.value.id}</td>
                <td>${user.key.username}</td>
                <td>
                    <c:forEach items="${user.key.roles}" var="role">${role.name}</c:forEach>
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

    <c:if test="${usernameError!=null}">
        <div class="text-danger">
                ${usernameError}
        </div>

    </c:if>
    <c:if test="${passwordError!=null}">
        <div class="text-danger">
                ${passwordError}
        </div>

    </c:if>

    <button class="btn btn-outline-info" id="registrationButton">New user registration</button>
    <div id="registration" class="registration container" style="display: none;">

        <div>
            <form:form action = "/registration" modelAttribute="userForm">
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
                    <div class="text-danger"> ${usernameError}</div>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="emailId">Email</label>
                    <form:input type="text" id="emailId" class="form-control" path="googleUsername" placeholder="Email"
                    ></form:input>
                    <form:errors class="form-control" path="googleUsername"></form:errors>
                    <div class="text-danger"> ${googleUsernameError}</div>
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
                    <div class="text-danger">   ${passwordError}</div>
                </div>
                <button class="btn btn-outline-success" type="submit" >Registration</button>
            </form:form>
        </div>


<%--        <div>--%>
<%--            &lt;%&ndash;       <form:form action = "/registration" method="POST" modelAttribute="userForm">&ndash;%&gt;--%>
<%--            <form action="/registration">--%>
<%--                <h2 class="text-info">Registration</h2>--%>
<%--                <div class="form-outline mb-4">--%>
<%--                    <label class="form-label" for="nameId">Name</label>--%>
<%--                    <input type="text"--%>
<%--                           name="name" class="form-control" id="nameId" value="" required>--%>
<%--                </div>--%>
<%--                <div class="form-outline mb-4">--%>
<%--                    <label class="form-label" for="SurnameId">Surname</label>--%>
<%--                    <input type="text"--%>
<%--                           name="surname" class="form-control" id="SurnameId" value="" required>--%>
<%--                </div>--%>
<%--                <div class="form-outline mb-4">--%>
<%--                    <label class="form-label" for="PositionId">Position</label>--%>
<%--                    <input type="text"--%>
<%--                           name="position" class="form-control" id="PositionId" value="" required>--%>
<%--                </div>--%>
<%--                <div class="form-outline mb-4">--%>
<%--                    <label class="form-label" for="RoleId">Role</label>--%>
<%--                    <select type="text" class="form-control"--%>
<%--                            name="role" id="RoleId" value="" required>--%>
<%--                        <option value="doctor">doctor</option>--%>
<%--                        <option value="nurse">nurse</option>--%>
<%--                        <option value="admin">admin</option>--%>
<%--                    </select>--%>
<%--                </div>--%>
<%--                <div class="form-outline mb-4">--%>
<%--                    <label class="form-label" for="userNameId">Username</label>--%>

<%--                    <c:choose>--%>
<%--                        <c:when test="${usernameError==null}">--%>
<%--                            <input type="text"--%>
<%--                                   name="username" class="form-control" id="userNameId" minlength="2"  placeholder="Username" value=""--%>
<%--                                   required >--%>
<%--                        </c:when>--%>
<%--                        <c:otherwise>--%>
<%--                            <input type="text"--%>
<%--                                   name="username" class="form-control is-invalid" id="userNameId" minlength="2" placeholder="Username" value=""--%>
<%--                                   required >--%>
<%--                        </c:otherwise>--%>
<%--                    </c:choose>--%>
<%--                    <c:if test="${usernameError!=null}">--%>
<%--                        <div class="invalid-feedback">--%>
<%--                                ${usernameError}--%>
<%--                        </div>--%>
<%--                    </c:if>--%>

<%--                    &lt;%&ndash;                    <form:input type="text" id="userNameId" class="form-control" path="username" placeholder="Username"&ndash;%&gt;--%>
<%--                    &lt;%&ndash;                               ></form:input>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;                    <form:errors class="form-control" path="username"></form:errors>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;                        ${usernameError}&ndash;%&gt;--%>
<%--                </div>--%>
<%--                <div class="form-outline mb-4">--%>
<%--                    <label class="form-label" for="emailId">Email</label>--%>
<%--                    <input type="text"--%>
<%--                           name="email" class="form-control" id="emailId" placeholder="some@some.com" value="">--%>
<%--                    &lt;%&ndash;                    <form:input type="text" id="emailId" class="form-control" path="googleUsername" placeholder="Email"&ndash;%&gt;--%>
<%--                    &lt;%&ndash;                                ></form:input>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;                    <form:errors class="form-control" path="googleUsername"></form:errors>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;                        ${googleUsernameError}&ndash;%&gt;--%>
<%--                </div>--%>
<%--                <div class="form-outline mb-4">--%>
<%--                    <label class="form-label" for="passwordId">Password</label>--%>
<%--                    <input type="password"--%>
<%--                           name="password" class="form-control" minlength="2"  id="passwordId" placeholder="Password" value=""--%>
<%--                           required>--%>
<%--                    &lt;%&ndash;                    <form:input type="password"  class="form-control" id="passwordId" path="password" placeholder="Password"></form:input>&ndash;%&gt;--%>
<%--                </div>--%>
<%--                <div class="form-outline mb-4">--%>
<%--                    <label class="form-label" for="passwordConfirmId">Password confirm</label>--%>
<%--                    <input type="password"--%>
<%--                           name="passwordConfirm" class="form-control" minlength="2"  id="passwordConfirmId"--%>
<%--                           placeholder="Password confirm" value="" required>--%>

<%--                    &lt;%&ndash;                    <form:input class="form-control" type="password" id="passwordConfirmId" path="passwordConfirm"&ndash;%&gt;--%>
<%--                    &lt;%&ndash;                                placeholder="Confirm your password"></form:input>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;                    <form:errors class="form-control" path="password"></form:errors>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;                        ${passwordError}&ndash;%&gt;--%>
<%--                </div>--%>
<%--                <button class="btn btn-outline-success" type="submit">Registration</button>--%>
<%--            </form>--%>
<%--        </div>--%>
    </div>
</div>
<script type="text/javascript">
    <%@include file="/resources/scripts/appScript.js"%>
</script>
</body>
</html>

