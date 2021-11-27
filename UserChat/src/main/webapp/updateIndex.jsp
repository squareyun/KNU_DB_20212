<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
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
	 <div class="main-screen" >
	 	<header style = "margin-top:5%;">
			<h2 style="text-align:center;">SHARE POST</h2>
			<h3>LET US KNOW THE BIG SALE EVENT!!!</h3>
		</header>
		<main >
			<form method="post" action="">
				<table class="table table-striped"
					style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th style="background-color: #eeeeee; text-align: center; color: #000000;">공유 게시판 수정</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td> <textarea class="summernote" name="contents" style="all: none;" ></textarea> </td>
						</tr>
					</tbody>
				</table>
				<input type="submit" class="btn btn-primary pull-right" value="게시물 등록">
			</form>
		</main>
	 </div>
	 <footer style = "margin-top : 5%; color : tomato; font-weight : bold; text-align : center;">
			<h4>
			 * 이 칠판은 공용 게시판입니다. 누군가가 수정 중 일때는 수정할 수 없습니다. *
			</h4>
		</footer>
  </body>
  <script>
	$('.summernote').summernote({
		placeholder: '글 내용',
		tabsize: 2,
		height: 400,
		minHeight: 500,
		lang: "ko_KR",
	});
</script>
</html>
