<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html>
  <%@ include file = "views/header.jsp" %>

  <body>
    <%@ include file = "views/navigation.jsp" %>
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
    <section class="center">
        <div class="header">
            <h1>
                 <span class = "service-name">로그인 </span>
            </h1>
        </div>
        <div class="form-wrap">
            <div class="user-info">
                <form class="user-info-form" method="post" action="./ProfileLoginServlet">
                    <input id = "Email" class = "id" name="Email" type="text" placeholder="Email" />
                    <input id = "Password" class = "pwd" name="Password" type="password" placeholder="Password"/>
                    <input class="login-Btn"type = "submit" value="접속하기">
                </form>
            </div>
        </div>
    </section>
    <%
		 if (session.getAttribute("messageType")!= null) { 
		 	session.removeAttribute("messageType");
		 	session.removeAttribute("messageContent");
		 }; 
	 %> 
  </body>
</html>
