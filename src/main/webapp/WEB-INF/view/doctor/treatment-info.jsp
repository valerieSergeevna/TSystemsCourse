<%@ page import="com.spring.webapp.dto.PatientDTOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@include file="../general/navbar.jsp" %>

<html>

<body>

<%--<div class="container">--%>
<%--    <form:form class="main-form text-info" action="/saveTreatment" modelAttribute="patient">--%>
<%--        <h2 class="text-info">Treatment info</h2>--%>
<%--        <br>--%>
<%--        <c:if test="${patient.id != null}">--%>
<%--            <form:hidden path="id"></form:hidden>--%>
<%--        </c:if>--%>
<%--        <div class="row mb-4">--%>
<%--            <div class="col">--%>
<%--                <div class="form-outline">--%>
<%--                    <label class="form-label" for="form3Example1">Name</label>--%>
<%--                    <form:input id="form3Example1" class="form-control" path="name"/>--%>
<%--                    <form:errors path="name"></form:errors>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="col">--%>
<%--                <div class="form-outline">--%>
<%--                    <label class="form-label" for="form3Example2">Last name</label>--%>
<%--                    <form:input class="form-control" id="form3Example2" path="surname"/>--%>
<%--                    <form:errors path="surname"></form:errors>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="form-outline mb-4">--%>
<%--            <label class="form-label" for="form3Example3">Ages</label>--%>
<%--            <form:input class="form-control" id="form3Example3" path="ages"/>--%>
<%--            <form:errors path="ages"></form:errors>--%>
<%--        </div>--%>

<%--        <div class="form-outline mb-4">--%>
<%--            <label class="form-label" for="form3Example4">Disease</label>--%>
<%--            <form:input id="form3Example4" class="form-control" path="disease"/>--%>
<%--            <form:errors path="disease"></form:errors>--%>
<%--        </div>--%>
<%--        <div class="form-outline mb-4">--%>
<%--            <label class="form-label" for="form3Example5">Status</label>--%>
<%--            <select class="form-control"--%>
<%--                    id="form3Example5" name="status">--%>
<%--                <option selected value="${patient.status}">${patient.status}</option>--%>
<%--                <option value="in process">in process</option>--%>
<%--                <option value="discharged">discharged</option>--%>
<%--            </select>--%>
<%--        </div>--%>

<%--        <div class="form-outline mb-4">--%>
<%--            <label class="form-label" for="form3Example6">Insurance number</label>--%>
<%--            <form:input class="form-control" id="form3Example6" path="insuranceNumber"/>--%>
<%--            <form:errors path="insuranceNumber"></form:errors>--%>

<%--        </div>--%>
<%--        <c:if test="${patient.treatments.size() != 0}">--%>
<%--            <c:forEach items="${patient.treatments}" var="treatment" varStatus="count">--%>
<%--                <input type="hidden" name="treatment" value="${treatment.treatmentId}">--%>
<%--                <br><br>--%>

<%--                <div class="row mb-4">--%>
<%--                    <div class="col">--%>
<%--                        <div class="form-outline mb-4">--%>
<%--                            <label class="form-label" for="form3Example7${count.index}">Medicine/Procedure name</label>--%>
<%--                            <input type="text" class="form-control"--%>
<%--                                   id="form3Example7${count.index}" name="treatmentName"--%>
<%--                                   value="${treatment.typeName}" readonly>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                    <div class="col">--%>
<%--                        <div class="form-outline">--%>
<%--                            <div class="form-outline mb-4">--%>
<%--                                <label class="form-label" for="form3Example8${count.index}">Type</label>--%>
<%--                                    &lt;%&ndash; <select class="form-control"--%>
<%--                                             for="form3Example8${count.index}" name="treatmentType">--%>
<%--                                         <option selected value="${treatment.type}">${treatment.type}</option>--%>
<%--                                         <option value="medicine">medicine</option>--%>
<%--                                         <option  value="procedure">procedure</option>--%>
<%--                                     </select>&ndash;%&gt;--%>
<%--                                <input type="text" class="form-control"--%>
<%--                                       id="form3Example8${count.index}"--%>
<%--                                       name="treatmentType" value="${treatment.type}" readonly>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="form-outline mb-4">--%>
<%--                    <label class="form-label" for="form3Example9${count.index}">Time Pattern </label>--%>
<%--                    <input type="text" class="form-control"--%>
<%--                           id="form3Example9${count.index}" name="treatmentPattern"--%>
<%--                           value="${treatment.timePattern}">--%>
<%--                </div>--%>
<%--                <div class="form-outline mb-4">--%>
<%--                    <label class="form-label" for="form3Example10${count.index}">Dose</label>--%>
<%--                    <input type="text" class="form-control"--%>
<%--                           id="form3Example10${count.index}" name="treatmentDose" value="${treatment.dose}">--%>
<%--                </div>--%>
<%--                <div class="form-outline mb-4">--%>
<%--                    <label class="form-label" for="form3Example11${count.index}">Period</label>--%>
<%--                </div>--%>
<%--                                <div class="form-outline mb-4">--%>
<%--                                    <label class="form-label" for="datepicker1${count.index}">Start date</label>--%>
<%--                                    <input type="text" class="form-control datepicker" name="startDate"--%>
<%--                                           id="datepicker1${count.index}" value="${treatment.startDate}">--%>
<%--                                </div>--%>
<%--                                <div class="form-outline mb-4">--%>
<%--                                    <label class="form-label" for="datepicker2${count.index}">End date</label>--%>
<%--                                    <input type="text" class="form-control datepicker" name="endDate"--%>
<%--                                           id="datepicker2${count.index}" value="${treatment.endDate}">--%>
<%--                                </div>--%>
<%--                <c:url var="deleteButton" value="/deleteTreatment">--%>
<%--                    <c:param name="treatmentId" value="${treatment.treatmentId}"></c:param>--%>
<%--                </c:url>--%>
<%--                <input type="button" class="btn danger btn-outline-danger btn-sm " value="Delete treatment"--%>
<%--                       onclick="window.location.href ='${deleteButton}'"/>--%>

