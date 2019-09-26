<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/resources/css/layout.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet" >
</head>
<body>
<div class="container">
	<h2>사용자 추가 결과</h2>
	${resultMessage}
	<br><br>
<form method="get" action="userList"><input type="submit" value="사용자목록" class="button"></form>

</div>
</body>
</html>