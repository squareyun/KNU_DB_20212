package profile;


import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import category.CategoryDTO;
import db.dbInfo;

public class ProfileDAO {
	//dao:데이터베이스 접근 객체의 약자로
	//실질적으로 db에서 회원정보 불러오거나 db에 회원정보를 넣을때
	private static Connection conn; //connection db에 접근하게 해주는 객체
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	//private ProfileDTO currentUser = null;
	
	//Oracle에 접속 해주는 부분
	public ProfileDAO() {//생성자 실행될때마다 자동으로 db연결이 이루어질수 있도록함
		
		try {	
			dbInfo db = new dbInfo();
			Class.forName(db.getDriverName());
			conn = DriverManager.getConnection(db.getDbURL(), db.getDbID(), db.getDbPassword());
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
	public static ProfileDTO getUserByEmail(String userEmail) {

		ProfileDTO user = null;
		userEmail.trim();
		String SQL = "SELECT * FROM PROFILE WHERE Email = ?";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userEmail);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String Email = rs.getString("Email");
				String Password = rs.getString("Password");
				String Fname = rs.getString("Fname");
				String Lname = rs.getString("Lname");
				String Gender = rs.getString("Gender");
				String Phone_num = rs.getString("Phone_num");
				String Nickname = rs.getString("Nickname");
				String Country = rs.getString("Country");
				String State = rs.getString("State");
				String City = rs.getString("City");
				String Street = rs.getString("Street");
				String ProfileImg = rs.getString("ProfileImg");
				int Restriction = rs.getInt("Restriction");
				int Role_id = rs.getInt("Role_id");
				int PRid = rs.getInt("PRid");
				Timestamp Create_date = rs.getTimestamp("Create_date");
				user = new ProfileDTO(Fname, Lname, PRid, Email, Password, Restriction, Phone_num, Create_date, Country,
						State, City, Street, Nickname, Gender, ProfileImg, Role_id);
			}

		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		}

