<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전화기 정보 수정</title>
<link href="${pageContext.request.contextPath}/resources/css/layout.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/form_input_table.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet" >

</head>
<body>
<div class="container">
<h2>전화기 정보 수정</h2>
	<form method="post" id="inputPhoneForm">
		<input type="hidden" value="${phone.id}" name="id">
		<table class="form_input_table">
			<tr>
				<th>전화기 ID</th>
				<td>${phone.id}</td>
			</tr>
			<tr>
				<th>번호</th>
				<td>
					<span><input type="text" name="number1" id="number1" value="${number1}" maxlength="3" 
							onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" required></span>-
					<span><input type="text" name="number2" id="number2" value="${number2}" maxlength="4"
							onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" required></span>-
					<span><input type="text" name="number3" id="number3" value="${number3}" maxlength="4"
							onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" required></span>
				</td>
			</tr>
			<tr>
				<th>통신사</th>
				<td><select name="agency" id="agency">
							<option value="${phone.agency}">${phone.agency}</option>
							<option value="SKT">SKT</option>
							<option value="KT">KT</option>
							<option value="LGU+">LGU+</option>
							<option value="기타">기타</option>
					</select></td>
			</tr>
		</table>
		<input type="hidden" value="${phone.user.id}" name="userId">
		<br>
		<div style="width: 100%; text-align: right;">
			<span><input class="button" type="button" value=취소 onclick='history.back()'></span>
			<span><input class="button" type="submit" value=수정 formaction="/phoneJsp/phoneUpdate"></span>
		</div>
	</form>
</div>
</body>
</html>