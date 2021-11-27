package profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProfileRestrictionServlet")
public class ProfileRestrictionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String prid = request.getParameter("prid");
		Boolean result = new ProfileDAO().giveRestriction(Integer.parseInt(prid));
		int res = (result == true) ? 1 : 0; 
		response.getWriter().write(res+ "");
	}

}
