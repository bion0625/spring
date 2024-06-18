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
    <title>User Add</title>
</head>
<body>
    <form method="post">
        <label>
            <input name="id" type="text" value="${currentUser.id}" placeholder="id">
        </label>
        <br/>
        <label>
            <input name="password" type="password" value="${currentUser.password}" placeholder="password">
        </label>
        <br/>
        <label>
            <input name="name" type="text" value="${currentUser.name}" placeholder="name">
        </label>
        <br/>
        <label>
            <input name="email" type="text" value="${currentUser.email}" placeholder="email">
        </label>
        <button type="submit">add</button>
    </form>
</body>
</html>
