package chatroom;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteChatroomServlet
 */
@WebServlet("/DeleteChatroomServlet")
public class DeleteChatroomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String chatroomID = request.getParameter("chatroomID");
		
		if(chatroomID == "" || chatroomID == null)
			response.getWriter().write(0+ "");
		else {
			Boolean result = new ChatroomDAO().DeleteChatroom(Integer.parseInt(chatroomID));
			int res = (result == true) ? 1 : 0;
			response.getWriter().write(res+ "");
		}
	}

}
