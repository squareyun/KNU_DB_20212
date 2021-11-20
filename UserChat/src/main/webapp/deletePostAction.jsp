<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO" %>
<%@ page import="post.PostDTO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
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
		} else {
			PostDAO postDAO = new PostDAO();
			int result = postDAO.delete(Integer.parseInt(request.getParameter("pid").toString()));
			if(result == -1 ) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('글삭제에 실패했습니다.')");
				script.println("history.back()");
				script.println("</script>");
			} else {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("location.href='postpage.jsp?category="+ category + "'");
				script.println("</script>");
			}
		}
	%>

</body>
</html>