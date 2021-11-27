package post;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import profile.ProfileDAO;

/**
 * Servlet implementation class IndexUpdateServlet
 */
@WebServlet("/IndexUpdateServlet")
public class IndexUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String Contents = request.getParameter("Contents");
		String latestTime = request.getParameter("latestTime");
		IndexDAO indexDAO = new IndexDAO();
		if(indexDAO.isUpdated(latestTime) == false) {
			response.getWriter().write( indexDAO.update(Contents) + "");
		}else {
			response.getWriter().write( -1 + "");
		}
		//response.getWriter().write( indexDAO.update(Contents) + "");
		
	}

}
