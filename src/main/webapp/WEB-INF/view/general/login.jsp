<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="index.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style>

</head>
<body>


        <c:url  value="/login" var="loginUrl"/>
        <div class="login container">
        <form action="${loginUrl}" method="post">
            <c:if test="${param.error != null}">
                <p class="text-danger" >
                    Invalid username and password.
                </p>
            </c:if>
            <c:if test="${param.logout != null}">
                <p class="text-info">
                    You have been logged out.
                </p>
            </c:if>
            <div class="form-outline mb-4">
                <label class="form-label" for="username">Username</label>
                <input class="form-control" type="text" id="username" name="username"/>
            </div>
            <div class="form-outline mb-4">
                <label class="form-label" for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password"/>
            </div>
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <button type="submit" class="btn btn-info btn-block" class="btn">Log in</button>
        </form>
    </div>
</body>
</html>