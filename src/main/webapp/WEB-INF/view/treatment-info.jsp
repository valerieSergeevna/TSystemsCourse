<%@ page import="com.spring.webapp.dto.PatientDTOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>


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
<h2>Treatment info</h2>
<br>


<form:form action="saveTreatment" method="post" modelAttribute="patient">
    <c:if test="${patient.treatments != null}" >
<form:hidden path="id"></form:hidden>
    </c:if>
Name <form:input path="name"/>
    <form:errors path="name"></form:errors>
<br><br>
Surname <form:input path="surname"/>
    <form:errors path="surname"></form:errors>
<br><br>
Birthdate <form:input path="birthDate"/>
    <form:errors path="birthDate"></form:errors>
<br><br>
Disease <form:input path="disease"/>
    <form:errors path="disease"></form:errors>
<br><br>

<c:if test="${patient.treatments.size() != 0}" >


<c:forEach items="${patient.treatments}" var="treatment" varStatus="count">
<input type="hidden" name="treatment" value="${treatment.treatmentId}">
    <br><br>
    Medicine/Procedure name <input type="text" name="treatmentName" size="30"
                        value="${treatment.typeName}">

Type
<%--<select name="treatmentType" >
    <option>${"treatment"}</option>
    <option>${"procedure"}</option>
</select>--%>
<input type="text" name="treatmentType" size="30" value="${treatment.type}">
    <br><br>
    Time Pattern <input type="text" name="treatmentPattern" size="30"
                        value="${treatment.timePattern}">
    <br><br>
    Dose <input type="text" name="treatmentDose" size="30" value="${treatment.dose}">
    <br><br>
    Period <input type="text" name="treatmentPeriod" size="30" value="${treatment.period}">
    <br>
        <%--</form:form>--%>
    <c:url var="deleteButton" value="/deleteTreatment">
        <c:param name="treatmentId" value="${treatment.treatmentId}"></c:param>
    </c:url>
    <input type="button" value="Delete treatment"
           onclick="window.location.href ='${deleteButton}'" />
    </c:forEach>
</c:if>
    <br>

    <div id="container"></div>
    <input type="button" value="Add treatment"
           onclick="addForm()"/>
    <%----%>
    <input type="submit" value="OK"/>
    </form:form>


</body>
</html>