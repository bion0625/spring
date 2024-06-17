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
    <title>User Edit</title>
</head>
<body>
    <form method="post">
        <input name="id" type="text" disabled value="${user.id}">
        <input name="name" type="text" value="${user.name}"/>
        <input name="email" type="text" value="${user.email}"/>
        <%--<input type="hidden" name="level" value="${user.level}"/>--%>
        <%--<input type="hidden" name="password" value="${user.password}"/>
        <input type="hidden" name="login" value="${user.login}"/>
        <input type="hidden" name="recommend" value="${user.recommend}"/>--%>
        <button type="submit">edit</button>
    </form>
</body>
</html>
