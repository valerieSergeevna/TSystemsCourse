<%@ page import="com.spring.webapp.dto.PatientDTOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@include file="index.jsp" %>
<style>
    <%@include file="/WEB-INF/css/style.css" %>
</style>

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
    <form:form class="main-form" action="saveTreatment" method="post" modelAttribute="patient">
        <h2 class="text-info">Treatment info</h2>
        <br>
        <c:if test="${patient.treatments != null}">
            <form:hidden path="id"></form:hidden>
        </c:if>
        <div class="row">
            <div class="col">
                <div class="form-group row">
                    <label class="text-info col-sm-2 col-form-label">Name</label>
                    <div class="col-xs-2">
                        <div class="col-sm-10">
                            <form:input class="form-control" path="name"/>
                            <form:errors path="name"></form:errors>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="form-group row">
                    <label class="text-info col-sm-2 col-form-label">Surname</label>
                    <div class="col-xs-2">
                        <div class="col-sm-10">
                            <form:input class="form-control" path="surname"/>
                            <form:errors path="surname"></form:errors>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br><br>
        <div class="row">
            <div class="col">
                <div class="form-group row">
                    <label class="text-info col-sm-2 col-form-label">
                        Birthdate </label>
                    <div class="col-xs-2">
                        <div class="col-sm-10">
                        <form:input class="form-control" path="birthDate"/>
                        <form:errors path="birthDate"></form:errors>
                    </div>
                </div>
            </div>
        </div>
        <br><br>
        <div class="row">
            <div class="col">
                <div class="form-group row">

                    <label class="text-info col-sm-2 col-form-label">
                        Disease</label>
                    <div class="col-xs-4">
                        <div class="col-sm-10">
                            <form:input class="form-control" path="disease"/>
                            <form:errors path="disease"></form:errors>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br><br>
        <div class="row">
            <div class="col">
                <div class="form-group row">
                    <label class="text-info col-sm-2 col-form-label">Status </label>
                    <div class="col-xs-2">
                        <div class="col-sm-10">
                            <form:input class="form-control" path="status"/>
                            <form:errors path="status"></form:errors>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <br><br>
        <div class="row">
            <div class="col">
                <div class="form-group row">
                    <label class="text-info">
                        Insurance number</label>
                    <div class="col-xs-2">
                        <form:input class="form-control" path="insuranceNumber"/>
                        <form:errors path="status"></form:errors>
                    </div>
                </div>
            </div>
        </div>
        <br><br>

        <c:if test="${patient.treatments.size() != 0}">


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
                       onclick="window.location.href ='${deleteButton}'"/>
            </c:forEach>
        </c:if>
        <br>

        <div id="container"></div>
        <input type="button" value="Add treatment"
               onclick="addForm()"/>
        <%----%>
        <div class="form-group row">
            <div class="col-sm-2"></div>
            <div class="col-sm-10">
                <input type="submit" class="btn btn-primary" value="OK"/>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>