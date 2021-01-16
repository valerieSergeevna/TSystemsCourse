<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h2>Cancel reason</h2>
<br>
<form:form action="/nurse/setCancelInfo" modelAttribute="cancelEvent">
    <form:hidden path="id"></form:hidden>
    <p>Reason:</p>
    <br><form:textarea rows="10" cols="20" path="cancelReason"/>
    <input type="submit" value="OK">
</form:form>
</body>
</html>