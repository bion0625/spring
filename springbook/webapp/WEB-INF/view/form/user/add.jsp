<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-06-06
  Time: 오전 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>FORM USER ADD</title>
    <style>
        .errorMessage {border: 2px solid red;}
    </style>
</head>
<body>
<form method="post">
    <p>
        <spring:bind path="user.name">
            <label for="${status.expression}" <c:if test="${status.errorMessage != ''}">class="errorMessage" </c:if>>Name: </label>
            <input type="text" id="${status.expression}" name="${status.expression}"
                   value="${status.value}"/>
            <span class="errorMessage">
                <c:forEach var="errorMessage" items="${status.errorMessages}">
                    ${errorMessage}
                </c:forEach>
            </span>
        </spring:bind>
    </p>
    <p>
        <spring:bind path="user.age">
            <label for="${status.expression}" <c:if test="${status.errorMessage != ''}">class="errorMessage" </c:if>>Age: </label>
            <input type="text" id="${status.expression}" name="${status.expression}" value="${status.value}"/>
            <span class="errorMessage">
                <c:forEach var="errorMessage" items="${status.errorMessages}">
                    ${errorMessage}
                </c:forEach>
            </span>
        </spring:bind>
    </p>

    <button>submit</button>
</form>
</body>
</html>
