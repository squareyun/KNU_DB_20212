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
				for(FriendDTO friend : friendsDtoList){
					ProfileDTO friend_profile =  new ProfileDAO().getUserByPRid(friend.getFriend_id());
					profileDtoList.add(friend_profile);
				}
			}
		}
		
     %>
    <% if(currentUser != null){ %>
        <section class = "friends-screen">
			<h2>친구 페이지</h2>
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
            </main>
			<footer>
				<hr/>
				<h3>친구 추가</h3>
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
$('#friendActiveId').addClass("active")
</script>
</html>