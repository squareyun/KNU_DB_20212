<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="post.PostDTO"%>
<%@ page import="profile.ProfileDTO"%>
<%@ page import="profile.ProfileDAO"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<%@ include file="layout/header.jsp"%>
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
	%>
	<%@ include file="layout/navigation.jsp"%>
	<%
	if (session.getAttribute("messageType") != null) {
		session.removeAttribute("messageType");
		session.removeAttribute("messageContent");
	}
	%>
	<div class="container">
		<h1>유저 검색 기능</h1>
		<form id="searchForm">
			<table class="table table-bordered table-hover"
				style="text-align: center; border: 1px solid #dddddd">
				<tbody>
					<tr>
						<td style="width: 110px"><h5>닉네임</h5></td>
						<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
							id="nickname" type="text" name="nickname" maxlength="20"
							size="20" style="width: 100%;" /></td>
						<td style="width: 110px"><h5>성별</h5></td>
						<td>
							<div style="display: flex; align-content: space-between;">
								<div style="width: 100%;">
									<input class="" id="genderall" type="radio" name="gender"
										value="무관" checked /> <label>무관</label>
								</div>
								<div style="width: 100%;">
									<input class="" id="man" type="radio" name="gender" value="남자" />
									<label>남자</label>
								</div>
								<div style="width: 100%;">
									<input class="" id="woman" type="radio" name="gender"
										value="여자" /> <label>여자</label>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td style="width: 110px"><h5>COUNTRY</h5></td>
						<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
							id="country" type="text" name="country" maxlength="20" size="20"
							style="width: 100%;" /></td>
						<td style="width: 110px"><h5>STATE</h5></td>
						<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
							id="state" type="text" name="state" maxlength="20" size="20"
							style="width: 100%;" /></td>
					</tr>
					<tr>
						<td style="width: 110px"><h5>CITY</h5></td>
						<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
							id="city" type="text" name="city" maxlength="20" size="20"
							style="width: 100%;" /></td>
						<td style="width: 110px"><h5>STREET</h5></td>
						<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
							id="street" type="text" name="street" maxlength="20" size="20"
							style="width: 100%;" /></td>
					</tr>
					<tr>
						<td style="width: 110px"><h5>성</h5></td>
						<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
							id="fname" type="text" name="fname" maxlength="20" size="20"
							style="width: 100%;" /></td>
						<td style="width: 110px"><h5>이름</h5></td>
						<td><input class="" placeholder="조건에 포함시키지 않으려면 비워두세요."
							id="lname" type="text" name="lname" maxlength="20" size="20"
							style="width: 100%;" /></td>
					</tr>
					<tr>
						<td style="width: 110px"><h5>가입날짜</h5></td>
						<td colspan="3">
							<div style="display: flex; align-content: space-between;">
								<div style="width: 100%">
									<input class="" id="startdate" type="date" name="startdate" />
									<span>이후</span>
								</div>
								<div style="width: 100%">
									<input class="" id="enddate" type="date" name="enddate" /> <span>이전</span>
								</div>
							</div>
					</tr>

					<tr>
						<td style="text-align: center;" colspan="4"><input
							class="btn btn-danger " type="reset" value="초기화">
							<p class="btn btn-primary " onclick="search()">검색</p></td>
					</tr>
				</tbody>
			</table>
			<hr />
			<h3 id="searchResultNum">조회 결과</h3>

			<div style="height: 200px; overflow-x: auto; overflow-y: auto;">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">성</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">이름</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">PRID</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">이메일</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">경고</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">전화번호</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">가입날짜</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">COUNTRY</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">STATE</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">CITY</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">STREET</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">닉네임</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">성별</th>
							<th
								style="background-color: #eeeeee; text-align: center; color: #000000;">역할</th>
						</tr>
					</thead>
					<tbody id="searchResult">
					</tbody>
				</table>
			</div>
		</form>
		<hr />
		<h1>정지 유저 목록</h1>
		<table class="table table-bordered table-hover"
			style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">성</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">이름</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">PRID</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">이메일</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">전화번호</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">가입날짜</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">COUNTRY</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">STATE</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">CITY</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">STREET</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">닉네임</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">성별</th>
					<th
						style="background-color: #eeeeee; text-align: center; color: #000000;">
						비고</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<ProfileDTO> RPlist = new ProfileDAO().getRestrictedProfile();

				for (int i = 0; i < RPlist.size(); i++) {
				%>
				<tr>
					<td><%=RPlist.get(i).getFname()%></td>
					<td><%=RPlist.get(i).getLname()%></td>
					<td><%=RPlist.get(i).getPRid()%></td>
					<td><%=RPlist.get(i).getEmail()%></td>
					<td><%=RPlist.get(i).getPhone_num()%></td>
					<td><%=RPlist.get(i).getCreate_date().toString().substring(0, 10)%></td>
					<td><%=RPlist.get(i).getCountry()%></td>
					<td><%=RPlist.get(i).getState()%></td>
					<td><%=RPlist.get(i).getCity()%></td>
					<td><%=RPlist.get(i).getStreet()%></td>
					<td><%=RPlist.get(i).getNickname()%></td>
					<td><%=RPlist.get(i).getGender()%></td>
					<td><p class="btn btn-danger"
							onclick="release(<%=RPlist.get(i).getPRid()%>)">풀기</p></td>
				</tr>
				<%
				}
				%>

			</tbody>
		</table>
	</div>