<%--            </c:forEach>--%>
<%--        </c:if>--%>
<%--        <br>--%>
<%--        <div id="container"></div>--%>
<%--        <br>--%>
<%--        <input class="btn btn-outline-primary btn-sm" type="button" value="Add treatment"--%>
<%--               onclick="addForm()"/>--%>
<%--        &lt;%&ndash;&ndash;%&gt;--%>
<%--        <br>--%>
<%--        <br>--%>
<%--        <input type="submit" class="btn btn-outline-success btn-sm" value="Save"/>--%>

<%--        &lt;%&ndash;    <c:if test="${patient.id != null}">&ndash;%&gt;--%>
<%--        &lt;%&ndash;        &lt;%&ndash; <form:hidden path="id"></form:hidden>&ndash;%&gt;&ndash;%&gt;--%>
<%--        &lt;%&ndash;        ${patient.surname}&ndash;%&gt;--%>
<%--        &lt;%&ndash;        <br>&ndash;%&gt;--%>
<%--        &lt;%&ndash;        ${patient.treatments.get(0).dose}&ndash;%&gt;--%>
<%--        &lt;%&ndash;        <br>&ndash;%&gt;--%>
<%--        &lt;%&ndash;        <input type="text" class="form-control datepicker" name="endDate" value="${patient.treatments.get(0).endDate}"/>&ndash;%&gt;--%>
<%--        &lt;%&ndash;        <input type="submit" class="btn btn-outline-success btn-sm" value="Save"/>&ndash;%&gt;--%>
<%--        &lt;%&ndash;    </c:if>&ndash;%&gt;--%>

<%--    </form:form>--%>
<%--</div>--%>

<div class="container">
    <form:form  action="/saveTreatment" class="main-form text-info" modelAttribute="patient">

        <h2 class="text-info">Treatment info</h2>
        <br>
        <c:if test="${patient.id != null}">
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

        <c:if test="${patient.treatments.size() != 0}">
            <c:forEach items="${patient.treatments}" var="treatment" varStatus="count">
                <input type="hidden" name="treatment" value="${treatment.treatmentId}">
                <br><br>
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
                    <label class="form-label" for="form3Example9${count.index}">Time Pattern </label>
                    <input type="text" class="form-control"
                           id="form3Example9${count.index}" name="treatmentPattern"
                           value="${treatment.timePattern}">
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="form3Example10${count.index}">Dose</label>
                    <input type="text" class="form-control"
                           id="form3Example10${count.index}" name="treatmentDose" value="${treatment.dose}">
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="form3Example11${count.index}">Period</label>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="datepicker1${count.index}">Start date</label>
                    <input type="text" class="form-control datepicker" name="startDate"
                           id="datepicker1${count.index}" value="${treatment.startDate}">
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="datepicker2${count.index}">End date</label>
                    <input type="text" class="form-control datepicker" name="endDate"
                           id="datepicker2${count.index}" value="${treatment.endDate}">
                </div>
                <%--</form:form>--%>
                <c:url var="deleteButton" value="/deleteTreatment">
                    <c:param name="treatmentId" value="${treatment.treatmentId}"></c:param>
                </c:url>
                <input type="button" class="btn danger btn-outline-danger btn-sm " value="Delete treatment"
                       onclick="window.location.href ='${deleteButton}'"/>

            </c:forEach>
        </c:if>
        <br>
        <div id="container"></div>
        <br>
        <input class="btn btn-outline-primary btn-sm" type="button" value="Add treatment"
               onclick="addForm()"/>
        <%----%>
        <br>
        <br>
        <input type="submit" class="btn btn-outline-success btn-sm" value="Save"/>
    </form:form>
</div>
<script type="text/javascript">
    <%@include file="/resources/scripts/appScript.js"%>
</script>
</body>
</html>