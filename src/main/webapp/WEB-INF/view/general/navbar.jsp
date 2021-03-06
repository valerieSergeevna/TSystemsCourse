<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@include file="index.jsp" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style>
    <nav class="navbar topnav navbar-expand-md ">
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link text-info" href="/patients">Patients </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-info" href="/medicineProcedure">Medicines/Procedures</a>
                </li>
                <security:authorize access="hasAnyRole('NURSE', 'ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link text-info" href="/nurse/showAllTreatments">Treatments event</a>
                    </li>
                </security:authorize>

<%--                <security:authorize access="hasRole('ADMIN')">--%>
<%--                    <li class="nav-item">--%>
<%--                        <a class="nav-link text-info" href="/registration">Registration</a>--%>
<%--                    </li>--%>
<%--                </security:authorize>--%>
                <security:authorize access="hasRole('ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link text-info" href="/users">Users</a>
                    </li>
                </security:authorize>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <security:authorize access="hasRole('DOCTOR')">
                        <div class="role text-info">Doctor</div>
                    </security:authorize>
                    <security:authorize access="hasRole('NURSE')">
                        <div class="role text-info">Nurse</div>
                    </security:authorize>
                    <security:authorize access="hasRole('ADMIN')">
                        <div class="role text-info">ADMIN</div>
                    </security:authorize>

                </li>

                <li class="nav-item">
                    <a class="role text-info" href="/logout">logout</a>
                </li>

                <%-- <li class="nav-item">
                     <a class="nav-link" href="#">Register</a>
                 </li>--%>
            </ul>
        </div>
    </nav>
    <%--  <nav class="navbar topnav text-info navbar-expand-sm
       nav-tabs bg-light navbar-collapse justify-content-between fixed-top">
          <a class="navbar-brand" href="#">Patients</a>
          <a class="navbar-brand" href="#">Medicines/Procedures</a>--%>
    <%--security--%>
    <%--   <div class ="navbar-brand">
           Doctor
       </div>
   </nav>--%>
</head>
<body>
<script type="text/javascript" src="/webjars/js-cookie/js.cookie.js"></script>
</body>
</html>
