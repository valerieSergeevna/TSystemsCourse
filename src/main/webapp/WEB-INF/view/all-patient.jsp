<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="navbar.jsp" %>
<!DOCTYPE html>
<html>
<body>
<div class="container">
    <h2 class = "text-info">Patients list</h2>
    <table class="table table-light table-hover">
        <thead style="background-color:skyblue">
        <tr class = "text-white">
            <th style="width: 10%">Name</th>
            <th style="width: 20%">Surname</th>
            <th style="width: 30%">Disease</th>
            <th style="width: 40%">Operations</th>
        </tr>
        </thead>
        <c:forEach var="patient" items="${allPatient}">
            <tbody>
            <tr class = "text-info">
                <c:url var="updateButton" value="/updateTreatmentInfo">
                    <c:param name="patientId" value="${patient.id}"></c:param>
                </c:url>
                <c:url var="deleteButton" value="/deletePatient">
                    <c:param name="patientId" value="${patient.id}"></c:param>
                </c:url>
                <c:url var="viewButton" value="/viewPatient">
                    <c:param name="patientId" value="${patient.id}"></c:param>
                </c:url>
                <td>${patient.name}</td>
                <td>${patient.surname}</td>
                <td>${patient.disease}</td>
                <security:authorize access="hasRole('DOCTOR')">
                <td><input type="button" value="Update" class="btn  btn-outline-info"
                                                        onclick="window.location.href ='${updateButton}'"/>
                 <input type="button" value="Delete" class="btn btn-outline-danger"
                                                        onclick="window.location.href ='${deleteButton}'"/></td>
                </security:authorize>
                <input type="button" value="View" class="btn btn-outline-dark"
                       onclick="window.location.href ='${viewButton}'"/></td>
            </tr>
            </tbody>
        </c:forEach>
        <br>
    </table>

    <security:authorize access="hasRole('DOCTOR')">
    <input type="button" value="Add" class="btn btn-outline-success" onclick="window.location.href = '/addNewPatient'"/>
    </security:authorize>>

</div>
<a href="logout">logout</a>
</body>
</html>