package reply;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateReplyServlet")
public class UpdateReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String rid = request.getParameter("rid");
		String content = request.getParameter("content");
		Boolean result = new ReplyDAO().update(Integer.parseInt(rid), content);
		int res = (result == true) ? 1 : 0; 
		response.getWriter().write(res+ "");
	}

}