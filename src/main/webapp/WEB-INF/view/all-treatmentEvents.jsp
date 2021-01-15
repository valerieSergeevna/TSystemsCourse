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
    <%--<form:form action="nurse/updateStatus" method="post" modelAttribute="allEvents">--%>
    <c:forEach var="event" items="${allEvents}">
        <tr>

                <%--  <c:if test="${event.id != null}" >
                  <form:hidden path="id"></form:hidden> </c:if>
                  <td><form:input path="patient.name"/><td>
                  <td><form:input path="patient.surname"/></td>
                  <td><form:input path="patient.disease"/></td>
                  <td><form:input path="type"/></td>
                  <td><form:input path="treatmentTime"/></td>
                  <td><form:input path="dose"/></td>
                  <td><form:select path="status">
                      <form:option value="in plan" label="in plan"/>
                      <form:option value="completed" label="completed"/>
                      <form:option value="canceled" label="canceled"/>
                  </form:select></td>
                  <td><input type="submit" value="submit"/></td>
           --%>
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
                    <%--  <select name="status" onchange="this.form.submit()">--%>
                    <%--  <select  name="status${event.id}">
                                    <option value="in plan">${"in plan"}</option>
                                    <option value="completed">${"completed"}</option>
                                    <option value="canceled">${"canceled"}</option>
                                </select>
                        </td>
                        <td>--%>
                <c:url var="updatePlanButton" value="/nurse/updateStatus">
                    <c:param name="eventId" value="${event.id}"></c:param>
                    <c:param name="eventStatus" value="in plan"></c:param>
                </c:url>
                <c:url var="updateCompletedButton" value="/nurse/updateStatus">
                    <c:param name="eventId" value="${event.id}"></c:param>
                    <c:param name="eventStatus" value="completed"></c:param>
                </c:url>
                <c:url var="updateCanceledButton" value="/nurse/updateStatus">
                    <c:param name="eventId" value="${event.id}"></c:param>
                    <c:param name="eventStatus" value="canceled"></c:param>
                </c:url>
            <input type="button" value="completed"
                   onclick="window.location.href ='${updateCompletedButton}'"/>
            <input type="button" value="canceled"
                   onclick="window.location.href ='${updateCompletedButton}'"/>
            </td>
        </tr>
        </tr>
    </c:forEach>
    <%--  </form:form>--%>
    <br>
</table>
</body>
</html>