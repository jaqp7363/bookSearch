<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
</head>
<%@include file="/WEB-INF/jsp/common.jsp" %>
<style type="text/css">
</style>
<script type="text/javascript">
$( document ).ready(function() {
	var url = "http://localhost:8080/book/mySearchHist";
	var param = {};
	ajax(url, "post", JSON.stringify(param), function(result) {
		$("#hist").html(result.data);
	});
});
function closePopup() {
	parent.closePop();
}
</script>
<body>
	<div style="height:400px;">
		<table style="width:300px;">
			<caption>나의 검색 히스토리</caption>
			<colgroup>
				<col style="width:50%;" /> 
				<col style="width:50%;" /> 
			</colgroup>
			<tbody id="hist">
				
			</tbody>
		</table>
	</div>
	<button onClick="closePopup()">닫기</button>
</body>
</html>