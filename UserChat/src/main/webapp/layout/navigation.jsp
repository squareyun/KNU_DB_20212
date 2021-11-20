<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
    <% 
     String Email= null;
     if (session.getAttribute("Email")!= null) { 
    	 Email = (String) session.getAttribute("Email"); 
    	 String path = request.getServletPath();
         if(path.equals("/Login.jsp") || path.equals("/Join.jsp")){
        	 response.sendRedirect("index.jsp");
         }
     }
     else {
    	 String path = request.getServletPath();
         if( !path.equals("/Login.jsp") && !path.equals("/Join.jsp")){
        	 response.sendRedirect("Login.jsp");
         }
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
        <a class="navbar-brand" href="index.jsp">DataBase App</a>
      </div>
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li class="active"><a href="index.jsp">메인</a></li>
          <li><a href="postpage.jsp">게시판</a></li>
          <li><a href="Friends.jsp">친구</a></li>
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
                <a href="UserInfo.jsp">회원정보 수정</a>
              </li>
            	<li>
            		<a href = "logoutAction.jsp">로그아웃</a>
            	</li>
            </ul>
          </li>
        </ul>

        <% } %>
      </div>
    </nav>