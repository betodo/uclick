<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>특정 사용자의 전화기</title>
<link href="${pageContext.request.contextPath}/resources/css/list_table.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/layout.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet" >
</head>
<body>
<div class="container">
<h2>${userName}의 전화기 목록</h2>
	<table class="list_table">
		<thead>
			<tr>
				<th>아이디</th>
				<th>번호</th>
				<th>통신사</th>
				<th>수정/삭제</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty phones}">
					<tr>
						<td colspan=4><spring:message code="phone.listEmpty" /></td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${phones}" var="p">
					<form method="post"> 
						<tr>
							<td>${p.id}</td>
							<td>${p.number}</td>
							<td>${p.agency}</td>
							<td>
								<span><input class="button" type="submit" formaction="/phoneJsp/inputPhone_forUpdate" value="수정"></span>
								<span><input class="button" type="submit" formaction="/phoneJsp/phoneDelete" value="삭제"></span>
							</td>
						</tr>
						<input type="hidden" name="phoneId" value="${p.id}">
						<input type="hidden" name="userId" value="${userId}">
					</form>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<br>
	<div style="width: 100%; text-align: right;">
		<form method="get" action="/phoneJsp/inputPhone_forInsert/${userId}"><input type="submit" value="전화기 추가" class="button" ></form>
	</div>
	<span><input class="button"  type="button" value="사용자 목록" onclick='location.href="/userJsp/userList"'></span>
	<span><input class="button"  type="button" value="전화기 목록" onclick='location.href="/phoneJsp/phoneList"'></span>
</div>
</body>
</html>