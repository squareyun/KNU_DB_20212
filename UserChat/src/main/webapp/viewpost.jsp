<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="post.PostDTO"%>
<%@ page import="category.CategoryDAO"%>
<%@ page import="category.CategoryDTO"%>
<%@ page import="reply.ReplyDAO"%>
<%@ page import="reply.ReplyDTO"%>
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
table th {
	font-family: "Hanna";
	font-size: 25px;
	font-weight: normal;
}
.ptitle {
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
						<td style="width: 20%;" class="ptitle">글 제목</td>
						<td colspan="2">
						<%=postDTO.getTitle().replaceAll(" ", "&nbsp;").replaceAll("<", "%lt;").replaceAll(">", "%gt;").replaceAll("\n", "<br>") %>
						</td>
					</tr>
					<tr>
						<td style="border-right: 2px #000000;" class="ptitle">작성자</td>
						<td colspan="2"><%=postDTO.getNickname() %></td>
					</tr>
					<tr>
						<td class="ptitle">작성일자</td>
						<td colspan="2"><%=postDTO.getCreate_date().substring(0, 11) + postDTO.getCreate_date().substring(11, 13) + ":" + postDTO.getCreate_date().substring(14, 16)%></td>
					</tr>
					<tr>
						<td class="ptitle">내용</td>
						<td colspan="2" style="height: 200px; text-align: left;">
						<%=postDTO.getContents() %>
						</td>
					</tr>
				</tbody>
			</table>
			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="4"
							style="background-color: #eeeeee; text-align: center; color: #000000;">댓글
							정보</th>
					</tr>
				</thead>
				<tbody>
					<%
				ReplyDAO replyDAO = new ReplyDAO();
				List<ReplyDTO> rlist = replyDAO.getReplyListInPost(pid);
				%>
					<% if(rlist.size() != 0) { %>
					<% for (int i=0; i<rlist.size(); i++) { %>
					<tr>
						<td style="width: 20%;" class="ptitle"><%=i + 1%></td>
						<td style="text-align: left"><%=rlist.get(i).getContents()%></td>
						<td style="width: 150px;"><%=rlist.get(i).getCreate_date().substring(0, 11) + rlist.get(i).getCreate_date().substring(11, 13) + ":"
								+ rlist.get(i).getCreate_date().substring(14, 16)%></td>
						<% if(session.getAttribute("Email").toString().equals(rlist.get(i).getEmail())) { %>
						<td style="width: 70px;">
						<div style="display: flex;">
							<button style="all: unset; margin-right: 10px"
								onclick=""
								type="button">
							<img src="./image/write.png " alt="수정" width="30px" height="30px">
							</button>
							<button style="all: unset;"
								onclick="if(confirm('댓글을 삭제하시겠습니까?')) deleteReply('<%=rlist.get(i).getRid()%>')"
								type="button">
							<img src="./image/delete.png " alt="삭제" width="25px" height="25px">
							</button>
						</div>
						</td>
						<%
						}
						%>
					</tr>
					
					<%
					}
					}
					%>
					<tr>
						<td colspan="4">
							<div style="display: flex;">
								<input type="text" class="form-control" placeholder="댓글을 입력하세요." id="reply">
								<button
								style="margin-left: 10px;"
								class="btn btn-success"
								onclick="writeReply();"
								type="button">
								등록
								</button>
							</div>
						</td>
					</tr>

				</tbody>
			</table>
			<a href="postpage.jsp?category=<%=category%>" class="btn btn-primary ">목록</a>
			<%
				if(session.getAttribute("Email").toString().equals(postDTO.getEmail())) {
			%>
				<a href="updatepost.jsp?category=<%=category %>&pid=<%=pid %>" class="btn btn-primary">수정</a>
				<a
					onclick="return confirm('게시글을 삭제하시겠습니까?')"
					href="deletePostAction.jsp?category=<%=category %>&pid=<%=pid %>"
					class="btn btn-primary">삭제
				</a>
			<%
				}
			%>
		</div>
	</div>
</body>
<script>
$('#postpageActiveId').addClass("active");

function deleteReply(rids) {
	$.ajax({
	      type: "POST",
	      url: "./DeleteReplyServlet",
	      data: { rid : rids },
	      success: function (result) {
	        if (result == 1) {
	          Swal.fire({
	              icon: 'success',
	              title: '댓글을 삭제했습니다.',
	              showConfirmButton: false,
	              timer: 1000
	          })
	          .then(
	          ()=>{
					window.location.reload();
				})
	        } else {
	          Swal.fire({
	              icon: 'error',
	              title: '댓글 삭제에 실패했습니다.',
	              showConfirmButton: true
	            });
	        }
	      }
	      });
};

function writeReply() {
	const content = $("#reply").val();
	
	$.ajax({
	      type: "POST",
	      url: "./WriteReplyServlet",
	      data: { 
	    	  pid : '<%=postDTO.getPid()%>',
			  post_creator_id : '<%=postDTO.getCreator_id()%>',
			  creator_email : '<%=session.getAttribute("Email").toString()%>',
			  contents: content
	      },
	      success: function (result) {
	        if (result == 1) {
	          Swal.fire({
	              icon: 'success',
	              title: '댓글을 등록했습니다.',
	              showConfirmButton: false,
	              timer: 1000
	          })
	          .then(
	          ()=>{
					window.location.reload();
				})
	        } else {
	          Swal.fire({
	              icon: 'error',
	              title: '댓글 등록에 실패했습니다.',
	              showConfirmButton: true
	            });
	        }
	      }});
}
</script>
</html>