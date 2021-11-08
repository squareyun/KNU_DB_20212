package profile;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProfileLoginServlet
 */
@WebServlet("/ProfileLoginServlet")
public class ProfileLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String Email = request.getParameter("Email");
		String Password = request.getParameter("Password");
		if(Email == null || Email.equals("") || Password == null || Password.equals("") ) {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","모든 내용을 입력하세요");
			response.sendRedirect("Login.jsp");
			return;
		}
		int result = new ProfileDAO().login(Email,Password);
		if (result == 1) {
			System.out.println("로그인 성공 ");
			request.getSession().setAttribute("Email",Email);
			request.getSession().setAttribute("messageType","성공 메세지");
			request.getSession().setAttribute("messageContent","로그인 성공");
			response.sendRedirect("index.jsp");
		}
		else if(result == 2) {
			System.out.println("비밀번호를 확인하세요");
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","비밀번호를 확인하세요");
			response.sendRedirect("Login.jsp");
		}
		else if(result == 0) {
			System.out.println("아이디가 존재하지 않습니다 ");
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","아이디가 존재하지 않습니다");
			response.sendRedirect("Login.jsp");
		}
		else if(result == -1) {
			System.out.println("데이터 베이스 오류가 발생했습니다 ");
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","데이터 베이스 오류가 발생했습니다");
			response.sendRedirect("Login.jsp");
		}
		
	}

}
