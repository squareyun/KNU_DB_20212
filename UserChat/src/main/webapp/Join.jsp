<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <!DOCTYPE html>
<html>
  <%@ include file = "layout/header.jsp" %>
  
  <body>
    <%@ include file = "layout/navigation.jsp" %>
    <div class="container">
      <form method="post" action="./ProfileRegisterServlet">
        <table
          class="table table-bordered table-hover"
          style="text-align: center; border: 1px solid #dddddd"
        >
          <thead>
            <tr>
              <th colspan="3"><h4>회원가입</h4></th>
            </tr>
          </thead>
          <tbody>
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
                />
              </td>
              <td style="width: 110px">
                <button
                  class="btn btn-primary"
                  onclick="registerEmailCheckFunction();"
                  type="button"
                >
                  중복체크
                </button>
              </td>
            </tr>
            <tr>
              <td style="width: 110px"><h5>비밀번호</h5></td>
              <td colspan="2">
                <input
                  class="form-control"
                  id="Password1"
                  type="password"
                  name="Password1"
                  maxlength="20"
                  placeholder="비밀번호를 입력하세요."
                />
              </td>
            </tr>
            <tr>
              <td style="width: 110px"><h5>비밀번호 확인</h5></td>
              <td colspan="2">
                <input
                  onkeyup="passwordCheckFunction();"
                  class="form-control"
                  id="Password2"
                  type="password"
                  name="Password2"
                  maxlength="20"
                  placeholder="비밀번호 확인을 해주세요."
                />
              </td>
            </tr>
            <tr >
            
              <td style="width: 55px"><h5>성,이름</h5></td>
              <td colspan="2">
              	<div class="col-md-6 ">
    			<input class="form-control"
                  id="Fname"
                  type="text"
                  name="Fname"
                  maxlength="10"
                  placeholder="성">
  				</div>
  				<div class="col-md-6">
    			<input class="form-control"
                  id="Lname"
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
                        checked
                      />남자
                    </label>
                    <label class="btn btn-primary">
                      <input
                        type="radio"
                        name="Gender"
                        autocomplete="off"
                        value="여자"
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
              <td>
                <input
                  class="form-control" 
                  id="Nickname"
                  type="text"
                  name="Nickname"
                  maxlength="20"
                  placeholder="닉네임 입력해 주세요."
                />
              </td>
              <td style="width: 110px">
                <button
                  class="btn btn-primary"
                  onclick="registerNicknameCheckFunction();"
                  type="button"
                >
                  중복체크
                </button>
              </td>
            </tr>
            <tr>
              <!-- 비밀번호를 실시간으로 확인하여 같은지 확인. -->
              <td style="text-align: center" colspan="3">
                <h5 style="color: red" id="passwordCheckMessage"></h5>
                <input
                  class="btn btn-primary "
                  type="submit"
                  value="등록"
                />
              </td>
            </tr>
          </tbody>
        </table>
      </form>
    </div>
    <script type="text/javascript">
    
	  function showTimerAlert(str) {
	  	  Swal.fire({
	            icon: 'fail',
	            title: str,
	            showConfirmButton: false,
	            timer: 1500
	          });
	  }
      function registerEmailCheckFunction(){
        var Email = $("#Email").val();
        // ajax 비동기 통신
        $.ajax({
          type: "POST",
          //아래의 url로 보내줌
          url: "./ProfileRegisterEmailCheckServlet",
          data: { Email: Email },
          //성공했다면 result 값을 반환받음
          success: function (result) {
            if (result == 1) {
              Swal.fire({
                  icon: 'success',
                  title: `중복되지 않는 이메일입니다.`,
                  showConfirmButton: false,
                  timer: 1500
                });
            } else {
              Swal.fire({
                  icon: 'error',
                  title: `중복되는 이메일입니다.`,
                  showConfirmButton: true
                });
            }
          }
        });
      }
      function registerNicknameCheckFunction() {
        var Nickname = $("#Nickname").val();
        // ajax 비동기 통신
        $.ajax({
          type: "POST",
          //아래의 url로 보내줌
          url: "./ProfileRegisterNicknameCheckServlet",
          data: { Nickname: Nickname },
          //성공했다면 result 값을 반환받음
          success: function (result) {
            if (result == 1) {
              Swal.fire({
                  icon: 'success',
                  title: `중복되지 않는 닉네임입니다.`,
                  showConfirmButton: false,
                  timer: 1500
                });
            } else {
              Swal.fire({
                  icon: 'error',
                  title: `중복되는 닉네임입니다.`,
                  showConfirmButton: true
                });
            }
          }
        });
      }
      // javascript 비밀번호 체크 기능
      function passwordCheckFunction() {
        var Password1 = document.getElementById("Password1").value;
        var Password2 = document.getElementById("Password2").value;
        if (Password1 != Password2) {
          document.getElementById("passwordCheckMessage").innerHTML =
            "비밀번호가 틀립니다. 확인해주세요.";
        } else {
          document.getElementById("passwordCheckMessage").innerHTML =
            "";
        }
      }

    </script>
  </body>
</html>
