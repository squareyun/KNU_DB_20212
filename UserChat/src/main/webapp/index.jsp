<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.css" />
    <link rel="stylesheet" href="css/custom.css?after" />
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

        <% } else { %>
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
              회원관리
              <span class="caret"></span>
            </a>
             <ul class="dropdown-menu">
            	<li>
            		<a href = "logoutAction.jsp">로그아웃</a>
            	</li>
            </ul>
          </li>
        </ul>

        <% } %>
      </div>
    </nav>
  </body>
</html>
