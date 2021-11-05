package profile;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
public class ProfileDAO {
	//dao:데이터베이스 접근 객체의 약자로
	//실질적으로 db에서 회원정보 불러오거나 db에 회원정보를 넣을때
	private static Connection conn; //connection db에 접근하게 해주는 객체
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private static String driverName = "oracle.jdbc.driver.OracleDriver";
	private static String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbID = "userchat";
	private static String dbPassword = "1234";
	//Oracle에 접속 해주는 부분
	public ProfileDAO() {//생성자 실행될때마다 자동으로 db연결이 이루어질수 있도록함
		//ㄴㅇㅁ
		try {
			driverName = "oracle.jdbc.driver.OracleDriver";
			dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
			dbID = "userchat";
			dbPassword = "1234";
			
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			
			System.out.println("DB에 연결 되었습니다.\n");
			
		}catch(ClassNotFoundException e) {
			System.out.println("DB 드라이버 로딩 실패 :" +e.toString());
		}catch(SQLException sqle) {
			System.out.println("DB 접속실패 :"+sqle.toString());
		}catch(Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
		
			
		
	}
	 public int login(String Email, String Password) {
		 String SQL = "SELECT Password FROM PROFILE WHERE Email = ?";
		 
		 try {
			 //pstmt: prepared statement 정해진 sql문장을 db에 삽입하는 형식으로 인스턴스가져옴
			 pstmt = conn.prepareStatement(SQL);
			 //sql인젝션 같은 해킹기법을 방해하는것 pstmt를 이용해 하나의 문장을 미리 준비해서 (물음표사용)
			 //물음표에 해당하는 내용을 유저 아이디로, 매개변수로 이용 1)존재하는지 2)비번 무엇인지
			 pstmt.setString(1, Email);
			 //rs:result set에 결과보관
			 rs = pstmt.executeQuery();
			 //결과가 존재한다면 실행
			 if(rs.next()) {
				 //패스워드 일치한다면 실행
				 if(rs.getString(1).equals(Password)) {
					 return 1;//로긴성공
				 }
				 else
					 return 2;//비번 불일치
			 }
			 return 0;//아이디 없음
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
				System.out.println(e.toString());
			} 
			catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
			}
			
		}
		 
		 return -2;//데이터베이스 오류를 의미
	 }
	 
	 public int registerCheck(String Email) {
		 
		 String SQL = "SELECT Password FROM PROFILE WHERE Email = ?";
		 
		 try {
			 //pstmt: prepared statement 정해진 sql문장을 db에 삽입하는 형식으로 인스턴스가져옴
			 pstmt = conn.prepareStatement(SQL);
			 //sql인젝션 같은 해킹기법을 방해하는것 pstmt를 이용해 하나의 문장을 미리 준비해서 (물음표사용)
			 //물음표에 해당하는 내용을 유저 아이디로, 매개변수로 이용 1)존재하는지 2)비번 무엇인지
			 pstmt.setString(1, Email);
			 //rs:result set에 결과보관
			 rs = pstmt.executeQuery();
			 //결과가 존재한다면 실행
			 if(rs.next() || Email.equals("")) {
					 return 0;//이미 존재하는 회
			 }				 
			 else
				 return 1;// 가입가능 
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
				System.out.println(e.toString());
			} 
			catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
			}
			
		}
		 
		 return -2;//데이터베이스 오류를 의미
	 }
	 
	 public int register(String Fname, String Lname, String Email, String Password, String Phone_num, String Country, String State ,String City, String Street,String Nickname , String Gender,String ProfileImg )  {

		 
		 String SQL = "INSERT INTO PROFILE VALUES(?,?,Profile_SEQ.NEXTVAL,?,?,?,?,TO_DATE(?, 'yyyy-mm-dd'),?,?,?,?,?,?,?,?)";
		 Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 int GeneralUser = 1;
		 int initRestriction = 1;
		 try {
			 
			 pstmt = conn.prepareStatement(SQL); 
			
			 pstmt.setString(1, Fname); 
			 pstmt.setString(2, Lname); 
			 pstmt.setString(3, Email); 
			 pstmt.setString(4, Password); 
			 pstmt.setInt(5,initRestriction); 
			 pstmt.setString(6, Phone_num);
			 pstmt.setString(7, sdf.format(timeStamp).toString()); 
			 pstmt.setString(8, Country); 
			 pstmt.setString(9, State); 
			 pstmt.setString(10, City); 
			 pstmt.setString(11, Street); 
			 pstmt.setString(12, Nickname);
			 pstmt.setString(13, Gender); 
			 pstmt.setString(14, ProfileImg);
			 pstmt.setInt(15,GeneralUser); 
			 
			 int res = pstmt.executeUpdate(); 
			 
			 if(res==1){ 
				 return 1; //가입 가능한 회원 아이디 
			 }
			 else { 
				 return 0; //실패 
			 } 
		  } 
		 catch(Exception e){
			  e.printStackTrace(); 
		  } finally {
				try {
					if(rs!=null) {
						rs.close();
					}
					if(pstmt!=null) {
						pstmt.close();
					}
					if(conn!=null) {
						conn.close();
					}
				} 
				catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.toString());
				}
				
		}
		return -1; // DB 오류 
	 }
 }

