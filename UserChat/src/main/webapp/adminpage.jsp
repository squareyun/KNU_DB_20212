<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="post.PostDTO"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<%@ include file="layout/header.jsp"%>
<body>
	<%
	if (session.getAttribute("messageType") != null) {
		String Error = "오류 메세지";
		String messageType = (String) session.getAttribute("messageType");
		String msg = (String) session.getAttribute("messageContent");
		if (messageType.equals(Error)) {
	%>
	<script>showErrorMessage('<%=msg%>');</script>
	<%
	} else {
	%>
	<script>showSuccessMessage('<%=msg%>
		');
	</script>
	<%
	}
	}
	%>
	<%@ include file="layout/navigation.jsp"%>
	<%
	if (session.getAttribute("messageType") != null) {
		session.removeAttribute("messageType");
		session.removeAttribute("messageContent");
	}
	%>
	<div class="container">
		<h1>유저 검색 기능</h1>
		<table class="table table-bordered table-hover"
			style="text-align: center; border: 1px solid #dddddd">
			<tbody>
				<tr>
					<td style="width: 110px"><h5>닉네임</h5></td>
					<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
						id="nickname" type="text" name="nickname" maxlength="20" size="20" style="width: 100%;"/></td>
					<td style="width: 110px"><h5>이름</h5></td>
					<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
						id="nickname" type="text" name="nickname" maxlength="20" size="20" style="width: 100%;"/></td>
				</tr>
				<tr>
					<td style="width: 110px"><h5>COUNTRY</h5></td>
					<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
						id="nickname" type="text" name="nickname" maxlength="20" size="20" style="width: 100%;"/></td>
					<td style="width: 110px"><h5>STATE</h5></td>
					<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
						id="nickname" type="text" name="nickname" maxlength="20" size="20" style="width: 100%;"/></td>
				</tr>
				<tr>
					<td style="width: 110px"><h5></h5></td>
					<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
						id="nickname" type="text" name="nickname" maxlength="20" size="20" style="width: 100%;"/></td>
					<td style="width: 110px"><h5>STREET</h5></td>
					<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
						id="nickname" type="text" name="nickname" maxlength="20" size="20" style="width: 100%;"/></td>
				</tr>
				<tr>
					<td style="width: 110px"><h5>COUNTRY</h5></td>
					<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
						id="nickname" type="text" name="nickname" maxlength="20" size="20" style="width: 100%;"/></td>
					<td style="width: 110px"><h5>STATE</h5></td>
					<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
						id="nickname" type="text" name="nickname" maxlength="20" size="20" style="width: 100%;"/></td>
				</tr>

				<tr>
					<td style="text-align: center;" colspan="4"><input
						class="btn btn-primary " type="reset" onclick="" value="검색하기" /></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
<script>
	$('#adminActiveId').addClass("active")
</script>
</html>