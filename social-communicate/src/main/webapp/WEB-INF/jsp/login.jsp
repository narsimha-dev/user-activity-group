<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<font color="red">${errorMessage}</font>
<form method="post">
  Name: <input type="text name="userName">
  Password: <input type="password" name="userPassword"> 
  <input type="submit"> 
</form>
</body>
</html>