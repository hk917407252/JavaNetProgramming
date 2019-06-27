<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>成绩查询</title>
</head>
<body>
	<!--定义一个student类存储学生信息  -->
	<% class Student{int sno;String name;String lecture; float grade;} %>
	<!-- 建立一个以student对象为元素的列表 -->
	<% List<Student> studentList = new ArrayList(); %>
	
    <form method="post" action="" style="font-size: 15px" name = "myForm">
        姓名：<select name="sname">
	  		<option value ="何文勇">何文勇</option>
	  		<option value ="李强">李强</option>
	  		<option value="雷发根">雷发根</option>
	  		<option value="黄凯">黄凯</option>
		</select>
        课程：<select name="lecture">
	  		<option value ="软件工程">软件工程</option>
	  		<option value ="数据库原理">数据库原理</option>
	  		<option value="大型数据库">大型数据库</option>
	  		<option value="数据结构">数据结构</option>
	  		<option value="java">java</option>
		</select>
        <input type="submit" value="查询">
    </form>
    <p></p>
    
    <% 	request.setCharacterEncoding("utf-8");
    	response.setCharacterEncoding("utf-8");
    	Class.forName("org.h2.Driver");
        /* String url = "jdbc:h2:./mydb"; */
        String url = "jdbc:h2:D:/RuanJian/H2/mydb"; 
        String name = "sa";
        String password = "";
        Connection connention = DriverManager.getConnection(url,name,password);
        String sname = request.getParameter("sname");
        String lecture = request.getParameter("lecture");
        
        String sql = "select * from student_grade where name = '"+ sname + "' and lecture = '" + lecture +"'";
        Statement statement = connention.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        /* 将所有student对象都封装进列表中 */
        while(rs.next()){
            Student s = new Student() ;
            s.sno = Integer.parseInt(rs.getString("sno"));
            s.name =rs.getString("name");
            s.lecture = rs.getString("lecture");
            s.grade = Float.parseFloat(rs.getString("grade"));
            studentList.add(s);
        }
        /* 关闭链接 */
        statement.close();
        connention.close();
        rs.close();
        %>
        
    <table border="1" style="font-size: 15px">
    	<tr>
	     	<td style="width: 100px;text-align: center">学号</td>
	        <td style="text-align: center">姓名</td>
	        <td style="text-align: center">课程</td>
	        <td>成绩</td>
    	</tr>
    	  	
    <% if(studentList !=null) {%>
    	<% for(int i=0;i<studentList.size();i++){ %>
    		<tr>
	            <td style="width: 100px;text-align: center"><%out.print(studentList.get(i).sno);%></td>
	            <td style="text-align: center"><%out.print(studentList.get(i).name);%></td>
	            <td style="text-align: center"><%out.print(studentList.get(i).lecture);%></td>
	            <td style="text-align: center"><%out.print(studentList.get(i).grade);%></td>
        	</tr>
    	<%}%>
    <%}%>    
    </table>
</body>
</html>
