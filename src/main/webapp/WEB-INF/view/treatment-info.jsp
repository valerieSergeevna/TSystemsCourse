<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h2>Treatment info</h2>
<br>
<%--
<input type="hidden" name="patient" value="${patient.name}">
<br><br>
<input type="hidden" name="patient" value="${patient.surname}">
<br><br>
<input type="hidden" name="patient" value="${patient.disease}">
<br><br>
<input type="hidden" name="patient" value="${patient.status}">
<br><br>
<form:form action="saveTreatment" modelAttribute="patient">
    <form:hidden path="id" ></form:hidden>
    Name <form:input path="name"/>
    <br><br>
    Surname <form:input path="surname"/>
    <br><br>
    Birthdate <form:input path="birthDate"/>
    <br><br>
    Disease <form:input path="disease"/>
    <br><br>
</form:form>--%>

<form:form action="saveTreatment" method="post" modelAttribute="patient">
<form:hidden path="id"></form:hidden>
Name <form:input path="name"/>
<br><br>
Surname <form:input path="surname"/>
<br><br>
Birthdate <form:input path="birthDate"/>
<br><br>
Disease <form:input path="disease"/>
<br><br>

<c:forEach items="${patient.treatments}" var="treatment" varStatus="count">


    <%--Type <form:input path= "treatment" value = "${item.type}"/>
        <br><br>
          <%--    Time Pattern <form:input path= "treatments[${count.index}].timePattern" value = "${treatment.timePattern}"/>
        <br><br>
        Dose <form:input path= "treatments[${count.index}].dose" value = "${treatment.dose}"/>
        <br><br>
        Period <form:input path= "treatments[${count.index}].period" value = "${treatment.period}"/>
        <br><br>--%>
<input type="hidden" name="treatment" value="${treatment.treatmentId}">
    Time Pattern <input type="text" name="treatmentPattern" size="30"
                        value="${treatment.treatmentId}">
    <br><br>
    Treatment name <input type="text" name="treatmentName" size="30"
                        value="${treatment.typeName}">
Type
<input type="text" name="treatmentType" size="30" value="${treatment.type}">
    <br><br>
    Time Pattern <input type="text" name="treatmentPattern" size="30"
                        value="${treatment.timePattern}">
    <br><br>
    Dose <input type="text" name="treatmentDose" size="30" value="${treatment.dose}">
    <br><br>
    Period <input type="text" name="treatmentPeriod" size="30" value="${treatment.period}">
    <br><br>
        <%--</form:form>--%>
    </c:forEach>
    <input type="submit" value="OK"/>
    </form:form>




</body>
</html>