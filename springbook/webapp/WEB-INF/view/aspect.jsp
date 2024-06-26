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
    <title>ASPECT</title>
</head>
<body>
    <form method="post">
        <label>ID:
            <input name="id">
        </label>
        <br/>
        <label>NAME:
            <input name="name">
        </label>
        <label>PASSWORD:
            <input type="password" name="password">
        </label>
        <label>EMAIL:
            <input name="email">
        </label>
        <br/>
        <label>LEVEL:
            <select name="level">
                <option value="1">BASIC</option>
                <option value="2">SILVER</option>
                <option value="3">GOLD</option>
            </select>
        </label>
        <button>submit</button>
    </form>
</body>
</html>
