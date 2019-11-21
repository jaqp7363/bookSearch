<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
</head>
<%@include file="/WEB-INF/jsp/common.jsp" %>
<script type="text/javascript">
	var duplicateCheck = true;
$( document ).ready(function() {
	$("#signUpBtn").click(function() {
		var loginId = $("#loginId").val();
		var password = $("#password").val();
		var email = $("#email").val();
		
		if(loginId == null || loginId =="") {
			alert("ID를 입력해주세요");
			return;
		};
		if(password == null || password =="") {
			alert("PassWord를 입력해주세요");
			return;
		};
		if(email == null || email =="") {
			alert("email를 입력해주세요");
			return;
		};
		
		if(duplicateCheck) {
			alert("id중복체크 후 가입해주세요.");
			return;
		}
		
		var url = "http://localhost:8080/auth/signup";
		var param = {"loginId":loginId, "password":password, "email":email};
		ajax(url, "post", JSON.stringify(param), function(result) {
			closePopup();
			alert('가입을 환영합니다. 로그인을 진행해주세요.');
		});
	});
	
	$("#loginId").change(function() {
		duplicateCheck = true;
	});

});
function closePopup() {
	parent.closePop();
}
function duplicateSuccess(result) {
	console.log(result);
	if(result) {
		alert('이미 사용중인 ID입니다.');
		return;
	}
	console.log("duplicateCheck:"+duplicateCheck);
	duplicateCheck = result;
	alert('사용가능한 ID입니다.');
}
function fn_duplicateCheck() {
	var loginId = $("#loginId").val();
	if(loginId == null || loginId == "") {
		alert("ID를 입력해주세요");
		return;
	}
	var url = "http://localhost:8080/auth/existsByLoginId";
	var param = {"loginId":loginId};
	ajax(url, "post", JSON.stringify(param), duplicateSuccess);
}
</script>
<body>
	<div>
		<table>
			<caption>회원가입</caption>
				<colgroup>
					<col style="width:12%;" /> 
					<col style="width:38%;" /> 
					<col style="width:12%;" /> 
					<col style="width:38%;" />
				</colgroup>
				<tbody>
					<tr>
						<th>ID</th>
						<td>
							<input id="loginId" style="width:47%">
							<button onClick="fn_duplicateCheck()">중복체크</button>
						</td>
						<th>PW</th>
						<td>
							<input id="password"/>
						</td>
					</tr>
					<tr>
						<th>emial</th>
						<td colspan="3"><input id="email"/></td>
					</tr>
		</table>
	</div>
	<br/>
	<button id="signUpBtn">가입</button>
	<button onClick="closePopup()">닫기</button>
</body>
</html>