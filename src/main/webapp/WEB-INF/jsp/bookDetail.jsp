<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
</head>
<%@include file="/WEB-INF/jsp/common.jsp" %>
<script type="text/javascript">
$( document ).ready(function() {
});
function closePopup() {
	parent.closePop();
}
</script>
<body>
	<img alt="도서썸네일" src="${thumbnail}" height="250" width="200" style="float:left;margin:10px 10px 0 10px;">
	<table style="width:600px; height:500px;">
		<caption>도서정보</caption>
		<colgroup>
			<col style="width:12%;" /> 
			<col style="width:38%;" /> 
			<col style="width:12%;" /> 
			<col style="width:38%;" />
		</colgroup>
		<tbody>
			<tr>
				<th>제목</th>
				<td>"${title}"</td>
				<th>ISBN</th>
				<td>"${isbn}"</td>
			</tr>
			<tr>
				<th>저자</th>
				<td>"${authors}"</td>
				<th>출판사</th>
				<td>"${publisher}"</td>
			</tr>
			<tr>
				<th>출판일</th>
				<td>"${datetime}"</td>
				<th>정가</th>
				<td>"${price}"</td>
			</tr>
			<tr>
				<th>판매가</th>
				<td>"${sale_price}"</td>
			</tr>
			<tr>
				<th>소개</th>
				<td colspan="3">"${contents}"</td>
			</tr>
		</tbody>
	</table>
	<button onClick="closePopup()">닫기</button>
</body>
</html>