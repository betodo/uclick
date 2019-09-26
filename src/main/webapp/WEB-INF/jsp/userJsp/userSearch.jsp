<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat,java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 검색</title>
<link href="${pageContext.request.contextPath}/resources/css/list_table.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/layout.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet" >
</head>
<body>
<script type="text/javascript">
	//날짜 출력 소수점이하 문자열 자르기
	function regDate_print(regDate, index){
		var regDateTemp = regDate; //문자열 자르기 전 날짜
		//console.log("temp : "+regDateTemp);
		var newRegDate = regDateTemp.substr(0,regDateTemp.length-5);//문자열 자른 후 날짜
		//console.log("new : "+newRegDate);
		var td_regDate = document.getElementsByClassName('td_regDate')[index]//innerHTML을 사용하기 위해 태그 가져오기
		//console.log("td : "+td_regDate);
		td_regDate.innerHTML = newRegDate;//innerHTML로 출력
	
	}	
	
	/* //document.write를 사용하는 경우
		 function regDate_print(regDate){
			var printRegDate = regDate; //값을 받아와서
			//console.log(printRegDate);
			document.write(printRegDate.substr(0,printRegDate.length-2));//소수점을 자르고 출력
		}
	*/
</script>
<div class="container">
<h2>사용자 검색</h2>
<form method="get" action="/userJsp/userList">
	<span><input type="text" placeholder="이름으로 검색" name="searchName">
	<input type="submit" value="검색" class="button" ></span>
	<input type="hidden" value="search" name="key">
</form>
<br>
	<table class="list_table">
		<thead>
			<tr>
			<th>ID</th>
			<th>이름</th>
			<th>부서</th>
			<th>직위</th>
			<th>등록일</th>
			<th>수정/삭제</th>		
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty users}">
					<tr>
						<td colspan=4>
							<spring:message code="common.listEmpty"/>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
						<c:forEach items="${users}" var="u" varStatus="status">
						<form method="post"> 
							<tr>
								<!-- <td>${status.index}</td> --> 
								<td>${u.id}</td>
								<td><a href="../phoneJsp/phoneList_ofUser/${u.id}/${u.name}">${u.name}</a></td>
								<td>${u.dept}</td>
								<td>${u.jobPosition}</td>
								<td class="td_regDate">
									<script type="text/javascript">regDate_print('${u.regDate}','${status.index}');</script>
								</td>
								<td>
									<span><input class="button" type="submit" formaction="inputUser_forUpdate" value="수정"></span>
									<span><input class="button" type="submit" formaction="userDelete" value="삭제"></span>
								</td>
							</tr>
							<input type="hidden" name="userId" value="${u.id}">
						</form>
						</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<br>
	<div style="width: 100%; text-align: right;">
		<form method="get" action="/userJsp/inputUser_forInsert"><input class="button" type="submit" value="사용자 추가"></form>
	</div>
	<span><input class="button" type="button" value="사용자 목록" onclick='location.href="/userJsp/userList"'></span>
	<span><input class="button" type="button" value="전화기 목록" onclick='location.href="/phoneJsp/phoneList"'></span>
	<!-- <span><form method="get" action="/phoneJsp/phoneList"><input class="button" type="submit" value="전화기 목록"></form></span> -->
</div>
</body>
</html>