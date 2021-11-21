package friend;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FriendRequestServlet")
public class FriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String userPrid = request.getParameter("userPrid");
		String Nickname = request.getParameter("FriendNickname");
		Boolean result = new FriendDAO().CreateFriendRequest(Integer.parseInt(userPrid.trim()), Nickname.trim());
		int res = (result == true) ? 1 : 0; 
		response.getWriter().write(res+ "");
	}

}
