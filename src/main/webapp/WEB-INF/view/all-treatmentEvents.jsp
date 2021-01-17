<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<br>
<h2 class="text-info"> Events list</h2>
<br>
<div class="container">
    <input type="button" class="btn btn-outline-secondary btn-sm" id="all" value="Show all treatments"
           onclick="window.location.href = '/nurse/showAllTreatments'"/>
    <input type="button" class="btn btn-outline-secondary btn-sm" id="hour" value="Show nearest hour treatments"
           onclick="window.location.href = '/nurse/showNearestHourTreatments'"/>
    <input type="button" class="btn btn-outline-secondary btn-sm" id="today" value="Show today's treatments"
           onclick="window.location.href = '/nurse/'"/>
    <br><br>
    <%--Find patient's treatment <input type="text" name="patientSurname" size="30"
                        value="" onsubmit="window.location.href = '/nurse/findBySurname'">--%>
    <form class="search-form" action="/nurse/findBySurname">
        <div class="row mb-4">
            <div class="col">
                <div class="form-outline">
                    <label class="form-label text-info" for="form3Example1">Find patient's treatment</label>
                    <input id="form3Example1" class="form-control" name="patientSurname"/>
                </div>
            </div>
            <div class="col">
                <div class="form-outline">
                    <label class="form-label" for="form3Example2"></label>
                    <input type="submit" id="form3Example2" value="Find" style="float: left; margin-top:30px;"
                           class="btn btn-xs btn-outline-info "/>
                </div>
            </div>
        </div>
        <%--  Find patient's treatment <input name="patientSurname"/>
          <input type="submit" value="Find" class="btn  btn-outline-info"/>--%>
    </form>
    <table class="table table-light table-hover">
        <thead style="background-color:skyblue">
        <tr class="text-white">
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
        </thead>
        <c:forEach var="event" items="${allEvents}">
            <tr class="text-info">
                <td>${event.patient.name}</td>
                <td>${event.patient.surname}</td>
                <td>${event.patient.disease}</td>
                <td>${event.type}</td>
                <td>${event.procedureMedicine.name}</td>
                <td>${event.treatmentTime}</td>
                <td>${event.dose}</td>
                <td>${event.status}</td>

                <td>
                    <c:url var="showReasonButton" value="/nurse/cancelStatus">
                        <c:param name="eventId" value="${event.id}"></c:param>
                        <c:param name="eventStatus" value="canceled"></c:param>
                    </c:url>
                    <c:url var="updateCompletedButton" value="/nurse/updateStatus">
                        <c:param name="eventId" value="${event.id}"></c:param>
                        <c:param name="eventStatus" value="completed"></c:param>
                    </c:url>
                    <c:url var="updateCanceledButton" value="/nurse/cancelStatus">
                        <c:param name="eventId" value="${event.id}"></c:param>
                        <c:param name="eventStatus" value="canceled"></c:param>
                    </c:url>
                    <c:if test="${event.status.equals('in plan')}">
                        <input type="button" value="completed"
                               class="btn btn-outline-success"
                               onclick="window.location.href ='${updateCompletedButton}'"/>
                        <input type="button" value="canceled" class="btn btn-outline-danger"
                               onclick="window.location.href ='${updateCanceledButton}'"/>
                    </c:if>
                    <c:if test="${event.status.equals('canceled')}">
                        <input type="button" value="Show reasons" class="btn btn-outline-warning"
                               onclick="window.location.href ='${showReasonButton}'"/>
                    </c:if>
                    <c:if test="${event.status.equals('completed')}">
                        <input type="button" value="completed"
                               class="btn btn-outline-success"
                               onclick="window.location.href ='${updateCompletedButton}'" disabled/>
                        <input type="button" value="canceled" class="btn btn-outline-danger"
                               onclick="window.location.href ='${updateCanceledButton}'"disabled/>
                    </c:if>

                </td>
            </tr>
        </c:forEach>
        <br>
    </table>
</div>
</body>
</html>