<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h2>Treatment info</h2>
<br>


<form:form modelAttribute="patient">
    <form:hidden path="id" ></form:hidden>
    Name <form:input path="name"/>
    <br><br>
    Surname <form:input path="surname"/>
    <br><br>
    Birthdate <form:input path="birthDate"/>
    <br><br>
    Disease <form:input path="disease"/>
    <br><br>
</form:form>

<c:forEach items="${treatments}" var="treatment">
<form:form action="saveTreatment" modelAttribute="treatment">
    <form:hidden path= "" ></form:hidden>
    Type <form:input path="type"/>
    <br><br>
    Time Pattern <form:input path="timePattern"/>
    <br><br>
    Dose <form:input path="dose"/>
    <br><br>
    Period <form:input path="period"/>
    <br><br>
    <input type="submit" value="OK">
</form:form>
</c:forEach>
</body>
</html>