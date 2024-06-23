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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>FORM USER ADD</title>
    <style>
        .errorMessage {border: 2px solid red;}
    </style>
</head>
<body>
<form:form commandName="user" method="post">
    <p>
        <form:label path="name" cssErrorClass="errorMessage">Name</form:label>
        <form:input path="name" size="30"/>
        <form:errors path="name" cssClass="errorMessage"/>
    </p>
    <p>
        <form:label path="age">Age</form:label>
        <form:input path="age" size="30"/>
        <form:errors path="age" cssClass="errorMessage"/>
    </p>
    <p>
        <c:forEach var="interest" items="${interests}">
            <form:checkbox path="interests" value="${interest}" label="${interest}"/>
        </c:forEach>
    </p>
    <p>
<%--        <form:radiobuttons path="type" items="${types}" itemValue="value" itemLabel="key"/>--%>
        <%--<form:radiobutton path="type" label="관리자" value="1"/>
        <form:radiobutton path="type" label="회원" value="2"/>
        <form:radiobutton path="type" label="손님" value="3"/>--%>
    </p>
    <p>
        <form:label path="type">Type: </form:label>
        <form:select path="type">
            <form:option value=" " label="-- 선택해주세요 --"/>
            <form:options items="${types}" itemValue="value" itemLabel="key"/>
        </form:select>
    </p>

    <button>submit</button>
</form:form>
</body>
</html>
