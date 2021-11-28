package profile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/ProfileSearchServlet")
public class ProfileSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		
		String nickname = request.getParameter("nickname");
		String gender = request.getParameter("gender");
		String country = request.getParameter("country");
		String state = request.getParameter("state");
		String city = request.getParameter("city");
		String street = request.getParameter("street");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		
		/*
		 * System.out.println(nickname); System.out.println(gender);
		 * System.out.println(country); System.out.println(state);
		 * System.out.println(city); System.out.println(street);
		 * System.out.println(fname); System.out.println(lname);
		 * System.out.println(startdate); System.out.println(enddate);
		 */
		
		List<ProfileDTO> result = new ProfileDAO().getSearchProfile(nickname, gender, country, state, city, street, fname, lname, startdate, enddate);
		
		JSONArray ary = new JSONArray();
		for(ProfileDTO user : result) {
			JSONObject userinfo = new JSONObject();
			userinfo.put("fname", user.getFname());
			userinfo.put("lname",user.getLname());
			userinfo.put("prid", user.getPRid());
			userinfo.put("email",user.getEmail());
			userinfo.put("restriction", user.getRestrction());
			userinfo.put("phone_num",URLEncoder.encode(user.getPhone_num(),"UTF-8"));
			userinfo.put("create_date",URLEncoder.encode(user.getCreate_date().toString(),"UTF-8"));
			userinfo.put("country", user.getCountry());
			userinfo.put("state",user.getState());
			userinfo.put("city",user.getCity());
			userinfo.put("street",user.getStreet());
			userinfo.put("nickname",user.getNickname());
			userinfo.put("gender",user.getGender());
			userinfo.put("role_id", user.getRole_id());
			ary.add(userinfo);
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(ary);
		out.flush();
		out.close();
	}

}
