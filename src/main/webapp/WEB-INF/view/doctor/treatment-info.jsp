<%@ page import="com.spring.webapp.dto.PatientDTOImpl" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@include file="../general/navbar.jsp" %>

<html>
<body>
<div class="container">
    <form:form action="/saveTreatment" class="main-form text-info" modelAttribute="patient">

        <h2 class="text-info">Treatment info</h2>
        <button type="submit" class="btn btn-outline-success btn-circle btn-xl" data-toggle="tooltip"
                data-placement="right"
                title="save all information" value="Save"><i class="fa fa-check"></i>
        </button>
        <br>
        <h3 class="text-info">Patient info</h3>
        <br>
        <c:if test="${patient.id != null}">
            <form:hidden path="id"></form:hidden>
        </c:if>
        <security:authorize access="hasAnyRole('ADMIN')">
            <div class="form-outline mb-4">
                <label class="form-label" for="docId">Doctor id</label>
                <input name="doctorId" id="docId" value="${patient.doctor.id}" class="form-control">
<%--                <form:input class="form-control" id="docId" path="doctor.id"/>--%>
<%--                <form:errors path="doctor.id"></form:errors>--%>
            </div>
        </security:authorize>

        <security:authorize access="hasAnyRole('DOCTOR')">
            <div class="form-outline mb-4">
                <input hidden name="doctorId" value="${patient.doctor.id}" class="form-control">
                    <%--                <form:input class="form-control" id="docId" path="doctor.id"/>--%>
                    <%--                <form:errors path="doctor.id"></form:errors>--%>
            </div>
        </security:authorize>
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
            <label class="form-label" for="form3Example3">Ages</label>
            <form:input class="form-control" id="form3Example3" path="ages"/>
            <form:errors path="ages"></form:errors>
        </div>

        <div class="form-outline mb-4">
            <label class="form-label" for="form3Example4">Disease</label>
            <form:input id="form3Example4" class="form-control" path="disease"/>
            <form:errors path="disease"></form:errors>
        </div>
        <div class="form-outline mb-4">
                <%--
                <form:input class="form-control" id="form3Example5" path="status"/>
                <form:errors path="status"></form:errors>--%>
            <label class="form-label" for="form3Example5">Status</label>
            <select class="form-control"
                    id="form3Example5" name="status">
                <option selected value="${patient.status}">${patient.status}</option>
                <option value="in process">in process</option>
                <option value="discharged">discharged</option>
            </select>
        </div>

        <div class="form-outline mb-4">
            <label class="form-label" for="form3Example6">Insurance number</label>
            <form:input class="form-control" id="form3Example6" path="insuranceNumber"/>
            <form:errors path="insuranceNumber"></form:errors>

        </div>
        <hr/>

        <h3 class="text-info">Prescriptions</h3>

        <c:if test="${patient.treatments.size() != 0}">
            <c:forEach items="${patient.treatments}" var="treatment" varStatus="count">
                <input type="hidden" name="treatment" value="${treatment.treatmentId}">
                <br>
                <div class="col">
                    <div class="form-outline mb-4">
                        <c:url var="deleteButton" value="/deleteTreatment">
                            <c:param name="treatmentId" value="${treatment.treatmentId}"></c:param>
                        </c:url>
                        <button type="button" class="btn btn-outline-danger btn-circle"
                                data-toggle="tooltip" data-placement="right"
                                title="delete current prescription"
                                onclick="window.location.href ='${deleteButton}'">
                            <i class="fa fa-times"></i>
                        </button>
                    </div>
                </div>
                <div class="row mb-4">
                    <div class="col">
                        <div class="form-outline mb-4">
                            <label class="form-label" for="form3Example7${count.index}">Medicine/Procedure name</label>
                            <input type="text" class="form-control"
                                   id="form3Example7${count.index}" name="treatmentName"
                                   value="${treatment.typeName}" readonly>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-outline">
                            <div class="form-outline mb-4">
                                <label class="form-label" for="form3Example8${count.index}">Type</label>
                                    <%-- <select class="form-control"
                                             for="form3Example8${count.index}" name="treatmentType">
                                         <option selected value="${treatment.type}">${treatment.type}</option>
                                         <option value="medicine">medicine</option>
                                         <option  value="procedure">procedure</option>
                                     </select>--%>
                                <input type="text" class="form-control"
                                       id="form3Example8${count.index}"
                                       name="treatmentType" value="${treatment.type}" readonly>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="form3Example9${count.index}">Time Pattern (times/day(week)) </label>
                    <input type="number" class="form-control"
                           id="form3Example9${count.index}" name="treatmentPattern"
                           value="${treatment.timePattern}" required min="1" max="5">
                </div>

                <c:if test="${treatment.type.toString().equals('medicine')}">
                    <div class="form-outline mb-4">
                        <label class="form-label" for="form3Example10${count.index}">Dose</label>
                        <input type="number" class="form-control"
                               id="form3Example10${count.index}" name="treatmentDose" value="${treatment.dose}" required
                               min="0" step=".001">
                    </div>
                </c:if>
                <c:if test="${treatment.type.toString().equals('procedure')}">
                    <input type="hidden" name="treatmentDose" value="1"></c:if>

                <div class="form-outline mb-4">
                        <%--<label class="form-label" for="form3Example11${count.index}">Period</label>--%>
                    <h4>Period</h4>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="datepicker1${count.index}">Start date</label>
                    <input type="date" class="form-control datepicker" name="startDate"
                           id="datepicker1${count.index}" value="${treatment.startDate}" required
                           pattern="\d{4}-\d{2}-\d{2}">
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="datepicker2${count.index}">End date</label>
                    <input type="date" class="form-control datepicker" name="endDate"
                           id="datepicker2${count.index}" value="${treatment.endDate}" required
                           pattern="\d{4}-\d{2}-\d{2}" }>
                </div>
                <%--</form:form>--%>

                <%-- <input type="button" class="btn danger btn-circle btn-outline-danger btn-sm " value="Delete treatment"
                        onclick="window.location.href ='${deleteButton}'"/><i class="fa fa-times"></i>
                --%>

            </c:forEach>
        </c:if>
        <br>
        <div id="container"></div>
        <br>
        <input class="btn btn-outline-primary btn-sm" id="newTreatmentButton" type="button" value="Add prescription"
               onclick="addForm()"/>
        <input class="btn btn-outline-danger btn-sm" id="removeTreatmentButton" type="button"
               value="Delete prescription"
               onclick="removeForm()" hidden="true"/>
        <%----%>
        <br>
        <br>
        <input class="btn btn-outline-warning btn-sm" id="showBinButton" type="button" onclick="showOrHideBinForm(true)"
               value="Show bin treatments"/>
        <input class="btn btn-outline-warning btn-sm" id="hideBinButton" type="button"
               onclick="showOrHideBinForm(false)" value="Hide bin treatments" hidden/>
        <%--        <input type="submit" class="btn btn-outline-success btn-sm" value="Save"/>--%>
    </form:form>
    <form id="binListId" class="main-form text-info" hidden>
        <c:if test="${bin != null}">
            <h3 class="text-info">Bin prescriptions</h3>
            <c:forEach items="${bin}" var="binTreatment" varStatus="count">
                <input type="hidden" name="binTreatment" value="${binTreatment.treatmentId}">
                <br>
                <div class="col">
                    <div class="form-outline mb-4">
                    </div>
                </div>
                <div class="row mb-4">
                    <div class="col">
                        <div class="form-outline mb-4">
                            <label class="form-label" for="treatmentNameId${count.index}">Medicine/Procedure
                                name</label>
                            <input type="text" class="form-control"
                                   id="treatmentNameId${count.index}" name="treatmentName"
                                   value="${binTreatment.typeName}" readonly>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-outline">
                            <div class="form-outline mb-4">
                                <label class="form-label" for="typeId${count.index}">Type</label>
                                <input type="text" class="form-control"
                                       id="typeId${count.index}"
                                       name="treatmentType" value="${binTreatment.type}" readonly>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="patternId${count.index}">Time Pattern (times/day(week)) </label>
                    <input type="number" class="form-control"
                           id="patternId${count.index}" name="treatmentPattern"
                           value="${binTreatment.timePattern}" required min="1" max="5" readonly>
                </div>

                <c:if test="${binTreatment.type.toString().equals('medicine')}">
                    <div class="form-outline mb-4">
                        <label class="form-label" for="treatmentDoseId${count.index}">Dose</label>
                        <input type="number" class="form-control"
                               id="treatmentDoseId${count.index}" name="treatmentDose" value="${binTreatment.dose}"
                               required
                               min="0" step=".001" readonly>
                    </div>
                </c:if>
                <c:if test="${binTreatment.type.toString().equals('procedure')}">
                    <input type="hidden" name="treatmentDose" value="1"></c:if>

                <div class="form-outline mb-4">
                        <%--<label class="form-label" for="form3Example11${count.index}">Period</label>--%>
                    <h4>Period</h4>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="startDateId${count.index}">Start date</label>
                    <input type="date" class="form-control datepicker" name="startDate"
                           id="startDateId${count.index}" value="${binTreatment.startDate}" required
                           pattern="\d{4}-\d{2}-\d{2}" readonly>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="endDateID${count.index}">End date</label>
                    <input type="date" class="form-control datepicker" name="endDate"
                           id="endDateID${count.index}" value="${binTreatment.endDate}" required
                           pattern="\d{4}-\d{2}-\d{2}" readonly>
                </div>

            </c:forEach>
        </c:if>
    </form>
</div>
<script type="text/javascript">
    <%@include file="/resources/scripts/appScript.js"%>
</script>
</body>
</html>