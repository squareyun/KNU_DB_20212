package message;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import profile.ProfileDAO;

/**
 * Servlet implementation class MessageListServlet
 */
@WebServlet("/MessageListServlet")
public class MessageListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String CRID = request.getParameter("CRID");
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");
		if(CRID == null || CRID.equals("") || fromID == null || fromID.equals("") || toID == null || toID.equals("")
			 || listType == null || listType.equals(""))
			response.getWriter().write("0");
		else
			try {
				response.getWriter().write(getID(CRID, fromID, toID, listType));
			}catch(Exception e){
				System.out.println(e.getMessage().toString());
			}
	}

	private String getID(String CRID, String fromID, String toID, String listType) {
		System.out.println("get ID start");
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		MessageDAO mDAO = new MessageDAO();
		ArrayList<MessageDTO> messageList = mDAO.getMessageListByRecent(CRID, fromID, toID, listType);
		System.out.println(messageList);
		System.out.println(messageList.size());
		if(messageList.size() == 0) return "";
		System.out.println(result.toString());
		for(int i = 0; i < messageList.size(); i++) {
			System.out.println(i);
			result.append("[{\"value\": \"" + new ProfileDAO().getUserByPRid(messageList.get(i).getSender_id()).getProfileIMG() + "\"},");
			result.append("{\"value\": \"" + messageList.get(i).ishost() + "\"},");
			if(i == 0)
				result.append("{\"value\": \"0\"},");
			else
				result.append("{\"value\": \"" + mDAO.getLabel(messageList.get(i-1) , messageList.get(i)) + "\"},");
			result.append("{\"value\": \"" + messageList.get(i).getContents() + "\"},");
			result.append("{\"value\": \"" + messageList.get(i).getCreate_date_str() + "\"}]");
			if(i != messageList.size() - 1) result.append(",");
			System.out.println(result.toString());
		}
		result.append("], \"last\":\"" + messageList.get(messageList.size()- 1).getMid()+"\"}");
		System.out.println(result);
		return result.toString();
	}
}
