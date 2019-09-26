<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전화기 검색</title>
<link href="${pageContext.request.contextPath}/resources/css/list_table.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/layout.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet" >
</head>
<body>
<div class="container">
<h2>전화기</h2>
<form method="get" action="/phoneJsp/phoneList">
	<span><input type="text" placeholder="000-0000-0000로 검색" name="searchNumber">
	<input type="submit" value="검색" class="button"></span>
	<input type="hidden" value="search" name="key">
</form>
<br>
	<table class="list_table">
		<thead>
			<tr>
				<th>전화기 ID</th>
				<th>번호</th>
				<th>통신사</th>
				<th>사용자 ID</th>
				<th>사용자 이름</th>
			<tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty phones}">
					<tr>
						<td colspan=3><spring:message code="phone.listEmpty" /></td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${phones}" var="p">
						<tr>
							<td>${p.id}</td>
							<td><a href="phoneList_ofUser/${p.user.id}/${p.user.name}">${p.number}</a></td>
							<td>${p.agency}</td>
							<td>${p.user.id}</td>
							<td>${p.user.name}</td>
							<!--<td><input type="submit" formaction="oneUserForm" value="수정"></td> -->
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<br>
	<span><input class="button" type="button" value="사용자 목록" onclick='location.href="/userJsp/userList"'></span>
	<span><input class="button" type="button" value="전화기 목록" onclick='location.href="/phoneJsp/phoneList"'></span>
</div>
</body>
</html>