<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import = "profile.ProfileDAO" %>
<%@ page import = "profile.ProfileDTO" %>
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
    	String changedPriofileImgUrl = null;
    	String userEmail= (String) session.getAttribute("Email");
    	ProfileDTO currentUser = null;

    	try{
      		 currentUser = new ProfileDAO().getUserByEmail(userEmail);

    	} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			%><script>location.href = "logoutAction.jsp"</script><%
		}

     %>
     <% if(currentUser != null){ %>
    	 <div class="container">
         <form method="post" enctype="multipart/form-data" action="./ProfileUpdateServlet" onSubmit = "return uploadImg();">
           <table
             class="table table-bordered table-hover"
             style="text-align: center; border: 1px solid #dddddd"
           >
             <thead>
               <tr>
                 <th colspan="3"><h4>회원 정보 수정</h4></th>
               </tr>
             </thead>
             <tbody>
               <tr>
                 <td style="width: 110px"><h5>프로필 사진</h5></td>
                 <td>
                   <%if(currentUser.getProfileImg() == null){ %>
                      <label for="ProfileImgFile">
                   		  <img src="./image/addProfile.png" class = "userProfileImg" alt="User Profile Image" width="150" height="150">
                      </label>
                      <input style="display:none;" type="file" id="ProfileImgFile" name="ProfileImgFile" accept="image/*" onchange="loadFile(this)">
                   <%}else{ %>
                   		<label for="ProfileImgFile">
                   		  <img src="./image/addProfile.png" class = "userProfileImg" alt="User Profile Image" width="150" height="150">
                      </label>
                      <input style="display:none;" type="file" id="ProfileImgFile" name="ProfileImgFile" accept="image/*" onchange="loadFile(this)">
                   <%} %>
                      <input style="display:none;" type="text" class = "ProfileImg" id="ProfileImg" name="ProfileImg" value="">
                 </td>
               </tr>
               <tr>
                 <td style="width: 110px"><h5>이메일</h5></td>
                 <td>
                   <input
                     class="form-control"
                     type="email"
                     id="Email"
                     name="Email"
                     maxlength="20"
                     placeholder="이메일 입력해주세요"
                     readonly
                     value="<%= currentUser.getEmail() %>"
                   />
                 </td>
               </tr>
               <tr>
                 <td style="width: 110px"><h5>비밀번호</h5></td>
                 <td colspan="2">
                   <input
                     class="form-control"
   				  value="<%= currentUser.getPassword() %>"
                     id="Password"
                     type="password"
                     name="Password"
                     maxlength="20"
                     placeholder="비밀번호를 입력하세요."
                   />
                 </td>
               </tr>
               <tr >
                 <td style="width: 55px"><h5>성,이름</h5></td>
                 <td colspan="2">
                 	<div class="col-md-6 ">
       			<input class="form-control"
                     id="Fname"
   				           value="<%= currentUser.getFname() %>"
                     type="text"
                     name="Fname"
                     maxlength="10"
                     placeholder="성">
     				</div>
     				<div class="col-md-6">
       			<input class="form-control"
                     id="Lname"
   				  value="<%= currentUser.getLname() %>"
                     type="text"
                     name="Lname"
                     maxlength="10"
                     placeholder="이름">
     				</div>
                 </td>
               </tr>
               <tr>
                 <td style="width: 110px"><h5>성별</h5></td>
                 <td colspan="2">
                   <div
                     class="form-group"
                     style="text-align: center; margin: 0 auto"
                   >
                     <div class="btn-group" data-toggle="buttons">
                       <label class="btn btn-primary active">
                         <input
                           type="radio"
                           name="Gender"
                           autocomplete="off"
                           value="남자"
                           <% if(currentUser.getGender().equals("남자")) out.print("checked"); %>
                         />남자
                       </label>
                       <label class="btn btn-primary">
                         <input
                           type="radio"
                           name="Gender"
                           autocomplete="off"
                           value="여자"
   					              	<% if(currentUser.getGender().equals("여자")) out.print("checked"); %>
                         />여자
                       </label>
                     </div>
                   </div>
                 </td>
               </tr>
               <tr>
                 <td style="width: 110px"><h5>연락처</h5></td>
                 <td colspan="2">
                   <input
                     class="form-control"
   				           value="<%= currentUser.getPhone_num() %>"
                     id="Phone_num"
                     type="text"
                     name="Phone_num"
                     maxlength="20"
                     placeholder="연락처 입력해 주세요."
                   />
                 </td>
               </tr>
               <tr>
                 <td style="width: 110px"><h5>주소</h5></td>
                 <td colspan="4">
                 	<div class="col-md-2" id = "form-input1">
                 		<input
                     		class="form-control pull-left"
   						value="<%= currentUser.getCountry() %>"
                     		id="Country"
                     		type="text"
                     		name="Country"
                     		maxlength="20"
                     		placeholder="나라"
                   	/>
                  	</div>
                 	<div class="col-md-2" id = "form-input2">
                 		<input
   	                  class="form-control"
   					  value="<%= currentUser.getState() %>"
   	                  id="State"
   	                  type="text"
   	                  name="State"
   	                  maxlength="20"
   	                  placeholder="도시"
   	                />
                  	</div>
                  	<div class="col-md-2" id = "form-input3">
   	                <input
   	                  class="form-control"
   					          value="<%= currentUser.getCity() %>"
   	                  id="City"
   	                  type="text"
   	                  name="City"
   	                  maxlength="20"
   	                  placeholder="지역"
   	                />
                  	</div>
                  	<div class="col-md-2" id = "form-input4">
                 		<input
   	                  class="form-control"
   					          value="<%= currentUser.getStreet() %>"
   	                  id="Street"
   	                  type="text"
   	                  name="Street"
   	                  maxlength="20"
   	                  placeholder="동 "
   	                />
                  	</div>
                 </td>
               </tr>
               <tr>
                 <td style="width: 110px"><h5>닉네임</h5></td>
                 <td colspan="2">
                   <input
                     class="form-control" 
                     value="<%= currentUser.getNickname() %>"
                     readonly
                     id="Nickname"
                     type="text"
                     name="Nickname"
                     maxlength="20"
                     placeholder="닉네임 입력해 주세요."
                   />
                 </td>
               </tr>
               <tr>
               <tr>
                 <!-- 비밀번호를 실시간으로 확인하여 같은지 확인. -->
                 <td style="text-align: center" colspan="3">
                   <h5 style="color: red" id="passwordCheckMessage"></h5>
                   <input
                     class="btn btn-primary "
                     type="submit"
                     value="수정"
                   />
                 </td>
               </tr>
             </tbody>
           </table>
         </form>
       </div>
    	 
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
   <script>
    
      var currentImgFile = null;
   	  function loadFile(event){
        const theFile = event.files[0];
        currentImgFile = theFile;
        var profileImgTag = document.querySelector('.userProfileImg');
        if (theFile) {
          const reader = new FileReader();
          reader.readAsDataURL(theFile);
          reader.onloadend = (finishedEvent) => {
            const {
              currentTarget: { result },
            } = finishedEvent;
            changedPriofileImgUrl = result;
            profileImgTag.src  = changedPriofileImgUrl;
          };
        }
        else{
          showErrorMessage('파일 정보가 없습니다.');
          profileImgTag.src  = './image/addProfile.png';
          changedPriofileImgUrl = null;
        }
   	  }
    function uploadImg() {
        if(currentImgFile == undefined || currentImgFile == null){
          return true;
        }
        var profileImg = document.querySelector('.ProfileImg');
        var profileImgTag = document.querySelector('.userProfileImg');
        var imageUrl = profileImgTag.src;
        var form = new FormData();
        form.append("filename", currentImgFile.name);
        form.append("image", currentImgFile);
        var settings = {
          "url": "https://api.imgbb.com/1/upload?key=544aaa8b87c8d0918576b49f664d1333",
          "method": "POST",
          "timeout": 0,
          "processData": false,
          "mimeType": "multipart/form-data",
          "contentType": false,
          "data": form
        };
        return $.ajax(settings).done(function (response) {
          var jx = JSON.parse(response);
          $(".ProfileImg").val(jx.data.url);       
        });
    }
   </script>
  </body>
</html>
