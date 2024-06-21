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
    <title>webDataBinder</title>
</head>
<body>
    <form method="post">
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
            autoLogin
            <input name="autoLogin" type="checkbox" ${user.autoLogin ? 'checked' : ''}>
            <input name="_autoLogin" type="hidden" value="on"/>
        </label>
        <br/>
        <label>
            <span>관리자(?) : <input type="checkbox" name="type" value="admin" ${user.type=='admin' ? 'checked' : ''}></span>
            <input type="hidden" name="!type" value="member"/>
        </label>
        <button type="submit">webDataBinder</button>
    </form>
</body>
</html>
