<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>welcome</title>
</head>
<body>
    <div align="center">
       
            <table border="0">
            	<tr>
                  	<td><a href="start.xhtml">RichFaces</a> 
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><h2>Security Vulnarability Issues</h2></td>
                </tr>
                <tr>
                 <td>  <a href="sqlinj">Sql Injection</a> </td>
                  </tr>
                   <tr>
                  	<td><a href="xssattack?xss=<script>alert(1)</script>">XSS Attack</a> 
                    </td>
                    </tr> 
                     <tr>
                  	<td><a href="xsssafe?xss=<script>alert(1)</script>">XSS Safe</a> 
                    </td>
                    </tr> 
               <tr>
                 <td>  <a href="codeinject?filepath=/tmp;cat /etc/passwd">Command Injection</a> </td>
                  </tr>
                  <tr>
                 <td>  <a href="redirect?url=http://www.google.com">Redirect</a> </td>
                  </tr>
                  <tr>
                 <td>  <a href="vuln/referer?callback_=test">JSONP</a> </td>
                  </tr>
                  <tr>
                 <td>  <a href="path_traversal/vul?filepath=../../../../../etc/passwd">PathTraversal</a> </td>
                  </tr>
                  <tr>
                 <td>  <a href="ssrf/vuln?url=url=file:///etc/passwd">SSRF</a> </td>
                  </tr>
                   <tr>
                 <td>  <a href="xstream">RCEXtream</a> </td>
                  </tr>
                  <tr>
                 <td>  <a href="crossorigin">CrossOrigin</a> </td>
                  </tr> <tr>
                 <td>  <a href="/rce/exec">RCE</a> </td>
                  </tr>
                  <tr>
                 <td>  <a href="/fileupload/pic">File Upload</a> </td>
                  </tr>
                  <tr>
                 <td>  <a href="crlf/safecode">CRLF</a> </td>
                  </tr>
            </table>
       
    </div>
    
   
</body>
</html>