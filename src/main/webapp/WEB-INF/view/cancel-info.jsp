<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="navbar.jsp" %>
<!DOCTYPE html>
<html>
<body>
<div class="container">
<h2 class ="text-info">Cancel reason</h2>
<br>
<form:form action="/nurse/setCancelInfo" modelAttribute="cancelEvent">
    <form:hidden path="id"></form:hidden>
    <div class="row mb-4">
        <div class="col">
            <div class="form-outline">
                <h3 class="form-label text-info" for="form3Example1">Reason</h3>
                <form:textarea rows="5" cols="10" id="form3Example1" class="form-control" path="cancelReason"/>
            </div>
        </div>
    </div>
  <%--  <p>Reason:</p>
    <br><form:textarea rows="10" cols="20" path="cancelReason"/>--%>
    <input type="submit" class="btn btn-xs btn-outline-info " value="OK">
</form:form>
</div>
</body>
</html>