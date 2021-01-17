<%@ page import="com.spring.webapp.dto.PatientDTOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@include file="navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript">
        function addForm() {
            let container = document.getElementById("container");

            let inputTypeName = document.createElement("input");
            let inputType = document.createElement("input");
            let inputPattern = document.createElement("input");
            let inputDose = document.createElement("input");
            let inputPeriod = document.createElement("input");

            container.append('Medicine/Procedure name ');
            inputTypeName.type = "text";
            inputTypeName.name = "treatmentName";
            container.appendChild(inputTypeName);
            container.appendChild(document.createElement("br"));

            container.append('Type ');
            inputType.type = "text";
            inputType.name = "treatmentType";
            container.appendChild(inputType);
            container.appendChild(document.createElement("br"));
            container.appendChild(document.createElement("br"));

            container.append('Time Pattern ');
            inputPattern.type = "text";
            inputPattern.name = "treatmentPattern";
            container.appendChild(inputPattern);
            container.appendChild(document.createElement("br"));
            container.appendChild(document.createElement("br"));

            container.append('Dose ');
            inputDose.type = "text";
            inputDose.name = "treatmentDose";
            container.appendChild(inputDose);
            container.appendChild(document.createElement("br"));
            container.appendChild(document.createElement("br"));

            container.append('Period ');
            inputPeriod.type = "text";
            inputPeriod.name = "treatmentPeriod";
            container.appendChild(inputPeriod);
            container.appendChild(document.createElement("br"));
            container.appendChild(document.createElement("br"));
        }
    </script>
</head>
<body>
<div class="container">
    <form:form class="main-form text-info" action="saveTreatment" method="post" modelAttribute="patient">
        <h2 class="text-info">Treatment info</h2>
        <br>
        <c:if test="${patient.treatments != null}">
            <form:hidden path="id"></form:hidden>
        </c:if>
        <div class="row mb-4">
            <div class="col">
                <div class="form-outline">
                    <label class="form-label" for="form3Example1">Name</label>
                    <form:input id="form3Example1" class="form-control" path="name"/>
                    <form:errors path="name"></form:errors>
                </div>
            </div>
            <div class="col">
                <div class="form-outline">
                    <label class="form-label" for="form3Example2">Last name</label>
                    <form:input class="form-control" id="form3Example2" path="surname"/>
                    <form:errors path="surname"></form:errors>

                </div>
            </div>
        </div>
        <div class="form-outline mb-4">
            <label class="form-label" for="form3Example3">Birthdate</label>
            <form:input class="form-control" id="form3Example3" path="birthDate"/>
            <form:errors path="birthDate"></form:errors>
        </div>

        <div class="form-outline mb-4">
            <label class="form-label" for="form3Example4">Disease</label>
            <form:input id="form3Example4" class="form-control" path="disease"/>
            <form:errors path="disease"></form:errors>

        </div>

        <div class="form-outline mb-4">
            <label class="form-label" for="form3Example5">Status</label>
            <form:input class="form-control" id="form3Example5" path="status"/>
            <form:errors path="status"></form:errors>
        </div>

        <div class="form-outline mb-4">
            <label class="form-label" for="form3Example6">Insurance number</label>
            <form:input class="form-control" id="form3Example6" path="insuranceNumber"/>
            <form:errors path="status"></form:errors>

        </div>
        <br><br>

        <c:if test="${patient.treatments.size() != 0}">
            <c:forEach items="${patient.treatments}" var="treatment" varStatus="count">
                <input type="hidden" name="treatment" value="${treatment.treatmentId}">
                <br><br>
                <div class="form-outline mb-4">
                    <label class="form-label" for="form3Example7${count.index}">Medicine/Procedure name </label>
                    <input type="text" class="form-label"
                           for="form3Example7${count.index}" name="treatmentName" size="30"
                           value="${treatment.typeName}">
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label" for="form3Example8${count.index}">Type</label>
                        <%--<select name="treatmentType" >
                            <option>${"treatment"}</option>
                            <option>${"procedure"}</option>
                        </select>--%>
                    <input type="text" class="form-label"
                           for="form3Example8${count.index}"
                           name="treatmentType" size="30" value="${treatment.type}">
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="form3Example9${count.index}">Time Pattern </label>
                    <input type="text" class="form-label"
                           for="form3Example9${count.index}" name="treatmentPattern" size="30"
                           value="${treatment.timePattern}">
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="form3Example10${count.index}">Dose</label>
                    <input type="text" class="form-label"
                           for="form3Example10${count.index}" name="treatmentDose" size="30" value="${treatment.dose}">
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="form3Example11${count.index}">Period</label>
                    <input type="text" class="form-label"
                           for="form3Example11${count.index}" name="treatmentPeriod" size="30"
                           value="${treatment.period}">
                </div>
                <%--</form:form>--%>
                <c:url var="deleteButton" value="/deleteTreatment">
                    <c:param name="treatmentId" value="${treatment.treatmentId}"></c:param>
                </c:url>
                <input type="button" class="btn btn-outline-danger btn-sm" value="Delete treatment"
                       onclick="window.location.href ='${deleteButton}'"/>
            </c:forEach>
        </c:if>
        <br>

        <div id="container"></div>
        <input class="btn btn-outline-primary btn-sm" type="button" value="Add treatment"
               onclick="addForm()"/>
        <%----%>
        <input type="submit" class="btn btn-outline-success btn-sm" value="Save"/>

    </form:form>
</div>
</body>
</html>