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
<style>
a, a:hover {
	color: #000000;
	text-decoration: none;
}

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
	int pageNum = 1;
	if (request.getParameter("pageNum") != null) {
	pageNum = Integer.parseInt(request.getParameter("pageNum"));
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
				<img src="image/<%=cname%>.png" alt="<%=i + 1%>" class="img-responsive"><span><%=cname%></span>
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
					List<PostDTO> list = postDAO.getPostListInCategory(category, pageNum);
					for (int i = 0; i < list.size(); i++) {
					%>
					<tr>
						<td><a href="viewpost.jsp?category=<%=category%>&pid=<%=list.get(i).getPid()%>"><%=list.get(i).getTitle()%></a></td>
						<td><%=list.get(i).getNickname()%></td>
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
			<a
				href="postpage.jsp?category=<%=category%>&pageNum=<%=pageNum - 1%>"
				class="btn btn-success btn-arrow-left">이전</a>
			<%
			}
			%>
			<%
			if (postDAO.hasPageNext) {
			%>
			<a
				href="postpage.jsp?category=<%=category%>&pageNum=<%=pageNum + 1%>"
				class="btn btn-success btn-arrow-">다음</a>
			<%
			}
			%>
			<a href="writepost.jsp?category=<%=category%>"
				class="btn btn-primary pull-right">글쓰기</a>
		</div>
	</div>
</body>
<script>
$('#postpageActiveId').addClass("active")
</script>
</html>