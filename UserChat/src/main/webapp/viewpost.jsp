<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="post.PostDTO"%>
<%@ page import="profile.ProfileDAO"%>
<%@ page import="profile.ProfileDTO"%>
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
						<th colspan="5"
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
						<td style="width: 5%;" class="ptitle"><%=i + 1%></td>
						<td style="width: 10%;" class="ptitle"><%=rlist.get(i).getNickname()%></td>
						<td style="text-align: left; word-break:break-all;"><%=rlist.get(i).getContents()%></td>
						<td style="width: 160px; text-align: left; padding: 0">
						<%=rlist.get(i).getCreate_date().substring(0, 11) + rlist.get(i).getCreate_date().substring(11, 13) + ":"
								+ rlist.get(i).getCreate_date().substring(14, 16)%>
						<% if(session.getAttribute("Email").toString().equals(rlist.get(i).getEmail())) { %>
							
								<button style="all: unset; margin: 10px 0"
									onclick="updateReply('<%=rlist.get(i).getRid()%>')"
									type="button">
								<img src="./image/write.png " alt="수정" width="15px" height="15px">
								</button>
								<button style="all: unset;"
									onclick="deleteReplyConfirm(<%=rlist.get(i).getRid()%>)"
									type="button">
								<img src="./image/delete.png " alt="삭제" width="15px" height="15px">
								</button>
						<%
						}
						%>
						</td>
					</tr>
					
					<%
					}
					} else {
					%>
					<tr>
						<td>등록된 댓글이 없습니다 😥</td>
					</tr>
					<%} %>
					<tr>
						<td colspan="4">
							<div style="display: flex;">
								<input type="text" onkeyup="javascript:displayBytes(100,'reply');" class="form-control" placeholder="댓글을 입력하세요." id="reply">
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
			boolean isWriter = session.getAttribute("Email").toString().equals(postDTO.getEmail());
			boolean isAdmin = session.getAttribute("Role").toString().equals("2");
				if(isWriter || isAdmin) {
					if(isWriter) {
			%>
				<a href="updatepost.jsp?category=<%=category %>&pid=<%=pid %>" class="btn btn-primary">수정</a>
				<%} %>
				<button
					onclick="deletePostConfirm()"
					class="btn btn-primary">삭제
				</button>
				<button
					onclick="updatePostCategory()"
					class="btn btn-primary">카테고리 변경
				</button>
			<%
				} else {
			%>
				<button
					onclick="friendRequest()"
					class="btn btn-primary">글쓴이에게 친구요청
				</button>
			<%} %>
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

async function updateReply(rids) {
	const { value: contents } = await Swal.fire({
		  input: 'textarea',
		  inputLabel: '댓글 수정',
		  inputPlaceholder: '댓글 내용을 입력하세요.',
		  inputAttributes: {
			    'aria-label': '댓글 내용을 입력하세요.'
			  },
		  showCancelButton: true
	})
		if (contents) {
			$.ajax({
			      type: "POST",
			      url: "./UpdateReplyServlet",
			      data: { 
			    	  rid: rids,
			    	  content: contents,
			      },
			      success: function (result) {
			        if (result == 1) {
			          Swal.fire({
			              icon: 'success',
			              title: '댓글을 수정했습니다.',
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
			              title: '댓글 수정에 실패했습니다.',
			              showConfirmButton: true
			            });
			        }
			      }});
	}
}

// 출처: https://javafactory.tistory.com/840
function fnCut(str,lengths)
{
      var len = 0;
      var newStr = '';
  
      for (var i=0;i<str.length; i++) 
      {
        var n = str.charCodeAt(i);
        
       var nv = str.charAt(i);
        

        if ((n>= 0)&&(n<256)) len ++;
        else len += 2;
        
        if (len>lengths) break;
        else newStr = newStr + nv;
      }
      return newStr;
}

