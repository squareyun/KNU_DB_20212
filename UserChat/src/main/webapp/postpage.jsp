<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="post.PostDTO"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<%@ include file="layout/header.jsp"%>
<style>
	a, a:hover{
	color: #000000;
	text-decoration: none;
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
	int pageNum = 1;
	if (request.getParameter("pageNum") != null) {
	pageNum = Integer.parseInt(request.getParameter("pageNum"));
	}
	%>
	<%@ include file="layout/navigation.jsp"%>
	<%
	if (session.getAttribute("messageType") != null) {
		session.removeAttribute("messageType");
		session.removeAttribute("messageContent");
	}
	;
	%>
	<div class="container">
		<div class="row">
			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th
							style="background-color: #eeeeee; text-align: center; color: #000000; width: 60%">제목</th>
						<th
							style="background-color: #eeeeee; text-align: center; color: #000000; width: 20%">작성자</th>
						<th
							style="background-color: #eeeeee; text-align: center; color: #000000; width: 20%">작성일</th>
					</tr>
				</thead>
				<tbody>
					<%
					PostDAO postDAO = new PostDAO();
					List<PostDTO> list = postDAO.getPostListInCategory(1, pageNum);
					for (int i = 0; i < list.size(); i++) {
					%>
					<tr>
						<td><a href="view.jsp?postId=<%=list.get(i).getPid()%>"><%=list.get(i).getTitle()%></a></td>
						<td><%=list.get(i).getCreator_id()%></td>
						<td><%=list.get(i).getCreate_date().substring(0, 11) + list.get(i).getCreate_date().substring(11, 13) + ":"
		+ list.get(i).getCreate_date().substring(14, 16)%></td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
			<%
			if (pageNum != 1) {
			%>
			<a href="postpage.jsp?pageNum=<%=pageNum - 1%>"
				class="btn btn-success btn-arrow-left">이전</a>
			<%
			}
			%>
			<%
			if (postDAO.hasPageNext) {
			%>
			<a href="postpage.jsp?pageNum=<%=pageNum + 1%>"
				class="btn btn-success btn-arrow-">다음</a>
			<%
			}
			%>
			<a href="write.jsp" class="btn btn-primary pull-right">글쓰기</a>
		</div>
	</div>
</body>
</html>