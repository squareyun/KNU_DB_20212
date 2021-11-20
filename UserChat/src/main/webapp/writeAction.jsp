<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO" %>
<%@ page import="profile.ProfileDAO" %>
<%@ page import="profile.ProfileDTO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="post" class="post.PostDTO" scope="page" />
<jsp:setProperty name="post" property="title" />
<jsp:setProperty name="post" property="contents" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<%
		String userEmail = null;
		if (session.getAttribute("Email") != null) {
			userEmail = (String) session.getAttribute("Email"); 
		}
		if (request.getParameter("category") == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('잘못된 접근입니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
		if(userEmail == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href='Login.jsp'");
			script.println("</script>");
		} else {
			if (post.getTitle() == null || post.getContents() == null) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('입력이 안 된 사항이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
			} else {
				PostDAO postDAO = new PostDAO();
				ProfileDTO creator = new ProfileDAO().getUserByEmail(userEmail);
				int category = Integer.parseInt(request.getParameter("category"));
				int result = postDAO.write(category, creator.getPRid(), post.getTitle(), post.getContents());
				if(result == -1 ) {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('글쓰기에 실패했습니다.')");
					script.println("history.back()");
					script.println("</script>");
				} else {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("location.href='postpage.jsp?category="+ category +"'");
					script.println("</script>");
				}
			}
		}
	%>

</body>
</html>