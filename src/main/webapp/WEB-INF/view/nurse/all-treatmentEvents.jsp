<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../general/navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<br>
<h2 class="text-info event">Events list</h2>
<br>
<div class="container">
    <input type="button" class="btn btn-outline-secondary btn-sm" id="all" value="Show all treatments"
           onclick="window.location.href = '/nurse/showAllTreatments'"/>
    <input type="button" class="btn btn-outline-secondary btn-sm" id="hour" value="Show nearest hour treatments"
           onclick="window.location.href = '/nurse/showNearestHourTreatments'"/>
    <input type="button" class="btn btn-outline-secondary btn-sm" id="today" value="Show today's treatments"
           onclick="window.location.href = '/nurse/'"/>
    <br><br>
    <form class="search-form search-surname" action="/nurse/findBySurname">
        <div class="row mb-4">
            <div class="col">
                <div class="form-outline">
                    <label class="form-label text-info" for="form3Example1">Find patient's treatment</label>
                    <input onkeyup="filter()" id="form3Example1" class="form-control" name="patientSurname"/>
                </div>
            </div>
            <div class="col">
                <%--                <div class="form-outline">--%>
                <%--                    <label class="form-label"></label>--%>
                <%--                    <input type="submit" value="Find" style="float: left; margin-top:55px;"--%>
                <%--                           class="btn btn-xs btn-outline-info "/>--%>
                <%--                </div>--%>
                <form class="search-form search-date" action="/nurse/findByDate">
                    <div class="row mb-4">
                        <div class="col">
                            <div class="form-outline">
                                <label class="form-label text-info" for="datepicker">Find by date</label>
                                <input type="date" class="form-control datepicker" name="date"
                                       id="datepicker" required>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-outline">
                                <label class="form-label"></label>
                                <input type="submit" value="Find" style="float: left; margin-top:30px;"
                                       class="btn btn-xs btn-outline-info "/>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
    </form>


    </form>

    <form class="search-form" action="/nurse/getByType">
        <div class="row mb-4">
            <div class="col">
                <div class="form-outline">
                    <label class="form-label text-info" for="typeID">Find by treatment type</label>
                    <select onchange="typeFilter()" class="form-control" id="typeID"
                            name="treatmentType">
                        <option value="medicine">medicine</option>
                        <option value="procedure">procedure</option>
                    </select>
                </div>
            </div>
            <div class="col">
                <div class="form-outline">
<%--                    <label class="form-label"></label>--%>
<%--                    <input type="submit" value="Find" style="float: left; margin-top:30px;"--%>
<%--                           class="btn btn-xs btn-outline-info "/>--%>
                </div>
            </div>
        </div>
    </form>

    <table id="myTable" class="table table-light table-hover">
        <thead style="background-color:skyblue">
        <tr class="text-white">
            <th onclick="sortTable(0)">Name</th>
            <th onclick="sortTable(1)">Surname</th>
            <th onclick="sortTable(2)">Disease</th>
            <th onclick="sortTable(3)">Type</th>
            <th onclick="sortTable(4)">Treatment name</th>
            <th onclick="sortTable(5)">Time</th>
            <th onclick="sortTable(6)">Dose</th>
            <th onclick="sortTable(7)">Status</th>
            <th>Change status</th>
        </tr>
        </thead>
        <c:forEach var="event" items="${allEvents}">
            <c:if test="${event.treatmentTime.isBefore(currentTime)}"><tr class="text-info" bgcolor="#d3d3d3"></c:if>
            <c:if test="${event.treatmentTime.isAfter(currentTime) || event.treatmentTime.isEqual(currentTime)}">
                <tr class="text-info"></c:if>
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
                           onclick="window.location.href ='${updateCanceledButton}'" disabled/>
                </c:if>

            </td>
            </tr>
        </c:forEach>
        <br>
    </table>
</div>
<script type="text/javascript">
    <%@include file="/resources/scripts/appScript.js"%>
</script>
</body>
</html>