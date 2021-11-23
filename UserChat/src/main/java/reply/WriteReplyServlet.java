package reply;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WriteReplyServlet")
public class WriteReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String pid = request.getParameter("pid");
		String post_creator_id = request.getParameter("post_creator_id");
		String creator_email = request.getParameter("creator_email");
		String contents = request.getParameter("contents");
		Boolean result = new ReplyDAO().write(Integer.parseInt(pid), Integer.parseInt(post_creator_id), creator_email, contents);
		int res = (result == true) ? 1 : 0; 
		response.getWriter().write(res+ "");
	}

}