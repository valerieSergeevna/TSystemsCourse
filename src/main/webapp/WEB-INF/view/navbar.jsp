<%@include file="index.jsp" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style>
    <nav class="navbar topnav navbar-expand-md navbar-light bg-light">
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link text-info" href="#">Patients </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-info" href="#">Medicines/Procedures</a>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <div class="role text-info">Doctor</div>
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

</body>
</html>
