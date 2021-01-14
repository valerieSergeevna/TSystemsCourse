<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h2>Patients list</h2>
<br>
<table>
    <tr>
        <th>Name</th>
        <th>Surname</th>
        <th>Disease</th>
        <th>Type</th>
        <th>Time</th>
        <th>Dose</th>
        <th>Status</th>
    </tr>
    <c:forEach var="events" items="${allTreatmentEvents}">
        <tr>
            <c:url var="updateButton" value="/updateTreatmentInfo">
                <c:param name="patientId" value="${patient.id}"></c:param>
            </c:url>
            <c:url var="deleteButton" value="/deletePatient">
                <c:param name="patientId" value="${patient.id}"></c:param>
            </c:url>
            <td>${patient.name}</td>
            <td>${patient.surname}</td>
            <td>${patient.disease}</td>
            <td><input type="button" value="Update"
                       onclick="window.location.href ='${updateButton}'"/>
                <input type="button" value="Delete"
                       onclick="window.location.href ='${deleteButton}'"/></td>
        </tr>
    </c:forEach>
    <br>
</table>
<input type="button" value="Add" onclick="window.location.href = '/addNewPatient'"/>
</body>
</html>