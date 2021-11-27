<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="post.PostDTO"%>
<%@ page import="category.CategoryDAO"%>
<%@ page import="category.CategoryDTO"%>
<%@ page import="profile.ProfileDAO"%>
<%@ page import="profile.ProfileDTO"%>
<%@ page import="java.util.List"%>
<%@page import="java.io.PrintWriter"%>
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
	String userEmail = null;
	if (session.getAttribute("Email") != null) {
		userEmail = (String) session.getAttribute("Email"); 
	}
	if (userEmail == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 하세요')");
		script.println("location.href = 'Login.jsp");
		script.println("</script>");
	}
	int pid = -1;
	int category = -1;
	
	if (request.getParameter("pid") != null) {
		pid = Integer.parseInt(request.getParameter("pid"));
	}
	if (request.getParameter("category") != null) {
		category = Integer.parseInt(request.getParameter("category"));
	}
	if (pid == -1 || category == -1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 글입니다.')");
		script.println("history.back()");
		script.println("</script>");
	}
	PostDTO postDTO = new PostDAO().getPost(pid);
	if(!userEmail.equals(postDTO.getEmail())) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('권한이 없습니다.')");
		script.println("history.back()");
		script.println("</script>");
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
		
			<form method="post" action="updatePostAction.jsp?category=<%=category%>&pid=<%=pid%>">
				<table class="table table-striped"
					style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th style="background-color: #eeeeee; text-align: center; color: #000000;">글수정</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td> <textarea id="summernote" name="contents" style="all: none;" ><%=postDTO.getContents()%></textarea> </td>
						</tr>
					</tbody>
				</table>
				<input type="submit" class="btn btn-primary pull-right" value="게시물 수정">
			</form>
		</div>
	</div>
</body>
<script>
$('#postpageActiveId').addClass("active");

$('#summernote').summernote({
	tabsize: 2,
	height: 500,
	minHeight: 500,
	lang: "ko_KR",
});
</script>
</html>