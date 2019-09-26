<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전체 전화기 목록</title>
<link href="${pageContext.request.contextPath}/resources/css/list_table.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/layout.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/pagination.css" rel="stylesheet" >
</head>
<body>
<div class="container">
<h2>전체 전화기 목록</h2>
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
				<c:when test="${empty phones.content}">
					<tr>
						<td colspan=3><spring:message code="phone.listEmpty" /></td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${phones.content}" var="p">
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
	<div align="center">
	<!-- 자바스크립트로 페이징 구현 -->
	<script type="text/javascript">
		//페이징처리 변수선언
		var toPage = 5;//한 range에서 출력할 페이지링크 수
		var startPage=parseInt(${phones.number}/toPage)*toPage; // 각 range당 첫 페이지링크
		var endPage = startPage + toPage;//한 range에서 출력할  마지막 페이지링크 
		var size = 5;//한 page에서 조회할 row 수
		var url ="/phoneJsp/phoneList";//페이지네이션 처리할 url주소
		var nextRange = true;//다음 range가 있는지 유무
		
		//마지막 range 페이지링크 출력 처리
		if(endPage >= ${phones.totalPages}) { 
			endPage = ${phones.totalPages}; 
			nextRange = false; //마지막 range에선 다음 range가 없다.
		}
		
		//출력
		document.write("<ul id ='pagination'>");
		document.write("<li><a href="+url+"?nowPage="+0+"&size="+size+">처음</a>"); //처음클릭 -> 최초페이지=0
		//첫 range가 0이 아닐때 이전(<)링크 출력
		if(startPage != 0){		
			//이전(<)클릭->이전 range의 마지막링크값이 보내지고 parseInt(${users.number}/5)*5 계산에 의해 이전 range의 첫 페이지링크가 됨
			document.write("<li><a href="+url+"?nowPage="+(startPage-1)+"&size="+size+">&lt</a>");  
		}
		
		for(var i=startPage; i<endPage; i++){ //페이지링크 출력 클릭시 현재 페이지 번호를 보냄
			document.write("<li><a href="+url+"?nowPage="+i+"&size="+size+">"+(i+1)+"</a>");
		}
		
		if(nextRange){//다음 range가 있을때 다음(>)링크 출력
			//클릭시 다음range의 첫 페이지링크가 보내진다
			document.write("<li><a href="+url+"?nowPage="+endPage+"&size="+size+">&gt</a>");
		}
		//끝 클릭 -> 마지막페이지 = ${users.totalPages}-1 (0부터 시작하기에 -1)
		document.write("<li><a href="+url+"?nowPage="+(${phones.totalPages}-1)+"&size="+size+">끝</a>");
		document.write("</ul>");
	</script>
	</div>
	<br><br>
	<span><input class="button" type="button" value="사용자 목록" onclick='location.href="/userJsp/userList"'></span>
</div>
</body>
</html>