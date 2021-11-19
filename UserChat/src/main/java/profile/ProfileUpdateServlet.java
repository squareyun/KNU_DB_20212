package profile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@MultipartConfig(
		//1024byte = 1KB이다. 즉 1024*1024는 1MB. 
		//location = "", //  default 디스크 위치
		fileSizeThreshold = 1024*1024, // 1MB를 넘어서면 서버의 Disk 에 임시저장 하는 Disk 접근 방식으로 사용. 서버 RAM 과부하 방지
		maxFileSize = 1024*1024 *50, // 파일이 계속적으로 무한정 커지면 서버가 마비됨. 이를 방지하기위해 50MB를 파일의 하나의 크기로 MAX를 지정해둠.
		maxRequestSize = 1024*1024*50*5 // 파일이 여러개 일시 250MB까지만 수용함.
)
@WebServlet("/ProfileUpdateServlet")
public class ProfileUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		
		String Fname = request.getParameter("Fname");
		String Lname = request.getParameter("Lname");
		String Password = request.getParameter("Password");
		String Phone_num = request.getParameter("Phone_num");
		String Country = request.getParameter("Country");
		String State = request.getParameter("State");
		String City = request.getParameter("City");
		String Street = request.getParameter("Street");
		String Gender = request.getParameter("Gender");
		String Email = request.getParameter("Email");
		String ProfileImg = request.getParameter("ProfileImg");
		
		
//		Part filePart = request.getPart("ProfileImgFile");
//		
//		String fileName = filePart.getSubmittedFileName();
//		InputStream fis = filePart.getInputStream();
//		
//		System.out.println(Fname+Lname+Password+Phone_num+Country+State+City+Street+Gender+Email);
//		System.out.println(filePart);
//		
//		String realPath = request.getServletContext().getRealPath("/upload");//@MultipartConfig 에서 언급한 서버 Disk의 절대경로 얻는법
//		String filePath = realPath + File.pathSeparator + fileName;
//		FileOutputStream fos = new FileOutputStream(filePath);
//		System.out.println(filePath);
//		int size = 0;
//		byte [] buf = new byte[1024];
//		while((size = fis.read(buf)) != -1) {// 반환값이 정수형인 이유. read의 마지막을 읽었을 때 -1이라는 정수형을 반환
//			fos.write(buf,0,size); 
//		}
//		fos.close();
//		fis.close();
//		
//		File profileImg = new File(filePath);
		
		
		if(Fname == null || Fname.equals("") 
				|| Lname == null || Lname.equals("")
				|| Password == null || Password.equals("") 
				|| Phone_num == null || Phone_num.equals("") 
				|| Country == null || Country.equals("") 
				|| State == null || State.equals("")
				|| City == null || City.equals("") 
				|| Street == null || Street.equals("") 
				|| Gender == null || Gender.equals("") 
				) {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","모든 내용을 입력하세요!");
			response.sendRedirect("UserInfo.jsp");
			return;		
		}
		
		int result = new ProfileDAO().update(Email,Fname,Lname,Password,Phone_num,Country,State,City,Street,Gender,ProfileImg);
		if(result == 1) {
			request.getSession().setAttribute("messageType","성공 메세지");
			request.getSession().setAttribute("messageContent","회원수정에 성공했습니다.");
			response.sendRedirect("UserInfo.jsp");
		}
		else if(result == 0)  {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","회원수정에 실패하였습니다..");
			response.sendRedirect("UserInfo.jsp");
		}
		else {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent","데이타 베이스 오류 입니다.");
			response.sendRedirect("UserInfo.jsp");
		}
	}

}
