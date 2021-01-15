<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h2>Events list</h2>
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
        <th>Submit</th>
    </tr>
    <c:forEach var="event" items="${allEvents}">
        <tr>
     <%-- <form:form action="updateEvent" method="post" modelAttribute="event">
          <form:hidden path="id"></form:hidden>
          <td><form:input path="patient.name"/> <td>
          <td><form:input path="patient.surname"/></td>
          <td><form:input path="patient.disease"/></td>
          <td><form:input path="type"/></td>
          <td><form:input path="treatmentTime"/></td>
          <td><form:input path="dose"/></td>
          <c:url var="updateButton" value="/nurse/updateEvent">
              <c:param name="eventID" value="${event.id}"></c:param>
          </c:url>
          <td><select name="status">
              <option>${"in plan"}</option>
              <option>${"completed"}</option>
              <option>${"canceled"}</option>
          </select></td>
          <td><input type="submit" value="OK"/></td>
      </form:form>--%>
      <tr>
            <td>${event.patient.name}</td>
            <td>${event.patient.surname}</td>
            <td>${event.patient.disease}</td>
            <td>${event.type}</td>
            <td>${event.treatmentTime}</td>
            <td>${event.dose}</td>
            <td>
        <form:form method="post" action="updateStatus">
                  <%--  <select name="status" onchange="this.form.submit()">--%>
            <select name="status${event.id}">
                        <option value="in plan">${"in plan"}</option>
                        <option value="completed">${"completed"}</option>
                        <option value="canceled">${"canceled"}</option>
                    </select>
            </td>
            <td><c:url var="updateButton" value="/nurse/updateStatus">
                <c:param name="eventId" value="${event.id}"></c:param>
            </c:url>
                <input type="button" value="Submit"
                       onclick="window.location.href ='${updateButton}'" /></td>
        </tr>
        </form:form>
        </tr>
    </c:forEach>
    <br>
</table>
</body>
</html>