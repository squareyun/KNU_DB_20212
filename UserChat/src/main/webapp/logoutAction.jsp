<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <%@ include file = "views/header.jsp" %>
  <body>
    <% session.invalidate(); %>
    <script>
      location.href = "index.jsp";
    </script>
  </body>
</html>
