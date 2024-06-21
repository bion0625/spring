<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-06-06
  Time: 오전 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>validator</title>
</head>
<body>
    <form action="controller" method="post">
        <h3>Controller</h3>
        <label>
            id
            <input name="id" type="text" value="${user.id}" placeholder="id">
        </label>
        <br/>
        <label>
            password
            <input name="password" type="password" value="${user.password}" placeholder="password">
        </label>
        <br/>
        <label>
            name
            <input name="name" type="text" value="${user.name}" placeholder="name">
        </label>
        <br/>
        <label>
            email
            <input name="email" type="text" value="${user.email}" placeholder="email">
        </label>
        <br/>
        <label>
            age
            <input name="age" type="number" value="${user.age}" placeholder="age">
        </label>
        <button type="submit">validator</button>
    </form>

    <form action="atValid" method="post">
        <h3>@Valid</h3>
        <label>
            id
            <input name="id" type="text" value="${user.id}" placeholder="id">
        </label>
        <br/>
        <label>
            password
            <input name="password" type="password" value="${user.password}" placeholder="password">
        </label>
        <br/>
        <label>
            name
            <input name="name" type="text" value="${user.name}" placeholder="name">
        </label>
        <br/>
        <label>
            email
            <input name="email" type="text" value="${user.email}" placeholder="email">
        </label>
        <br/>
        <label>
            age
            <input name="age" type="number" value="${user.age}" placeholder="age">
        </label>
        <button type="submit">validator</button>
    </form>
</body>
</html>
