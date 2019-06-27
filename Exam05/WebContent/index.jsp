<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>欢迎系统</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" src="JS/jquery.js"></script>
    <script src="JS/cloud.js" type="text/javascript"></script>
</head>

<body>


<div >
    <div ">
        <form action="./teacher/teacher_index.jsp" method="post" style="border:1px;" >
                学号：<input style="border: 1px;" name="id" id="username" type="text"  placeholder="学号/工号"/><br>
                密码：<input style="border: 1px;" name="password" type="password" placeholder="密码"/><br>          
                <input style="border: 1px;" type="submit"  value="登录"/>
        </form>
    </div>
</div>
</body>

</html>
