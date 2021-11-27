<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="post.IndexDAO"%>
<%@ page import="post.PostDTO"%>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.io.*" %>
<!DOCTYPE html>
<html>
  <%@ include file="layout/header.jsp"%>
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
  <body >
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
	 
	 <div class="main-screen" >
	 	<header style = "margin-top:5%;">
			<h2 style="text-align:center;">SHARE POST</h2>
			<h3>LET US KNOW THE BIG SALE EVENT!!!</h3>
		</header>
		<% if(post != null) { %>
			<main >
				<form method="post" style="width: 60%; height:100%;">
					<table class="table table-striped"
						style="text-align: center; border: 1px solid #dddddd;width: 100%; height:100%;">
						<thead>
							<tr>
								<th style="background-color: #eeeeee; text-align: center; color: #000000;">공유 게시판 수정</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td> <textarea id="contents" class="contentes" name="contents" style="width: 100%; height:100%;" ><%=post.getContents()%></textarea> </td>
							</tr>
						</tbody>
					</table>
					<input type="button" class="btn btn-primary pull-right" value="게시물 수정" onclick="formSubmitFunction()">
				</form>
			</main>
		<% }else{ %>
			<script>location.href = "index.jsp"; </script>
		<%} %>
	 </div>
	 <footer style = "margin-top : 5%; color : tomato; font-weight : bold; text-align : center;">
			<h4>
			 * 이 칠판은 공용 게시판입니다. 누군가가 수정 중 일때는 수정할 수 없습니다. *
			</h4>
	</footer>
  </body>
  <script>
	
	function formSubmitFunction() {
        var Contents = $("#contents").val();
        // ajax 비동기 통신
        $.ajax({
          type: "POST",
          //아래의 url로 보내줌
          url: "./IndexUpdateServlet",
          data: { Contents: Contents },
          success: function (result) {
			  console.log(result);
            if (result == 1) {
              Swal.fire({
                  icon: 'success',
                  title: `게시판 수정에 성공하였습니다.`,
                  showConfirmButton: false,
                  timer: 1500
                }).then(()=>{ location.href = "index.jsp";});
            } else {
              Swal.fire({
                  icon: 'error',
                  title: `다른 유저가 현재 수정중입니다.`,
                  showConfirmButton: true
                }).then(()=>{ location.href = "index.jsp";});
            }
          }
        });
      }

</script>
</html>
