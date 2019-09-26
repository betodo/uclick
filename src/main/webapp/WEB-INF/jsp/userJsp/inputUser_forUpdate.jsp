<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 정보 수정</title>
<link href="${pageContext.request.contextPath}/resources/css/layout.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/form_input_table.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet" >
	<script type="text/javascript">
	/* 수정 버튼 입력시 실행함수 (ipput값의 유효성 check함수) */
		function submitForm() {
			const name = document.getElementById('name');
			const dept = document.getElementById('dept');
			const jobPosition = document.getElementById('jobPosition');
			
		 	if(name.value.trim() == "" || dept.value.trim() == "" || jobPosition.value.trim() == ""){
                alert("모든 입력값을 입력하세요");
                return;
            }
		 	updateUserForm.action = "userUpdate";
			updateUserForm.submit();
		}
	</script>
</head>
<body>
<div class="container">
<h2>사용자 정보 수정</h2>
	<form method="post" id="updateUserForm">
	<input type="hidden" value="${user.id}" name="id"><!-- 엔터티의 변수명과 같게! -->
	<table class="form_input_table">
			<tr>
				<th>사용자 ID</th>
				<td>${user.id}</td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" value="${user.name}" id="name" name="name" maxlength="20" required></td>
			</tr>
			<tr>
				<th>부서</th>
				<td><select name="dept" id="dept">
							<option value="${user.dept}">${user.dept}</option>
							<option value="영업본부">영업본부</option>
							<option value="기술사업">기술사업</option>
							<option value="전략사업">전략사업</option>
							<option value="UC사업">UC사업</option>
							<option value="경영관리">경영관리</option>
							<option value="기타">기타</option>
					</select></td>
			</tr>
			<tr>
				<th>직위</th>
				<td><select name="jobPosition" id="jobPosition">
							<option value="${user.jobPosition}">${user.jobPosition}</option>
							<option value="사원">사원</option>
							<option value="대리">대리</option>
							<option value="과장">과장</option>
							<option value="차장">차장</option>
							<option value="부장">부장</option>
							<option value="기타">기타</option>
					</select></td>
			</tr>
			<tr>
				<th>등록일</th>
				<td>${user.regDate}</td>
			</tr>
	</table>
	<br>
	<div style="width: 100%; text-align: right;">
		<span><input class="button" type="button" value=취소 onclick='location.href="/userJsp/userList"'></span>
		<span><input class="button" type="button" value=수정 onclick='submitForm()'></span>
	</div>
	</form>
</div>
</body>
</html>