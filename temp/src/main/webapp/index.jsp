<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-06-06
  Time: 오전 2:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="main.temp.HelloSpring" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(
            request.getSession().getServletContext());
    HelloSpring helloSpring = context.getBean(HelloSpring.class);

    System.out.println(helloSpring.sayHello("Root Context"));
%>
</body>
</html>
