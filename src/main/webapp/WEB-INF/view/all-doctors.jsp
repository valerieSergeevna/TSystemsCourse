<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h2>Doctors list</h2>
<br>
<table>
    <tr>
        <th>Name</th>
        <th>Surname</th>
        <th>Position</th>
        <th>Operations</th>
    </tr>
    <c:forEach var="doc" items="${allDocs}">
        <tr>
            <c:url var="updateButton" value="/updateDoctorInfo">
                <c:param name="docId" value="${doc.id}"></c:param>
            </c:url>
            <c:url var="deleteButton" value="/deleteDoctor">
                <c:param name="docId" value="${doc.id}"></c:param>
            </c:url>
            <td>${doc.name}</td>
            <td>${doc.surname}</td>
            <td>${doc.position}</td>
            <td><input type="button" value="Update"
                       onclick="window.location.href ='${updateButton}'"/>
                <input type="button" value="Delete"
                       onclick="window.location.href ='${deleteButton}'"/></td>
        </tr>
    </c:forEach>
    <br>
</table>
<input type="button" value="Добавить" onclick="window.location.href = '/addNewDoctor'"/>
</body>
</html>