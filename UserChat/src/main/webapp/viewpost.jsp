<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="post.PostDTO"%>
<%@ page import="category.CategoryDAO"%>
<%@ page import="category.CategoryDTO"%>
<%@ page import="profile.ProfileDAO"%>
<%@ page import="profile.ProfileDTO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<%@ include file="layout/header.jsp"%>
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
	%>
	<%@ include file="layout/navigation.jsp"%>
	<%
	if (session.getAttribute("messageType") != null) {
		session.removeAttribute("messageType");
		session.removeAttribute("messageContent");
	}
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
		<% PostDTO postDTO = new PostDAO().getPost(pid); %>
		<div class="row">
			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3" style="background-color: #eeeeee; text-align: center; color: #000000;">게시글 정보</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 20%;">글 제목</td>
						<td colspan="2">
						<%=postDTO.getTitle().replaceAll(" ", "&nbsp;").replaceAll("<", "%lt;").replaceAll(">", "%gt;").replaceAll("\n", "<br>") %>
						</td>
					</tr>
					<tr>
						<td>작성자</td>
						<td colspan="2"><%=postDTO.getNickname() %></td>
					</tr>
					<tr>
						<td>작성일자</td>
						<td colspan="2"><%=postDTO.getCreate_date().substring(0, 11) + postDTO.getCreate_date().substring(11, 13) + ":" + postDTO.getCreate_date().substring(14, 16)%></td>
					</tr>
					<tr>
						<td>내용</td>
						<td colspan="2" style="min-height: 200px; text-align: left;">
						<%=postDTO.getContents().replaceAll(" ", "&nbsp;").replaceAll("<", "%lt;").replaceAll(">", "%gt;").replaceAll("\n", "<br>") %>
						</td>
					</tr>
				</tbody>
			</table>
			<a href="postpage.jsp?category=<%=category%>" class="btn btn-primary">목록</a>
			<%
				if(session.getAttribute("Email").toString().equals(postDTO.getEmail())) {
			%>
				<a href="update.jsp?category=<%=category %>&pid=<%=pid %>" class="btn btn-primary">수정</a>
				<a href="deleteAction.jsp?pid=<%=pid %>" class="btn btn-primary">삭제</a>
			<%
				}
			%>
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