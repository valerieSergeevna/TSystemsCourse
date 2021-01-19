<%@ page import="com.spring.webapp.dto.PatientDTOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@include file="general/navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
<div class="container">
    <form:form modelAttribute="patient">
        <h2 class="text-info">Patient info</h2>
        <br>
        <c:if test="${patient.treatments != null}">
            <form:hidden path="id"></form:hidden>
        </c:if>
        <div class="card border-info">
            <div class="card-header bg-info text-white">
                    ${patient.name} ${patient.surname}
                <br>
                Disease: ${patient.disease}
                <br>
                Status: ${patient.status}
            </div>
            <c:if test="${patient.treatments.size() != 0}">
                <div class="card-body">
                    <h3 class="card-title">Treatments:</h3>
                    <c:forEach items="${patient.treatments}" var="treatment" varStatus="count">
                        <input type="hidden" name="treatment" value="${treatment.treatmentId}">
                        <h4 class="card-title">${treatment.typeName}</h4>
                        <p class="card-text">Type: ${treatment.type}</p>
                        <p class="card-text">Dose: ${treatment.dose}</p>
                        <p class="card-text">Period: ${treatment.period}</p>
                        <p class="card-text">Time pattern: ${treatment.timePattern}</p>
                        <br>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </form:form>
</div>
</body>
</html>