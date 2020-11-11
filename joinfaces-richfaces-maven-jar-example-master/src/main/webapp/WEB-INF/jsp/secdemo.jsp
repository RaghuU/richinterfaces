<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UserSearch</title>
</head>
<body>
    <div align="center">
        <form:form action="usersearch" method="post" modelAttribute="userForm">
        <tr>
                 <td>  <a href="welcome">HOME</a> </td>
                  </tr>
            <table border="0">
                <tr>
                    <td colspan="2" align="center"><h2>User Search</h2></td>
                </tr>
                <tr>
                    <td>User Id:</td>
                    <td><form:input path="id" /></td>
                    
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Search" /></td>
                </tr>
            </table>
        </form:form>
    </div>
    
    <div>
    
    <p><b>Users Data:</b><p>

	<ol>
		<c:forEach var="user" items="${usersList}">
		
			<li>${user.name}</li>
			
		</c:forEach>
	</ol>
    </div>
</body>
</html>