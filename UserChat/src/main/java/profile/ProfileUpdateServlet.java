package profile;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ProfileUpdateServlet")
public class ProfileUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		
		String Fname = request.getParameter("Fname");
		String Lname = request.getParameter("Lname");
		String Password = request.getParameter("Password");
		String Phone_num = request.getParameter("Phone_num");
		String Country = request.getParameter("Country");
		String State = request.getParameter("State");
		String City = request.getParameter("City");
		String Street = request.getParameter("Street");
		String Gender = request.getParameter("Gender");
		String Email = request.getParameter("Email");
		
		System.out.println(Fname+Lname+Password+Phone_num+Country+State+City+Street+Gender+Email);
		
		if(Fname == null || Fname.equals("") 
				|| Lname == null || Lname.equals("")
				|| Password == null || Password.equals("") 
				|| Phone_num == null || Phone_num.equals("") 
				|| Country == null || Country.equals("") 
				|| State == null || State.equals("")
				|| City == null || City.equals("") 
				|| Street == null || Street.equals("") 
				|| Gender == null || Gender.equals("") 
				) {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","모든 내용을 입력하세요!");
			response.sendRedirect("UserInfo.jsp");
			return;		
		}
		int result = new ProfileDAO().update(Email,Fname,Lname,Password,Phone_num,Country,State,City,Street,Gender);
		if(result == 1) {
			request.getSession().setAttribute("messageType","성공 메세지");
			request.getSession().setAttribute("messageContent","회원수정에 성공했습니다.");
			response.sendRedirect("UserInfo.jsp");
		}
		else if(result == 0)  {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","회원수정에 실패하였습니다..");
			response.sendRedirect("UserInfo.jsp");
		}
		else {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","데이타 베이스 오류 입니다.");
			response.sendRedirect("UserInfo.jsp");
		}
	}

}
