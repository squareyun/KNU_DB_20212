package profile;

import java.io.IOException;
import java.sql.Blob;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProfileRegisterServlet")
public class ProfileRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String Fname = request.getParameter("Fname");
		String Lname = request.getParameter("Lname");
		String Email = request.getParameter("Email");
		String Password1 = request.getParameter("Password1");
		String Password2 = request.getParameter("Password2");
		String Phone_num = request.getParameter("Phone_num");
		String Country = request.getParameter("Country");
		String State = request.getParameter("State");
		String City = request.getParameter("City");
		String Street = request.getParameter("Street");
		String Nickname = request.getParameter("Nickname");
		String Gender = request.getParameter("Gender");
		String ProfileImg = null;
		if(Fname == null || Fname.equals("") 
				|| Lname == null || Lname.equals("")
				|| Password1 == null || Password1.equals("") 
				|| Password2 == null || Password2.equals("") 
				|| Phone_num == null || Phone_num.equals("") 
				|| Country == null || Country.equals("") 
				|| State == null || State.equals("")
				|| City == null || City.equals("") 
				|| Street == null || Street.equals("") 
				|| Nickname == null || Nickname.equals("") 
				|| Gender == null || Gender.equals("") 
				) {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","모든 내용을 입력하세요!");
			response.sendRedirect("join.jsp");
			return;		
		}
		if (!Password1.equals(Password2)) {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","비밀번호가 서로 다릅니다.");
			response.sendRedirect("join.jsp");
		}
		int result = new ProfileDAO().register(Fname, Lname, Email, Password2, Phone_num, Country, State,City,Street,Nickname,Gender,ProfileImg);
		System.out.println(result);
		if(result == 1) {
			request.getSession().setAttribute("messageType","성공 메세지");
			request.getSession().setAttribute("messageContent","회원가입에 성공했습니다.");
			response.sendRedirect("Login.jsp");
		}
		else if(result == 0)  {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","존재하는 회원입니다.");
			response.sendRedirect("Join.jsp");
		}
		else {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","데이타 베이스 오류 입니다.");
			response.sendRedirect("Join.jsp");
		}
	}

}
