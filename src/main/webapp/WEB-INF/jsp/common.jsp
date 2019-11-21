<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-3.1.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="/resources/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/resources/css/ui.jqgrid.css" />
<style type="text/css">
	table {border: 1px solid #444444;}
</style>
<script type="text/javascript" src="/resources/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script> 
<script type="text/javascript" src="/resources/js/grid.locale-kr.js"></script>
<script type="text/javascript" src="/resources/js/jquery.jqGrid.min.js"></script>

<script type="text/javascript">
function loadingViewOpen() {
	$('#loading-view').show();
}
function loadingViewClose() {
	$('#loading-view').hide();
}
function ajax(url, type, param, callBack, errCallBack) {
	loadingViewOpen();
	$.ajax({
		url : url,
		type : type,
		data : param,
		contentType : 'application/json; charset=UTF-8',
		dataType : 'json',
		success : function(result) {
			loadingViewClose();
			callBack(result);
		},
		error : function(xhr, status, error) {
			loadingViewClose();
			if(errCallBack)errCallBack();
			alert('일시적인 장애가 발생하였습니다. 잠시후 다시 시도해주세요.');
		}
	})
}
</script>