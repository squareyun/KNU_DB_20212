package chatroom;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateChatroomServlet
 */
@WebServlet("/CreateChatroomServlet")
public class CreateChatroomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String userPrid = request.getParameter("userPrid");
		String op_Prid = request.getParameter("op_Prid");
		String chatroomName = request.getParameter("chatroomName");
		
		if(userPrid == "" || userPrid == null || op_Prid == "" || op_Prid == null || chatroomName == "" || chatroomName == null)
			response.getWriter().write(0+ "");
		else {
			Boolean result = new ChatroomDAO().CreateChatroom(new ChatroomDTO(Integer.parseInt(userPrid),Integer.parseInt(op_Prid),chatroomName));
			int res = (result == true) ? 1 : 0;
			response.getWriter().write(res+ "");
		}
	}
}
