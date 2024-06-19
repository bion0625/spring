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
    <title>User Type</title>
</head>
<body>
    <form action="add">
        <label>
            <input name="id" type="number">
        </label>
        <br/>
        <label>
            <input name="name">
        </label>
        <br/>
        <label>
            <select name="userType">
                <option value="1">관리자</option>
                <option value="2">회원</option>
                <option value="3">손님</option>
            </select>
        </label>
        <button>submit</button>
    </form>
</body>
</html>
