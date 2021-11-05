<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.css" />
    <link rel="stylesheet" href="css/custom.css?after" />
    <link rel="stylesheet" href="css/login.css?after" />
    <title>JSP AJax 실시간 회원제 채팅</title>
    <script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
  </head>

  <body>
    <% 
     String Email= null; 
     if (session.getAttribute("Email")!= null) { 
    	 Email = (String) session.getAttribute("Email"); 
     } 
    
     if (Email!= null) {
    	 String msg = "현재 로그인 되어 있는 상태 입니다."; 
    	 %>
    	 <script>showTimerAlert("현재 로그인 되어 있는 상태 입니다.");</script> 
    <% 
    	response.sendRedirect("index.jsp");
    	return;
     }  
     %>
    <nav class="navbar navbar-default">
      <div class="navbar-header">
        <button
          class="navbar navbar-toggle collapsed"
          data-toggle="collapsed"
          data-target="#bs-example-navbar-collapse-1"
          aria-expanded="false"
        >
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="index.jsp">실시간 회원제 채팅 서비스</a>
      </div>
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li class="active"><a href="index.jsp">메인</a></li>
        </ul>
        <% if(Email == null) { %>
        <ul class="nav navbar-nav navbar-right">
          <li class="dropdown">
            <a
              href="#"
              class="dropdown-toggle"
              data-toggle="dropdown"
              role="button"
              aria-haspop="true"
              aria-expanded="false"
            >
              접속하기 
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href="index.jsp">메인</a></li>
              <li><a href="Login.jsp">로그인</a></li>
              <li><a href="Join.jsp">회원가입</a></li>
            </ul>
          </li>
        </ul>
        <% } %>
      </div>
    </nav>
    <section class="center">
        <div class="header">
            <h1>
                 <span class = "service-name">로그인 </span>
            </h1>
        </div>
        <div class="form-wrap">
            <div class="user-info">
                <form class="user-info-form" method="post" action="./ProfileLoginServlet">
                    <input id = "Email" class = "id" name="Email" type="text" placeholder="Email" />
                    <input id = "Password" class = "pwd" name="Password" type="password" placeholder="Password"/>
                    <input class="login-Btn"type = "submit" value="접속하기">
                </form>
            </div>
        </div>
    </section>
  </body>
</html>
