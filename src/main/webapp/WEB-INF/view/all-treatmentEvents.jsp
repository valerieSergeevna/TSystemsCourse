<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<h2>Events list</h2>
<br>

<input type="button" id = "all" value="Show all treatments"
              onclick="window.location.href = '/nurse/showAllTreatments'" />
<input type="button" id = "hour" value="Show nearest hour treatments"
       onclick="window.location.href = '/nurse/showNearestHourTreatments'"/>
<input type="button" id = "today" value="Show today's treatments"
       onclick="window.location.href = '/nurse/'"/>
<br><br>
<%--Find patient's treatment <input type="text" name="patientSurname" size="30"
                    value="" onsubmit="window.location.href = '/nurse/findBySurname'">--%>
<form action="/nurse/findBySurname">
    Find patient's treatment <input name="patientSurname"/>
    <input type="submit" value="Find"/>
</form>
<br><br>

<table>
    <tr>
        <th>Name</th>
        <th>Surname</th>
        <th>Disease</th>
        <th>Type</th>
        <th>Treatment name</th>
        <th>Time</th>
        <th>Dose</th>
        <th>Status</th>
        <th>Change status</th>
    </tr>
    <c:forEach var="event" items="${allEvents}">
        <tr>
        <tr>
            <td>${event.patient.name}</td>
            <td>${event.patient.surname}</td>
            <td>${event.patient.disease}</td>
            <td>${event.type}</td>
            <td>${event.procedureMedicine.name}</td>
            <td>${event.treatmentTime}</td>
            <td>${event.dose}</td>
            <td>${event.status}</td>

            <td>
                <c:url var="updatePlanButton" value="/nurse/updateStatus">
                    <c:param name="eventId" value="${event.id}"></c:param>
                    <c:param name="eventStatus" value="in plan"></c:param>
                </c:url>
                <c:url var="updateCompletedButton" value="/nurse/updateStatus">
                    <c:param name="eventId" value="${event.id}"></c:param>
                    <c:param name="eventStatus" value="completed"></c:param>
                </c:url>
                <c:url var="updateCanceledButton" value="/nurse/cancelStatus">
                    <c:param name="eventId" value="${event.id}"></c:param>
                    <c:param name="eventStatus" value="canceled"></c:param>
                </c:url>
            <input type="button" value="completed"
                   onclick="window.location.href ='${updateCompletedButton}'"/>
            <input type="button" value="canceled"
                   onclick="window.location.href ='${updateCanceledButton}'"/>
            </td>
        </tr>
        </tr>
    </c:forEach>
    <br>
</table>
</body>
</html>