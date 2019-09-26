<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 추가</title>
<link href="${pageContext.request.contextPath}/resources/css/layout.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/form_input_table.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/form_error_msg.css" rel="stylesheet" >
</head>
	<script type="text/javascript">
	/*  뷰단에서 추가 버튼 입력시 실행함수 (ipput값의 유효성 check함수) */
		function submitForm() {
			const name = document.getElementById('name');
			const dept = document.getElementById('dept');
			const jopPosition = document.getElementById('jobPosition');
			
			//기본적으로 널 값을 잡음
	        if(name.value.trim() == "" || dept.value.trim() == "" || jobPosition.value.trim() == ""){
                alert("모든 입력값을 입력하세요");
                return;
            }
	        validUserDto.action = "/userJsp/userInsert";
	        validUserDto.submit();
		}
	</script>
<body>
<div class="container">
<h2>사용자 추가 정보 입력</h2>
	<!-- 서버단에서 유효성 검사 한번 더 이중 유효성 검사 이를 위해 스프링의 form태그 사용 -->
	<!-- form에서도 액션을 지정하고 자바 스크립트에서도 액션 지정함으로써 유효성 검사가 중복되게 함 -->
	<form:form action="/userJsp/userInsert" method="post" modelAttribute="validUserDto">
		<table  class="form_input_table">
			<tr>
				<th>사용자 ID</th>
				<td>자동입력</td>
			</tr>
			<tr>
				<th>이름</th>
				<!-- <td><input type="text" id="name" name="name" maxlength="20" required></td> -->
				<td>
					<form:input path="name" id="name"></form:input>
					<small><form:errors path="name" cssClass="errorMsg"/></small><!-- 서버단 유효성 검사 에러시 메세지 출력 -->
				</td>
			</tr>
			<tr>
				<th>부서</th>
				<td><form:select path="dept" name="dept" id="dept">
							<form:option value="">부서선택</form:option>
							<form:option value="영업본부">영업본부</form:option>
							<form:option value="기술사업">기술사업</form:option>
							<form:option value="전략사업">전략사업</form:option>
							<form:option value="UC사업">UC사업</form:option>
							<form:option value="경영관리">경영관리</form:option>
							<form:option value="기타">기타</form:option></form:select>
					<small><form:errors path="dept" cssClass="errorMsg"/></small>
				</td>
			</tr>
			<tr>
				<th>직위</th>
				<td><form:select path="jobPosition" name="jobPosition" id="jobPosition">
							<form:option value="">직위선택</form:option>
							<form:option value="사원">사원</form:option>
							<form:option value="대리">대리</form:option>
							<form:option value="과장">과장</form:option>
							<form:option value="차장">차장</form:option>
							<form:option value="부장">부장</form:option>
							<form:option value="기타">기타</form:option></form:select>
					<small><form:errors path="jobPosition" cssClass="errorMsg"/></small>
				</td>
			</tr>
			<tr>
				<th>등록일</th>
				<td>자동입력</td>
			</tr>
		</table>
		<br><!-- onclick='submitForm()' value="추가" -->
		<div style="width: 100%; text-align: right;">
		<span><input class="button" type="button" value=취소 onclick='location.href="/userJsp/userList"'></span>
		<span><button class="button" onclick='submitForm()'>추가</button></span>
<!-- 		<span><button  type="submit" class="button">추가</button></span> -->
		</div>
	 </form:form>
</div>
</body>
</html>