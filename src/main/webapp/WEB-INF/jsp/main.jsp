<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>도서 조회</title>
<style type="text/css">
.popupLayer {position:absolute; left:0; top:0; width:100%; height:100%; z-index:100; -ms-filter: alpha(opacity=50); filter: alpha(opacity=50); opacity:0.3; -moz-opacity:0.3;}
.popup_box {position:absolute; left:50%; top:50%; z-index:102; background:#fff;}
</style>
</head>
<%@include file="/WEB-INF/jsp/common.jsp" %>
<script type="text/javascript">
var currentPage = 1;
$(document).ready(function() {
	$('#signUp').click(function() {
		var w = 500;
		var h = 300;
		var left = $(window).scrollLeft() + ($(window).width() - w) / 2;
		var top = $(window).scrollTop() + ($(window).height() - h) / 2;
		$('.popup_box').css({
			'width' : w,
			'height' : h,
			'top' : top,
			'left' : left
		});
		$('#all_popup').show();
		$('#layerIframe').attr('src', '/singUpPopup');
	});
	
	$("#searchBtn").click(function() {
		if($("#keyword").val() == null || $("#keyword").val() == "") {
			alert('검색어를 입력해주세요.');
			return;
		}
		
		$.ajax({
			url : "http://localhost:8080/book/searchHist?query="+encodeURI($("#keyword").val()),
			type : "GET",
			contentType : 'application/json; charset=UTF-8',
			dataType : 'json',
			success : function(result) {
				loadingViewClose();
				mostSearchList();
			},
			error : function(xhr, status, error) {
				loadingViewClose();
			}
		});
		
		currentPage = 1;
		$("#list").setGridParam({
			datatype: 'json',
			postData : {
				currentPage : currentPage,
				query:$("#keyword").val()
			}
		}).trigger("reloadGrid");
		
	});
});
function mostSearchList() {
	var url = "http://localhost:8080/book/mostSearchList";
	var param = {};
	ajax(url, "post", JSON.stringify(param), function(result) {
		$("#mostList").html(result.data);
	});
}
function searchList() {
	$("#searchBtn").trigger('click');
}

function closePop() {
	$('#all_popup').hide();
	$('#layerIframe').attr('src', '');
}
function enterkey() {
	if (window.event.keyCode == 13) {
        $("#searchBtn").trigger('click');
   }
}
function mySearchHist() {
	var w = 350;
	var h = 500;
	var left = $(window).scrollLeft() + ($(window).width() - w) / 2;
	var top = $(window).scrollTop() + ($(window).height() - h) / 2;
	$('.popup_box').css({
		'width' : w,
		'height' : h,
		'top' : top,
		'left' : left
	});
	$('#all_popup').show();
	$('#layerIframe').attr('src', '/mySearchHist');
}
</script>
<%@include file="/WEB-INF/jsp/jqGrid.jsp" %>
<body>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
	<div style="margin:10px 10px 0 10px">
		<form action="${pageContext.request.contextPath}/logout" method="POST">
			<input type="submit" value="logout" style="float:left;margin-right:10px;"/>
		</form>
		"${pageContext.request.userPrincipal.name}"님 환영합니다.
		
		<button onClick="mySearchHist()">나의 검색 히스토리</button>
	</div>
	</c:if>
	<c:if test="${pageContext.request.userPrincipal.name == null}">
		<button id="signUp">회원가입</button>
		<a href="<c:url value="/login" />">로그인</a>
	</c:if>
	
	<br/>
	<div style="margin: 30px 0 0 40px;">
		<span>도서검색 : </span>
		<input id="keyword" maxlength="10" style="width:300px" onkeyup="enterkey();"/>
		<button id="searchBtn">검색</button>
	</div>
	
	<br/>
	<div id="NoData"></div>
	<table id="list"><tr><td></td></tr></table> 
	<div id="pager"></div>
	<div id="paginate"></div> 
	<br/>
	<br/>
	<table style="width:300px;">
		<caption>인기 검색어</caption>
		<colgroup>
			<col style="width:50%;" /> 
			<col style="width:50%;" /> 
		</colgroup>
		<tbody id="mostList">
			
		</tbody>
	</table>
	<%@include file="/WEB-INF/jsp/popup.jsp" %>
</body>
</html>