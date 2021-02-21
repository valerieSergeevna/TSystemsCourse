<%@ page import="com.spring.webapp.dto.PatientDTOImpl" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@include file="../general/navbar.jsp" %>

<html>
<body>
<div class="container">
    <form:form action="/saveUpdatedUser" class="main-form text-info" modelAttribute="userInfo">

        <h2 class="text-info">User info</h2>
        <button type="submit" class="btn btn-outline-success btn-circle btn-xl" data-toggle="tooltip"
                data-placement="right"
                title="save all information" value="Save"><i class="fa fa-check"></i>
        </button>
        <br>
        <c:if test="${userInfo.id != null}">
            <form:hidden path="id"></form:hidden>
        </c:if>
        <div class="row mb-4">
            <div class="col">
                <div class="form-outline">
                    <label class="form-label" for="form3Example1">Name</label>
                    <form:input id="form3Example1" class="form-control" path="name"/>
                    <form:errors path="name"></form:errors>
                </div>
            </div>
            <div class="col">
                <div class="form-outline">
                    <label class="form-label" for="form3Example2">Last name</label>
                    <form:input class="form-control" id="form3Example2" path="surname"/>
                    <form:errors path="surname"></form:errors>
                </div>
            </div>
        </div>
        <div class="form-outline mb-4">
            <label class="form-label" for="form3Example3">Position</label>
            <form:input class="form-control" id="form3Example3" path="position"/>
            <form:errors path="position"></form:errors>
        </div>
        <div class="form-outline mb-4">
            <label class="form-label" for="form3Example4">Username</label>
            <form:input class="form-control" id="form3Example4" path="username"/>
            <form:errors path="username"></form:errors>
        </div>
        <div class="form-outline mb-4">
            <label class="form-label" for="form3Example5">Email </label>
            <input type="email" class="form-control"
                   id="form3Example5" name="email"
                   value="${user.googleUsername}" placeholder="some@some.com">
        </div>
    </form:form>
</div>
</body>
</html>