<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder"%>	
<%@ page import="profile.ProfileDAO"%>
<%@ page import="profile.ProfileDTO"%>
<%@ page import="chatroom.ChatroomDAO"%>
<%@ page import="chatroom.ChatroomDTO"%>
<%@ page import="message.MessageDAO"%>
<%@ page import="message.MessageDTO"%>
<%@ page import="friend.FriendDAO"%>
<%@ page import="friend.FriendDTO"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<link rel="stylesheet" href="css/OpenSans.css?ver=1">
<link rel="stylesheet" href="css/MaterialIcons.css?ver=1">
<link rel="stylesheet" href="css/chatbox.css?ver=1">
<script type="text/javascript" src="js/jquery-3.6.0.min.js?ver=1"></script>
<%
String userEmail = (String) session.getAttribute("Email");
ProfileDTO currentUser = null;
List<FriendDTO> friendsDtoList = new ArrayList<FriendDTO>();
List<ProfileDTO> profileDtoList = new ArrayList<ProfileDTO>();
List<ChatroomDTO> chatroomList = new ArrayList<ChatroomDTO>();
List<ProfileDTO> op_profileDtoList = new ArrayList<ProfileDTO>();
int cur_id = -1;
try {
	currentUser = new ProfileDAO().getUserByEmail(userEmail);
} catch (Exception e) {
	e.getStackTrace();
	System.out.println(e.getMessage());
%><script>
				location.href = "logoutAction.jsp"
			</script>
<%
}
//getFriendList
if (currentUser != null) {
try {
	friendsDtoList = new FriendDAO().GetFriendList(currentUser.getPRid());
	chatroomList = new ChatroomDAO(currentUser).GetChatroomList();
	cur_id = currentUser.getPRid();
} catch (Exception e) {
	e.getStackTrace();
	System.out.println(e.getMessage());
%><script>
					location.href = "logoutAction.jsp"
				</script>
<%
}
if (friendsDtoList.isEmpty() == false) {
ProfileDAO connProfileDao = new ProfileDAO();
for (FriendDTO friend : friendsDtoList) {
	ProfileDTO friend_profile = connProfileDao.getUserByPRid(friend.getFriend_id());
	profileDtoList.add(friend_profile);
}
}
if (chatroomList.isEmpty() == false) {
ProfileDAO connProfileDao = new ProfileDAO();
for (ChatroomDTO chatroom : chatroomList) {
	ProfileDTO op_profile = connProfileDao.getUserByPRid(chatroom.getParticipant_id());
	if (op_profile.getPRid() == currentUser.getPRid())
		op_profile = connProfileDao.getUserByPRid(chatroom.getHost_id());
	op_profileDtoList.add(op_profile);
}
}
}
%>
<%
if (currentUser != null) {
%>
<div id="chat-circle" class="btn">
	<i class="material-icons">speaker_phone</i>
</div>
<div id="chatbox">
	<div class="chat-box-header">
		<div class="chat-box-toggle">
			<span class="material-icons">exit_to_app</span>
		</div>
	</div>
	<div id="topmenu">
		<button class="menulinks" onclick="openMenu(event, 'friendslist')"
			id="defaultOpen">
			<span class="material-icons">people</span>
		</button>
		<button class="menulinks" onclick="openMenu(event, 'chatroomslist')">
			<span class="material-icons">chat</span>
		</button>
	</div>
	<div id="chatroomslist" class="menucontents">
		<%
		if (!chatroomList.isEmpty()) {
		%>
		<div id="chatrooms">
			<%
			for (int i = 0; i < chatroomList.size(); i++) {
			%>
			<div class="chatroom">
				<img src="<%=op_profileDtoList.get(i).getProfileIMG()%>"
					class="chatroomimg" />
				<p>
					<strong><%=chatroomList.get(i).getCRname()%></strong><br> <span><%=op_profileDtoList.get(i).getNickname()%></span>
					<i><%=chatroomList.get(i).getCRId()%></i> <b><%=op_profileDtoList.get(i).getPRid()%></b>
				</p>
				<%
				if ((new FriendDAO().alreadyFriend(currentUser.getPRid(), op_profileDtoList.get(i).getPRid()))) {
				%>
				<div class="status alreadyfriend"></div>
				<%
				} else if ((new FriendDAO().alreadyRequestedFriend(currentUser.getPRid(), op_profileDtoList.get(i).getPRid()))) {
				if (!(new FriendDAO().alreadySendFriendRequest(currentUser.getPRid(), op_profileDtoList.get(i).getPRid()))) {
				%>
				<div class="status friendrequested"></div>
				<%
				} else {
				%>
				<div class="status friendrequest"></div>
				<%
				}
				} else {
				%>
				<div class="status"></div>
				<%
				}
				%>
			</div>
			<%
			}
			%>
		</div>
		<%
		}
		%>
	</div>
	<div id="friendslist" class="menucontents">
		<div id="friends">
			<%
			for (ProfileDTO profile : profileDtoList) {
			%>
			<div class="friend">
				<img src="<%=profile.getProfileIMG()%>" />
				<p>
					<strong><%=profile.getNickname()%></strong> <span><%=profile.getPRid()%></span>
				</p>
				<%
				if (new ChatroomDAO(currentUser).hasChatroom()) {
				%>
				<div class="status haschatroom"></div>
				<%
				} else {
				%>
				<div class="status"></div>
				<%
				}
				%>
			</div>
			<%
			}
			%>
		</div>
	</div>

	<div id="chatview" class="p1">
		<div id="chatroom_profile">
			<div id="close">
				<div class="cy"></div>
				<div class="cx"></div>
			</div>
			<p></p>
			<span></span> <i></i> <b></b>
		</div>
		<div id="chat-messages"></div>
		<div id="sendmessage">
			<input type="text" value="" />
			<button onclick="submitFunction()">
				<span class="material-icons"> send </span>
			</button>
		</div>

	</div>
	<div id="profileview" class="p2">
		<div id="profile">
			<div id="profile_close">
				<div class="cy"></div>
				<div class="cx"></div>
			</div>
			<p></p>
			<span></span>
			<div class="status"></div>
		</div>
		<div id="deleteFriend">
			<button onclick="deleteFriendFunction()">
				<span class="material-icons"> person_off </span>
			</button>
		</div>
		<div id="createChatroom">
			<input type="text" value="" />
			<button onclick="createChatroomFunction()">
				<span class="material-icons"> playlist_add </span>
			</button>
		</div>
		<div id="deleteChatroom">
			<button onclick="deleteChatroomFunction()">
				<span class="material-icons"> playlist_remove </span>
			</button>
		</div>
		<div id="createFriendrequest">
			<button onclick="friendRequestFunction()">
				<span class="material-icons"> person_add </span>
			</button>
		</div>
		<div id="deleteFriendrequest">
			<button onclick="denyAcceptFunction()">
				<span class="material-icons"> person_add_disabled </span>
			</button>
		</div>
		<div id="acceptFriendrequest">
			<button onclick="requestAcceptFunction()">
				<span class="material-icons"> person_add_alt </span>
			</button>
		</div>
	</div>
</div>
<script>
function check(){
	console.log("check");
}
function submitFunction() {
	console.log("check");
	var CRID = $("#chatroom_profile i").html();
	var fromID = "<%=cur_id%>";
	var toID = $("#chatroom_profile b").html();
	var chatContent = $("#sendmessage input").val();
	$.ajax({
		type: "POST",
		url: "./MessageSubmitServlet",
		data: {
			CRID : CRID,
			fromID: fromID,
			toID: toID,
			chatContent: chatContent
		},
		success: function (result) {
			if (result == 1) {
				Swal.fire({
					icon: 'success',
					title: `메시지 전송을 완료했습니다.`,
					showConfirmButton: false,
					timer: 1500
					});
				} else {
				Swal.fire({
					icon: 'error',
					title: `데이터베이스 오류`,
					showConfirmButton: true
					});
				}
			}
	});
	$("#sendmessage input").val("");
}
	var lastID = 0;
	function messageListFunction(listtype){
		var CRID = $("#chatroom_profile i").html();
		var fromID = "<%=cur_id%>";
		var toID = $("#chatroom_profile b").html();
		$.ajax({
			type: "POST",
			url: "./MessageListServlet",
			data: {
				CRID : CRID,
				fromID: fromID,
				toID: toID,
				listType: listtype,
			},
			success: function(data){
				if(data == "") return;
				var parsed = JSON.parse(data);
				var result = parsed.result;
				console.log(result);
				for(var i in result){
					addMessage(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value, result[i][4].value)
				}
				lastID = Number(result.last);
			} 
		});
	}
	function addMessage(profileImg, ishost, label, chatContent, chatTime) {
		if(label != "0")
			$("#chat-messages").append("<label>"+ label +"</label>");
		
		if(ishost == "1")
			$("#chat-messages").append('<div class= "message">' + 
								   '<img src= "'+profileImg+'" />' +
								   '<div class= "bubble">' +
								   chatContent +
								   '<div class= "corner"></div>' +
								   '<span>' + chatTime + '</span>' +
								   '</div>' +
								   '</div>');
		else
			$("#chat-messages").append('<div class= "message right">' + 
					   '<img src= "'+ profileImg +'" />' +
					   '<div class= "bubble">' +
					   chatContent +
					   '<div class= "corner"></div>' +
					   '<span>' + chatTime + '</span>' +
					   '</div>' +
					   '</div>');
	}
	function getInfiniteChat(){
		setInterval(function(){
			messageListFunction(lastID);
		}, 3000)
	}
function friendRequestFunction() {
    var FriendNickname = $("#profile p").html();
    // ajax 비동기 통신
	var CurrentUserNickname = "<%=currentUser.getNickname()%>";
	var userPrid = <%=cur_id%>;
	console.log(FriendNickname,CurrentUserNickname);
	if(FriendNickname == CurrentUserNickname){
		Swal.fire({
              icon: 'error',
              title: `자신에게는 친구요청을 보낼 수 없습니다.`,
              showConfirmButton: true
            });
	}else{
    	$.ajax({
		type: "POST",
		//아래의 url로 보내줌
		url: "./FriendRequestServlet",
		data: { 
			FriendNickname: FriendNickname,
			userPrid : userPrid
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
				title: `1. 이미 친구인 유저.\n2. 친구요청에 포함되는 유저.\n3. 존재하지 않는 유저.`,
				showConfirmButton: true
				});
			}
		}
		});
	}
}

