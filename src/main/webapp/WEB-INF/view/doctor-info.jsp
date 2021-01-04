<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h2>Информация о враче</h2>
<br>

<form:form action="saveDoctor" modelAttribute="doctor">
    <form:hidden path="id" ></form:hidden>
    Имя <form:input path="name"/>
    <br><br>
    Фамилия <form:input path="surname"/>
    <br><br>
    Позиция <form:input path="position"/>
    <br><br>
    <input type="submit" value="OK">
</form:form>
</body>
</html>