</body>
<script>
	$('#adminActiveId').addClass("active")
	
	function search() {
		$.ajax({
	        url: "./ProfileSearchServlet",
	        type: "post",
	        data: {
	        	nickname: $("#nickname").val(),
	        	gender: $("input[name='gender']:checked").val(),
	        	country: $("#country").val(),
	        	state: $("#state").val(),
	        	city: $("#city").val(),
	        	street: $("#street").val(),
	        	fname: $("#fname").val(),
	        	lname: $("#lname").val(),
	        	startdate: $("#startdate").val(),
	        	enddate: $("#enddate").val(),
	        },
	        success: function (data) {
	        	var result = "";
	        	for(i in data) {
	        		result += "<tr>"
	        		result += "<td>" + data[i].fname + "</td>";
	        		result += "<td>" + data[i].lname + "</td>";
	        		result += "<td>" + data[i].prid + "</td>";
	        		result += "<td>" + data[i].email + "</td>";
	        		result += "<td>" + data[i].restriction + "</td>";
	        		result += "<td>" + data[i].phone_num + "</td>";
	        		result += "<td>" + data[i].create_date.substr(0, 10) + "</td>";
	        		result += "<td>" + data[i].country + "</td>";
	        		result += "<td>" + data[i].state + "</td>";
	        		result += "<td>" + data[i].city + "</td>";
	        		result += "<td>" + data[i].street + "</td>";
	        		result += "<td>" + data[i].nickname + "</td>";
	        		result += "<td>" + data[i].gender + "</td>";
	        		
	        		var role = "";
	        		if(data[i].role_id == '3') {
	        			role = '정지'
	        		} else if( data[i].role_id == '2') {
	        			role = '관리자'
	        		} else {
	        			role = '유저'
	        		}
	        		result += "<td>" + role + "</td>";
	        		result += "</tr>";
	        	}
	          $('#searchResult').html(result);
	          $('#searchResultNum').html('조회 결과 (총 ' + data.length +' 개의 결과가 조회되었습니다..)')
	        },
	      });
	}
	
	function release(prid) {
		const swalWithBootstrapButtons = Swal.mixin({
			  customClass: {
			    confirmButton: 'btn btn-success',
			    cancelButton: 'btn btn-danger'
			  },
			  buttonsStyling: false
			})

			swalWithBootstrapButtons.fire({
			  title: '정말로 해당 회원을 정지 해제시키겠습니까?',
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonText: '네',
			  cancelButtonText: '아니오',
			}).then((result) => {
			  if(result.isConfirmed) {
				  $.ajax({
						type: "POST",
						url: "./ProfileRestrictionServlet",
						data: { 
							prid: prid,
							change: 1,
						},
						success: function (result) {
							if (result == 1) {
							Swal.fire({
								icon: 'success',
								title: `성공적으로 수행했습니다.`,
								showConfirmButton: false,
								timer: 1500
								})
								.then(
					          ()=>{
									window.location.reload();
								});
							}
							
							else {
							Swal.fire({
								icon: 'error',
								title: `실패했습니다.`,
								showConfirmButton: true
								});
							}
						}
						});
			  }
			})
	}
</script>
</html>