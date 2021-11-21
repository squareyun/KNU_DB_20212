<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "profile.ProfileDAO" %>
<%@ page import = "profile.ProfileDTO" %>
<%@ page import = "friend.FriendDAO" %>
<%@ page import = "friend.FriendDTO"%>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.util.ArrayList"%>
<%@ page import = "java.util.List"%>

<!DOCTYPE html>
<html>
<%@ include file = "layout/header.jsp" %>
<body>
    <%@ include file = "layout/navigation.jsp" %>
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
    <% 
    	String userEmail= (String) session.getAttribute("Email");
    	ProfileDTO currentUser = null;
		List<FriendDTO> friendsDtoList = new ArrayList<FriendDTO>();
		List<ProfileDTO> profileDtoList = new ArrayList<ProfileDTO>();
    	try{
      		 currentUser = new ProfileDAO().getUserByEmail(userEmail);

    	} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			%><script>location.href = "logoutAction.jsp"</script><%
		}
		if(currentUser != null) {
			try{
				friendsDtoList = new FriendDAO().GetFriendList(currentUser.getPRid());

			} catch (Exception e) {
				e.getStackTrace();
				System.out.println(e.getMessage());
				%><script>location.href = "logoutAction.jsp"</script><%
			}
			if(friendsDtoList.isEmpty()== false){
				ProfileDAO connProfileDao = new ProfileDAO();
				for(FriendDTO friend : friendsDtoList){
					ProfileDTO friend_profile =  connProfileDao.getUserByPRid(friend.getFriend_id());
					profileDtoList.add(friend_profile);
				}
			}
		}
		
     %>
    <% if(currentUser != null){ %>
        <section class = "friends-screen" style="margin-bottom : 5%;">
            <header>
                <h3>나</h3>
	            <div class="user-component">
	                <div class="user-component__column">
						<%if(currentUser.getProfileImg() != null){ %>
							<img
								src="<%= currentUser.getProfileImg() %>"
								alt="user"
								class="user-component__avatar user-component__avatar--xs"
							/>
						<% } else { %>
							<img
								src="./image/profile.png"
								alt="user"
								class="user-component__avatar user-component__avatar--xs"
							/>
						<% } %>
	                    <div class="user-component__text">
	                        <h4 class="user-component__title"><%= currentUser.getNickname() %></h4>
	                        <h6 class="user-component__subtitle"><%= currentUser.getPhone_num() %></h6>
	                    </div>
	                </div>
	            </div>
            </header>
            <main>
				<hr/>
				<h3>친구 목록</h3>
				<% if(profileDtoList.isEmpty() == false) { %>
					<ul class="firends-list" style="margin-top : 3%;">
						<% for (ProfileDTO profile : profileDtoList) { %>
							<li class="firend-one" style="list-style : none; margin-left : 0.7%;">
								<div class="user-component">
									<div class="user-component__column">
										<%if(profile.getProfileImg() != null){ %>
											<img
												src="<%= profile.getProfileImg() %>"
												alt="user"
												class="user-component__avatar user-component__avatar--xs"
											/>
										<% } else { %>
											<img
												src="./image/profile.png"
												alt="user"
												class="user-component__avatar user-component__avatar--xs"
											/>
										<% } %>
										<div class="user-component__text">
											<h4 class="user-component__title"><%= profile.getNickname() %></h4>
											<h6 class="user-component__subtitle"><%= profile.getPhone_num() %></h6>
										</div>
									</div>
								</div>
							</li>
						<% } %>
					</ul>
				<% } else { %>
					<div style="width : 100%; height : 25vh; display : flex; justify-content:center; align-items:center;">
						<img
							src="./image/empty.png"
							style="width: 200px; height : 200px;"
							alt="empty"
						/>
					</div>
				<%} %>
            </main>
			<footer>
				<hr/>
				<h3>친구 요청</h3>
				<form id = "addFriendForm">
					<table
						class="table table-bordered table-hover"
						style="text-align: center; border: 1px solid #dddddd"
					>
						<thead style="display:none;">
							<tr>
								<th colspan="3"><h4>친구 닉네임 정보</h4></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td style="width: 110px"><h5>닉네임</h5></td>
								<td>
									<input
										class="form-control" 
										placeholder="Nickname을 입력하세요"
										id="FriendNickname"
										type="text"
										name="FriendNickname"
										maxlength="20"
										placeholder="닉네임 입력해 주세요."
									/>
								</td>
								<td style="width: 110px">
									<button
									class="btn btn-secondary"
									onclick="friendNicknameCheckFunction();"
									type="button"
									>
									유저 확인
									</button>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="3">
									<input
										class="btn btn-primary "
										type="reset"
										onclick="friendRequestFunction();"
										value="요청하기"
									/>
								</td>
							</tr>
						</tbody>
					</table>
					<hr/>
					<h3>받은 친구 요청</h3>
			</footer>
        </section>
    <% } else{ %>
     	<div>
     		<h1>Error 유저 정보가 없습니다. 로그아웃이 진행됩니다.</h1>
     		<script>location.href='logoutAction.jsp' </script>
     	</div>
      
    <%} %>

    <%
		 if (session.getAttribute("messageType")!= null) { 
			 session.removeAttribute("messageType");
			 session.removeAttribute("messageContent");
		 }; 
	 %>
</body>
<script>
	// $('#friendActiveId').addClass("active");
	function friendNicknameCheckFunction() {
        var FriendNickname = $("#FriendNickname").val();
        // ajax 비동기 통신
		console.log(FriendNickname);
        $.ajax({
          type: "POST",
          //아래의 url로 보내줌
          url: "./FriendNicknameCheckServlet",
          data: { FriendNickname: FriendNickname },
          //성공했다면 result 값을 반환받음
          success: function (result) {
            if (result == 1) {
              Swal.fire({
                  icon: 'success',
                  title: `존재하는 유저의 닉네임입니다.`,
                  showConfirmButton: false,
                  timer: 1500
                });
            } else {
              Swal.fire({
                  icon: 'error',
                  title: `존재하지 않는 유저의 닉네임입니다.`,
                  showConfirmButton: true
                });
            }
          }
        });
    }
	function friendRequestFunction() {
        var FriendNickname = $("#FriendNickname").val();
        // ajax 비동기 통신
		console.log(FriendNickname);
        $.ajax({
          type: "POST",
          //아래의 url로 보내줌
          url: "./FriendRequestServlet",
          data: { 
			  FriendNickname: FriendNickname,
			  userPrid : <%= currentUser.getPRid() %>
		  },
          //성공했다면 result 값을 반환받음
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
                  title: `친구요청에 실패했습니다.`,
                  showConfirmButton: true
                });
            }
          }
        });
    }
</script>
</html>