package category;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdatePostCategoryServlet")
public class UpdatePostCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String pid = request.getParameter("pid");
		String cname = request.getParameter("cname");		
		Boolean result = new CategoryDAO().updatePostCategory(Integer.parseInt(pid), Integer.parseInt(cname));
		int res = (result == true) ? 1 : 0; 
		response.getWriter().write(res+ "");
	}

}
