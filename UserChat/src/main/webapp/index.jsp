<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="post.IndexDAO"%>
<%@ page import="post.PostDTO"%>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.io.*" %>
<!DOCTYPE html>
<html>
  <%@ include file = "layout/header.jsp" %>
  <body>
  	<%
	    if (session.getAttribute("messageType")!= null) {
	      String Error = "오류 메세지";
	  	  String messageType = (String) session.getAttribute("messageType");
	  	  String msg = (String) session.getAttribute("messageContent");
	  	  if(messageType.equals(Error)){
		  	%>
		  	  <script>
		  		showErrorMessage('<%=msg%>');
		  	  </script>
		  	<% 
	  	  }else{
	  		%>
		  	  <script>
		  		showSuccessMessage('<%=msg%>');
		  	  </script>
		  	<% 
	  		  
	  	  }
   	}    
    %>
    <%@ include file = "layout/navigation.jsp" %>
    <%
		 if (session.getAttribute("messageType")!= null) { 
			 session.removeAttribute("messageType");
			 session.removeAttribute("messageContent");
		 }; 
	 %>
	 <% 
	 	PostDTO post = new IndexDAO().getIndexPost();
	 %>
	 <%@ include file = "layout/chatbox.jsp" %>
	 <div class="main-screen">
	 	<header style = "margin-top:5%;">
			<h2 style="text-align:center;">SHARE POST</h2>
			<h3>LET US KNOW THE BIG SALE EVENT!!!</h3>
		</header>
		<main>
			<div class="DiceContainer">
				<div class="DiceOutFrame">
				<div class="DiceInner1Frame">
					<div class="eraser" onclick="location.href='updateIndex.jsp'">
						<div style = "background-color : #E17B53; height : 70%;">
						</div>
						<div style = "background-color : #FFFFFF; height : 30%;">
						</div>
					</div>
					<div class="DiceInner2Frame">
					<div class="DiceInner3Frame" style="overflow-y: scroll;">
						<pre style="background-color : #255f38; border:none; ">
							<h4 style="color: white; font-family: 'Hanna'; font-size: 25px;" ><%= post.getContents() %><h4/>
						</pre>
						<div class="DiceArea">
						</div>
					</div>
					</div>
				</div>
				</div>
			</div>
		</main>
	 </div>
	<%
		 if (session.getAttribute("messageType")!= null) { 
			 session.removeAttribute("messageType");
			 session.removeAttribute("messageContent");
		 }; 
	 %>
  </body>
  <script>
	$('#mainActiveId').addClass("active")
</script>
</html>
