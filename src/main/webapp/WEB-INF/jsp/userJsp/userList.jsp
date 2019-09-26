<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 목록</title>
<link href="${pageContext.request.contextPath}/resources/css/list_table.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/layout.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/pagination.css" rel="stylesheet" >
</head>
<body>
<div class="container">
<h2>사용자 목록</h2>
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
				<c:when test="${empty users.content}">
					<tr>
						<td colspan=4>
							<spring:message code="common.listEmpty"/>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
						<c:forEach items="${users.content}" var="u" varStatus="status">
						<form method="post"> 
							<tr>
								<td>${u.id}</td>
								<td><a href="../phoneJsp/phoneList_ofUser/${u.id}/${u.name}">${u.name}</a></td>
								<td>${u.dept}</td>
								<td>${u.jobPosition}</td>
								<!-- 등록 날짜 출력시 '분'까지만 초 단위 제거 -->
								<td><fmt:formatDate value="${u.regDate}" pattern="YYYY.MM.dd HH:mm" /></td>
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
	<div align="center">
	
	<!-- 자바스크립트로 페이징 구현 -->
	<script type="text/javascript">
		//페이징처리 변수선언//
		var toPage = 3;//페이지링크 출력 범위 한 단위를 range라 했을 때 한 range에서 출력할 페이지링크 수
		var startPage=parseInt(${users.number}/toPage)*toPage; // 각 range당 첫 페이지링크
		var endPage = startPage + toPage;//한 range에서 출력할  마지막 페이지링크 
		var size = 5;//한 page에서 조회할 row 수
		var url ="/userJsp/userList";//페이지네이션 처리할 url주소
		var nextRange = true;//다음 range가 있는지 유무
		
		//마지막 range 페이지링크 출력 처리
		if(endPage >= ${users.totalPages}) { 
			endPage = ${users.totalPages}; 
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
		document.write("<li><a href="+url+"?nowPage="+(${users.totalPages}-1)+"&size="+size+">끝</a>");
		document.write("</ul>");
	</script>
	
	</div>
	<br>
	<div style="width: 100%; text-align: right;">
		<form method="get" action="/userJsp/inputUser_forInsert" id="fm"><input class="button" type="submit" value="사용자 추가"></form>
	</div>
	<form method="get" action="/phoneJsp/phoneList"><input class="button" type="submit" value="전화기 목록"></form>
</div>
</body>
</html>