function createChatroomFunction() {
    var userPrid = "<%=cur_id%>";
	var op_Prid = $("#profile span").html();
	var chatroomName = ("#createChatroom input").val();
	if(userPrid == op_Prid){
		Swal.fire({
              icon: 'error',
              title: `자기 자신과 채팅방을 생성할 수 없습니다.`,
              showConfirmButton: true
            });
	}else{
    	$.ajax({
		type: "POST",
		//아래의 url로 보내줌
		url: "./createChatroomServlet",
		data: { 
			userPrid: userPrid,
			op_Prid : op_Prid,
			chatroomName : chatroomName
		},
		//성공했다면 result 값을 반환받음
		success: function (result) {
			if (result == 1) {
			Swal.fire({
				icon: 'success',
				title: `채팅방 생성을 완료했습니다.`,
				showConfirmButton: false,
				timer: 1500
				});
			} else {
			Swal.fire({
				icon: 'error',
				title: `1. 이미 채팅방이 있는 유저.\n2. 존재하지 않는 유저.`,
				showConfirmButton: true
				});
			}
		}
		});
	}
}

function deleteChatroomFunction() {
    var chatroomID = $("#chatroom_profile i").html();
    	$.ajax({
		type: "POST",
		//아래의 url로 보내줌
		url: "./deleteChatroomServlet",
		data: { 
			chatroomID: chatroomID
		},
		//성공했다면 result 값을 반환받음
		success: function (result) {
			if (result == 1) {
			Swal.fire({
				icon: 'success',
				title: `채팅방 삭제를 완료했습니다.`,
				showConfirmButton: false,
				timer: 1500
				});
			} else {
			Swal.fire({
				icon: 'error',
				title: `1. 채팅방이 없는 유저.\n2. 존재하지 않는 유저.`,
				showConfirmButton: true
				});
			}
		}
		});
}