		return user;
	}

	public static ProfileDTO getUserByPRid(int user_PRid) {

		ProfileDTO user = null;


		String SQL = "SELECT * FROM PROFILE WHERE prid = ?";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, user_PRid);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				String Email = rs.getString("Email");
				String Password = rs.getString("Password");
				String Fname = rs.getString("Fname");
				String Lname = rs.getString("Lname");
				String Gender = rs.getString("Gender");
				String Phone_num = rs.getString("Phone_num");
				String Nickname = rs.getString("Nickname");
				String Country = rs.getString("Country");
				String State = rs.getString("State");
				String City = rs.getString("City");
				String Street = rs.getString("Street");
				String ProfileImg = rs.getString("ProfileImg");
				int Restriction = rs.getInt("Restriction");
				int Role_id = rs.getInt("Role_id");
				int PRid = rs.getInt("PRid");
				Timestamp Create_date = rs.getTimestamp("Create_date");
				user = new ProfileDTO(Fname, Lname, PRid, Email, Password, Restriction, Phone_num, Create_date, Country,
						State, City, Street, Nickname, Gender, ProfileImg, Role_id);
			}

		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		}

		return user;
	}
	public static ProfileDTO getUserByNickname(String user_Nickname) {

		ProfileDTO user = null;

		String SQL = "SELECT * FROM PROFILE WHERE Nickname = ?";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user_Nickname);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				String Email = rs.getString("Email");
				String Password = rs.getString("Password");
				String Fname = rs.getString("Fname");
				String Lname = rs.getString("Lname");
				String Gender = rs.getString("Gender");
				String Phone_num = rs.getString("Phone_num");
				String Nickname = rs.getString("Nickname");
				String Country = rs.getString("Country");
				String State = rs.getString("State");
				String City = rs.getString("City");
				String Street = rs.getString("Street");
				String ProfileImg = rs.getString("ProfileImg");
				int Restriction = rs.getInt("Restriction");
				int Role_id = rs.getInt("Role_id");
				int PRid = rs.getInt("PRid");
				Timestamp Create_date = rs.getTimestamp("Create_date");
				user = new ProfileDTO(Fname, Lname, PRid, Email, Password, Restriction, Phone_num, Create_date, Country,
						State, City, Street, Nickname, Gender, ProfileImg, Role_id);
			}

		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		}

		return user;
	}
	 public int login(String Email, String Password) {
		 String SQL = "SELECT * FROM PROFILE WHERE Email = ? ";
		 System.out.println(Email+Password);
		 try {
			 //pstmt: prepared statement 정해진 sql문장을 db에 삽입하는 형식으로 인스턴스가져옴
			 pstmt = conn.prepareStatement(SQL);
			 //sql인젝션 같은 해킹기법을 방해하는것 pstmt를 이용해 하나의 문장을 미리 준비해서 (물음표사용)
			 //물음표에 해당하는 내용을 유저 아이디로, 매개변수로 이용 1)존재하는지 2)비번 무엇인지
			 //currentUser = getUserByEmail(Email);
			 pstmt.setString(1, Email.trim());
			 //rs:result set에 결과보관
			 ResultSet rs = pstmt.executeQuery();
			 //결과가 존재한다면 실행
			 if(rs.next()) {
				 //패스워드 일치한다면 실행
				 System.out.println(rs.getString("Password"));
				 if(rs.getString("Password").equals(Password)) {
					if(rs.getInt("ROLE_ID")!=3) {
						return 1;//로긴성공
					}else {
						return 3;//제한된 유
					}
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
	 
	 public int registerEmailCheck(String Email) {
		 
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
	 public int registerNicknameCheck(String Nickname) {
		 
		 String SQL = "SELECT Password FROM PROFILE WHERE Nickname = ?";
		 
		 try {
			 //pstmt: prepared statement 정해진 sql문장을 db에 삽입하는 형식으로 인스턴스가져옴
			 pstmt = conn.prepareStatement(SQL);
			 //sql인젝션 같은 해킹기법을 방해하는것 pstmt를 이용해 하나의 문장을 미리 준비해서 (물음표사용)
			 //물음표에 해당하는 내용을 유저 아이디로, 매개변수로 이용 1)존재하는지 2)비번 무엇인지
			 pstmt.setString(1, Nickname);
			 //rs:result set에 결과보관
			 rs = pstmt.executeQuery();
			 //결과가 존재한다면 실행
			 if(rs.next() || Nickname.equals("")) {
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
	 public int update(String Email,String Fname, String Lname, String Password, String Phone_num, String Country, String State ,String City, String Street, String Gender,String profileImg)  {

		 
		 String SQL = "UPDATE Profile Set Fname = ?, Lname = ?, Password = ?, Phone_num = ?,Country = ? , State = ?, City = ?, Street = ?,Gender = ?,profileImg = ? where Email = ?";
		 
		 try {
			 
			 pstmt = conn.prepareStatement(SQL); 
			
			 pstmt.setString(1, Fname.trim()); 
			 pstmt.setString(2, Lname.trim()); 
			 pstmt.setString(3, Password.trim()); 
			 pstmt.setString(4, Phone_num.trim());
			 pstmt.setString(5, Country.trim()); 
			 pstmt.setString(6, State.trim()); 
			 pstmt.setString(7, City.trim()); 
			 pstmt.setString(8, Street.trim()); 
			 pstmt.setString(9, Gender.trim());
			 pstmt.setString(10, profileImg.trim());
			 pstmt.setString(11, Email.trim()); 

			 int res = pstmt.executeUpdate(); 
			 
			 if(res==1){ 
				 System.out.println("유저 업데이트 완료");
				 return 1; //업데이트 완료
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
	 
	 public boolean giveRestriction(int prid, int change) {
			boolean result = false;

			try {
				String sql = "UPDATE PROFILE SET ROLE_ID = ?  WHERE PRID = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, change);
				pstmt.setInt(2, prid);
				int rs = pstmt.executeUpdate();
				if (rs > 0)
					result = true;
			} catch (SQLException e) {
				e.getStackTrace();
				System.out.println(e.getMessage());
				System.out.println(e.getLocalizedMessage());
				result = false;
			} catch (Exception e) {
				e.getStackTrace();
				System.out.println(e.getMessage());
				System.out.println(e.getLocalizedMessage());
				System.out.println("Error in category.updatePostCategory Function");
				result = false;
			}
			return result;
	}
	
	 public List<ProfileDTO> getSearchProfile(String nickname, String gender, String country, String state, String city, String street, String fname, String lname, String startdate, String enddate) {
			List<ProfileDTO> list = new ArrayList<>();
			boolean checkAnd = false;

			try {
				String sql = "select * from profile where ";
				if(!nickname.equals("")) {
					sql += " nickname like '%" + nickname + "%' ";
					checkAnd = true;
				}
				if(!gender.equals("") && !gender.equals("무관")) {
					if(checkAnd) sql += " and ";
					sql += " gender = '" + gender + "' ";
					checkAnd = true;
				}
				if(!country.equals("")) {
					if(checkAnd) sql += " and ";
					sql += " country like '%" + country + "%' ";
					checkAnd = true;
				}
				if(!state.equals("")) {
					if(checkAnd) sql += " and ";
					sql += " state like '%" + state + "%' ";
					checkAnd = true;
				}
				if(!city.equals("")) {
					if(checkAnd) sql += " and ";
					sql += " city like '%" + city + "%' ";
					checkAnd = true;
				}
				if(!street.equals("")) {
					if(checkAnd) sql += " and ";
					sql += " street like '%" + street + "%' ";
					checkAnd = true;
				}
				if(!fname.equals("")) {
					if(checkAnd) sql += " and ";
					sql += " fname like '%" + fname + "%' ";
					checkAnd = true;
				}
				if(!lname.equals("")) {
					if(checkAnd) sql += " and ";
					sql += " lname like '%" + lname + "%' ";
					checkAnd = true;
				}
				if(!startdate.equals("")) {
					if(checkAnd) sql += " and ";
					sql += " create_date >= '" + startdate + "' ";
					checkAnd = true;
				}
				if(!enddate.equals("")) {
					if(checkAnd) sql += " and ";
					sql += " create_date <= '" + enddate + "' ";
					checkAnd = true;
				}
				if(nickname.equals("") && gender.equals("무관") && country.equals("") && state.equals("") && city.equals("") && street.equals("") && fname.equals("") && lname.equals("") && startdate.equals("") && enddate.equals("")) {
					sql = "select * from profile";
				}
				
				System.out.println(sql);
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					ProfileDTO profile = new ProfileDTO();
					profile.setFname(rs.getString(1));
					profile.setLname(rs.getString(2));
					profile.setPRid(rs.getInt(3));
					profile.setEmail(rs.getString(4));
					profile.setRestrction(rs.getInt(6));
					profile.setPhone_num(rs.getString(7));
					profile.setCreate_date(rs.getTimestamp("Create_date"));
					profile.setCountry(rs.getString(9));
					profile.setState(rs.getString(10));
					profile.setCity(rs.getString(11));
					profile.setStreet(rs.getString(12));
					profile.setNickname(rs.getString(13));
					profile.setGender(rs.getString(14));
					profile.setRole_id(rs.getInt(16));
					
					list.add(profile);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return list;
		}
	 
	 public List<ProfileDTO> getRestrictedProfile() {
		 List<ProfileDTO> list = new ArrayList<>();

			try {
				String sql = "select * from profile where role_id = 3";
				
				System.out.println(sql);
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					ProfileDTO profile = new ProfileDTO();
					profile.setFname(rs.getString(1));
					profile.setLname(rs.getString(2));
					profile.setPRid(rs.getInt(3));
					profile.setEmail(rs.getString(4));
					profile.setPhone_num(rs.getString(7));
					profile.setCreate_date(rs.getTimestamp("Create_date"));
					profile.setCountry(rs.getString(9));
					profile.setState(rs.getString(10));
					profile.setCity(rs.getString(11));
					profile.setStreet(rs.getString(12));
					profile.setNickname(rs.getString(13));
					profile.setGender(rs.getString(14));
					
					list.add(profile);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return list;
		}
	
 }

