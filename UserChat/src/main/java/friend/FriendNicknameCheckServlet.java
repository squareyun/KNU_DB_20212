package friend;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import profile.ProfileDAO;
import profile.ProfileDTO;


@WebServlet("/FriendNicknameCheckServlet")
public class FriendNicknameCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String Nickname = request.getParameter("FriendNickname");
		ProfileDTO user = new ProfileDAO().getUserByNickname(Nickname);
		int res=0; // 실패
		if(user!=null) {
			res = 1; // 존재하는 유저
		} else {
			res = 0; //실패
		}
		response.getWriter().write(res+ "");
	}

}
