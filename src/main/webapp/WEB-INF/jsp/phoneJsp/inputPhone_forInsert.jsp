<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전화기 추가</title>
<link href="${pageContext.request.contextPath}/resources/css/layout.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/form_input_table.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/form_error_msg.css" rel="stylesheet" >
<style type="text/css">
	.error {margin-right: 6em;}
</style>
<script type="text/javascript">
	/* 수정 버튼 입력시 실행함수 (ipput값의 유효성 check함수) */
		function submitForm() {
			const num1 = document.getElementById('num1');
			const num2 = document.getElementById('num2');
			const num3 = document.getElementById('num3');
			const agency = document.getElementById('agency');
		
			//null값 check
		 	 if(num1.value.trim() == "" || num2.value.trim() == "" || num3.value.trim() == ""
		 			|| agency.value.trim() == ""){
                alert("모든 입력값을 입력하세요");
                return;
            }
			//폼에서도 액션을 지정하고 자바스크립트에서도 액션을 지정하여
			//유효성검사가 버튼 클릭 한번으로 뷰단, 서버단 중복 검사되게함
            fm.action = "/phoneJsp/phoneInsert";
            fm.submit();
		}
	</script>
</head>
<body>
<div class="container">
<h2>전화기 추가 정보 입력</h2>
<form:form method="post" modelAttribute="validPhoneDto" action="/phoneJsp/phoneInsert">
		<table class="form_input_table">
			<tr>
				<th>전화기ID</th>
				<td>자동입력</td>
			</tr>
			<tr>
				<th>번호</th>
				<td>
					<span>
						<form:input path="number1" id="num1" name="number1" maxlength="3"
							onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"></form:input>							
					</span>-
					<span>
						<form:input path="number2" id="num2" name="number2" maxlength="4"
							onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"></form:input>
					</span>-
					<span>
						<form:input path="number3" id="num3" name="number3" maxlength="4"
							onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"></form:input>
					</span>
					<br>
					<small class="error"><form:errors path="number2" cssClass="errorMsg"/></small>
					<small class="error"><form:errors path="number1" cssClass="errorMsg"/></small>
					<small class="error"><form:errors path="number3" cssClass="errorMsg"/></small>
				</td>
			</tr>
			<tr>
				<th>통신사</th>
				<td><form:select path="agency" name="agency" id="agency">
							<form:option value="">통신사선택</form:option>
							<form:option value="SKT">SKT</form:option>
							<form:option value="KT">KT</form:option>
							<form:option value="LGU+">LGU+</form:option>
							<form:option value="기타">기타</form:option>
					</form:select>
					<small><form:errors path="agency" cssClass="errorMsg"/></small>
				</td>
			</tr>
		</table>
		<br>
		<div style="width: 100%; text-align: right;">
			<input type="hidden" value="${userId}" name="userId">
			<span><input class="button" type="button" value=취소 onclick='history.back()'></span>
			<span><button class="button" onclick='submitForm()'>추가</button></span>
		</div>
	</form:form>
</div>
</body>
</html>