function displayBytes( sz, id )
{
 var obj = document.getElementById( id );
 var e_index = 0;
 
 var byteLength = (function(s, b,i,c){
		for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
	    return b
	})(obj.value);
 
 if (byteLength > sz)
 { 
  if (event.keyCode != '8')
  {
 var chkdgsoweg =  ( sz / 2 );
 alert('한글은 ' + chkdgsoweg + '자 , 영문은 ' + sz + '자 까지 입력이 가능합니다.');
  }
  obj.value = fnCut(obj.value , sz);
 }
}

function deleteReplyConfirm(rid) {
	const swalWithBootstrapButtons = Swal.mixin({
		  customClass: {
		    confirmButton: 'btn btn-success',
		    cancelButton: 'btn btn-danger'
		  },
		  buttonsStyling: false
		})

		swalWithBootstrapButtons.fire({
		  title: '정말로 삭제하시겠어요?',
		  text: "삭제하면 되돌릴 수 없습니다.",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonText: '네',
		  cancelButtonText: '아니오',
		}).then((result) => {
		  if (result.isConfirmed) {
			deleteReply(rid)
		  }
		})
}

function deletePostConfirm() {
	const swalWithBootstrapButtons = Swal.mixin({
		  customClass: {
		    confirmButton: 'btn btn-success',
		    cancelButton: 'btn btn-danger'
		  },
		  buttonsStyling: false
		})

		swalWithBootstrapButtons.fire({
		  title: '정말로 삭제하시겠어요?',
		  text: "삭제하면 되돌릴 수 없습니다.",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonText: '네',
		  cancelButtonText: '아니오',
		}).then((result) => {
		  if(result.isConfirmed) {
			  location.href = 'deletePostAction.jsp?category=<%=category %>&pid=<%=pid %>';
		  }
		})
}

function friendRequest() {
	const swalWithBootstrapButtons = Swal.mixin({
		  customClass: {
		    confirmButton: 'btn btn-success',
		    cancelButton: 'btn btn-danger'
		  },
		  buttonsStyling: false
		})

		swalWithBootstrapButtons.fire({
		  title: '글 작성자 [<%=postDTO.getNickname()%>]님에게 친구 요청을 하시겠습니까?',
		  showCancelButton: true,
		  confirmButtonText: '네',
		  cancelButtonText: '아니오',
		}).then((result) => {
		  if (result.isConfirmed) {
			  $.ajax({
					type: "POST",
					url: "./FriendRequestServlet",
					data: { 
						FriendNickname: '<%=postDTO.getNickname()%>',
						userPrid : '<%=session.getAttribute("PRid").toString()%>'
					},
					success: function (result) {
						if (result == 1) {
						Swal.fire({
							icon: 'success',
							title: `친구요청을 완료했습니다.`,
							showConfirmButton: false,
							timer: 1500
							});
						} else {
						Swal.fire({
							icon: 'error',
							title: `이미 친구이거나,\n 친구요청을 보낸적이 있어요.`,
							showConfirmButton: true
							});
						}
					}
					});
		  }
		})
}

async function updatePostCategory() {
	const { value: cname } = await Swal.fire({
		  input: 'text',
		  inputLabel: '카테고리 변경',
		  inputPlaceholder: '정확한 카테고리 이름을 작성하세요(소문자)',
		  showCancelButton: true
	})
		if (cname) {
			$.ajax({
				type: "POST",
				url: "./UpdatePostCategoryServlet",
				data: { 
					pid: '<%=postDTO.getPid()%>',
					cname : cname
				},
				success: function (result) {
					if (result == 1) {
					Swal.fire({
						icon: 'success',
						title: `카테고리 변경을 성공했습니다.`,
						showConfirmButton: false,
						timer: 1500
						});
					} else {
					Swal.fire({
						icon: 'error',
						title: '이름을 정확하게 입력하세요!',
						showConfirmButton: true
						});
					}
				}
				})
		}
}

</script>
</html>