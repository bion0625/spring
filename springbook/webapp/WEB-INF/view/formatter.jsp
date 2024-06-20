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
    <title>Formatter Product</title>
</head>
<body>
<form method="post">
    <label>
        <input name="name" placeholder="${product.name}">
    </label>
    <br/>
    <label>
        <input name="price" placeholder="${product.price}">
    </label>
    <br/>
    <label>
        <input name="birthday" type="date" value="${product.now}">
    </label>
    <button>submit</button>
</form>
</body>
</html>