function requestAcceptFunction() {
   var friendPRid = $("#profile span").html();
   var userPRid = <%=cur_id%>;
   $.ajax({
      type: "POST",
      //아래의 url로 보내줌
      url: "./RequestAcceptServlet",
      data: { 
    	  friendPRid : friendPRid,
		  userPRid : userPRid
	  },
      //성공했다면 result 값을 반환받음
      success: function (result) {
        if (result == 1) {
          Swal.fire({
              icon: 'success',
              title: `친구요청을 수락했습니다.`,
              showConfirmButton: false,
              timer: 1500
            }).then(
				()=>{
					window.location.reload();
				}
			)
        } else {
          Swal.fire({
              icon: 'error',
              title: `친구요청 수락에 실패했습니다.`,
              showConfirmButton: true
            });
        }
      }
    });
	
}
function denyAcceptFunction() {
	var friendPRid = $("#profile span").html();
	var userPRid = <%=cur_id%>;
	$.ajax({
      type: "POST",
      //아래의 url로 보내줌
      url: "./RequestDenyServlet",
      data: { 
    	  friendPRid : friendPRid,
		  userPRid : userPRid
	  },
      //성공했다면 result 값을 반환받음
      success: function (result) {
        if (result == 1) {
          Swal.fire({
              icon: 'success',
              title: `요청 삭제에 성공했습니다.`,
              showConfirmButton: false,
              timer: 1500
            }).then(
				()=>{
					window.location.reload();
				}
			)
        } else {
          Swal.fire({
              icon: 'error',
              title: `요청 삭제에 실패했습니다.`,
              showConfirmButton: true
            });
        }
      }
    });
}
function deleteFriendFunction(){
	var friendPRid = $("#profile span").html();
	var userPRid = <%=cur_id%>;
	$.ajax({
      type: "POST",
      //아래의 url로 보내줌
      url: "./DeleteFriendServlet",
      data: { 
    	  friendPRid : friendPRid,
		  userPRid : userPRid
	  },
      //성공했다면 result 값을 반환받음
      success: function (result) {
        if (result == 1) {
          Swal.fire({
              icon: 'success',
              title: `친구 삭제에 성공했습니다.`,
              showConfirmButton: false,
              timer: 1500
            }).then(
				()=>{
					window.location.reload();
				}
			)
        } else {
          Swal.fire({
              icon: 'error',
              title: '친구 삭제에 실패했습니다.',
              showConfirmButton: true
            });
        }
      }
    });
}
	function openMenu(evt, menuName) {
		var i, menucontents, menulinks;
		menucontents = document.getElementsByClassName("menucontents");
		for (i = 0; i < menucontents.length; i++) {
			menucontents[i].style.display = "none";
		}
		menulinks = document.getElementsByClassName("menulinks");
		for (i = 0; i < menulinks.length; i++) {
			menulinks[i].className = menulinks[i].className.replace(" active",
					"");
		}
		document.getElementById(menuName).style.display = "block";
		evt.currentTarget.className += " active";
	}
	document.getElementById("defaultOpen").click();
function refresh() {
    $.ajax({
      url: "",
      dataType: "text",
      success: function(html) {
        $('#fu').replaceWith($.parseHTML(html));
        setTimeout(refresh,2000);
      }
    });
  }
	$(document).ready(function() {
		messageListFunction("0");
		getInfiniteChat();
	});	
</script>
<script type="text/javascript" src="js/chatbox.js?ver=1"></script>
<%
}
%>