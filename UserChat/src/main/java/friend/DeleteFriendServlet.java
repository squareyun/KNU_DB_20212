package friend;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteFriendServlet")
public class DeleteFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String friendPRid = request.getParameter("friendPRid");
		String userPRid = request.getParameter("userPRid");
		System.out.println(friendPRid+"+"+userPRid);
		Boolean result = new FriendDAO().DeleteFriend(Integer.parseInt(userPRid), Integer.parseInt(friendPRid));
		int res = (result == true) ? 1 : 0; 
		response.getWriter().write(res+ "");
	}

}
