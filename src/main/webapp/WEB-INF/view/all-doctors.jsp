<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>
<h2>Список врачей</h2>
<br>
<table>
    <tr>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Позиция</th>
    </tr>
    <c:forEach var="doc" items="${allDocs}">
        <tr>
            <td>${doc.name}</td>
            <td>${doc.surname}</td>
            <td>${doc.position}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>