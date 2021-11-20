<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="post.PostDTO"%>
<%@ page import="category.CategoryDAO"%>
<%@ page import="category.CategoryDTO"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<%@ include file="layout/header.jsp"%>
	
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
	
<style>
.category span {
	font-family: "Hanna";
	font-size: 20px;
}
</style>
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
	int category = 1;
	if (request.getParameter("category") != null) {
	category = Integer.parseInt(request.getParameter("category"));
	}
	%>
	<%@ include file="layout/navigation.jsp"%>
	<%
	if (session.getAttribute("messageType") != null) {
		session.removeAttribute("messageType");
		session.removeAttribute("messageContent");
	} ;
	%>

	<div class="container">
		<div class="btn-group btn-group-justified" role="group"
			style="margin-bottom: 40px">
			<%
			CategoryDAO categoryDAO = new CategoryDAO();
			List<CategoryDTO> clist = categoryDAO.getList();
			for (int i = 0; i < clist.size(); i++) {
			%>
			<%
			String cname = clist.get(i).getCname();
			%>
			<div class="btn-group" role="group">
				<% if(category == i + 1) { %>
				<button type="button" class="btn btn-default category active" onclick="location.href='postpage.jsp?category=<%=i + 1%>'">
				<img src="image/<%=cname%>.png " alt="<%=i + 1%>" class="img-responsive"><span><%=cname%></span>
				</button>
				<%} else { %>
					<button type="button" class="btn btn-default category" onclick="location.href='postpage.jsp?category=<%=i + 1%>'">
					<img src="image/<%=cname%>.png " alt="<%=i + 1%>" class="img-responsive"><span><%=cname%></span>
				</button>
				<%} %>
			</div>
			<%
			}
			%>
		</div>
		<div class="row">
		
			<form method="post" action="writePostAction.jsp?category=<%=category%>">
				<table class="table table-striped"
					style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th style="background-color: #eeeeee; text-align: center; color: #000000;">글쓰기</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class="form-control" placeholder="글 제목" name="title"></td>
						</tr>
						<tr>
							<td> <textarea id="summernote" name="contents" style="all: none;" ></textarea> </td>
						</tr>
					</tbody>
				</table>
				<input type="submit" class="btn btn-primary pull-right" value="게시물 등록">
			</form>
		</div>
	</div>
</body>
<script>
$('#postpageActiveId').addClass("active");

$('#summernote').summernote({
	placeholder: '글 내용',
	tabsize: 2,
	height: 500,
	minHeight: 500,
	lang: "ko_KR",
});
</script>
</html>