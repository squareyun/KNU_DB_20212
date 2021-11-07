package profile;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import cons.ConsoleDB;

public class ProfileDAO {

	public static final Scanner scanner = new Scanner(System.in);
	private Connection conn;
	// private Statement stmt;
	private PreparedStatement pstmt;
	// private CallabelStatement cstmt;
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static final String USER_NAME = "test";
	public static final String USER_PASSWD = "test";

	public ProfileDAO() {
		// TODO Auto-generated constructor stub
	}

	public int getPRid_Nickname(String nickname) {
		// TODO Auto-generated method stub
		int prid = 0;
		try {
			conn = cons.ConsoleDB.conn;
			String sql = "SELECT PRid FROM PROFILE WHERE Nickname = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				prid = rs.getInt("PRid");
			}
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in hasFriend_Nickname Function");
			System.exit(1);
		}
		return prid;
	}

	public boolean hasNickname(String nickname) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = getConnection();
			String sql = "SELECT PRid FROM PROFILE WHERE Nickname = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			int rs = pstmt.executeUpdate();
			if (rs > 0)
				result = true;
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in hasFriend_Nickname Function");
			System.exit(1);
		}
		return result;
	}
	
	public String getNickname_PRid(int prid) {
		String nickname = "";
		try {
			conn = getConnection();
			String sql = "SELECT Nickname FROM PROFILE WHERE PRid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prid);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				nickname = rs.getString("Nickname");
			}
			
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in hasFriend_Nickname Function");
			System.exit(1);
		}
		return nickname;
	}

	public static ProfileDTO getUserByEmail(String userEmail) {

		ProfileDTO user = null;

		PreparedStatement pstmt = ConsoleDB.pstmt;
		Connection conn = ConsoleDB.conn;
		ResultSet rs = ConsoleDB.rs;

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
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("로그인에 실패 하였습니다..");
			System.exit(1);
		}

		return user;
	}

	public static ProfileDTO getUserByPRid(int user_PRid) {

		ProfileDTO user = null;

		PreparedStatement pstmt = ConsoleDB.pstmt;
		Connection conn = ConsoleDB.conn;
		ResultSet rs = ConsoleDB.rs;

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
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("로그인에 실패 하였습니다..");
			System.exit(1);
		}

		return user;
	}

	public static int login() { // 1번 로그인
		System.out.println("---------------------------------");
		System.out.println("이메일과 비밀번호를 입력하세요.");
		System.out.print("Email : ");
		String Email = scanner.nextLine();
		System.out.print("Password : ");
		String Password = scanner.nextLine();

		PreparedStatement pstmt = ConsoleDB.pstmt;
		Connection conn = ConsoleDB.conn;
		ResultSet rs = ConsoleDB.rs;

		int nextStep = 0;

		String SQL = "SELECT Password FROM PROFILE WHERE Email = ?";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, Email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals(Password)) {

					ConsoleDB.currentUser = getUserByEmail(Email);
					System.out.println();
					System.out.println("로그인을 성공하였습니다!");
					System.out.println("메인 페이지로 이동합니다.");
					nextStep = 4;// 로긴성공
					return nextStep;
				}
			}

		} catch (SQLException e) {
			System.out.println();
			System.out.println("로그인에 실패 하였습니다..");
			return nextStep;
		} catch (Exception e) {
			System.out.println();
			System.out.println("로그인에 실패 하였습니다..");
			return nextStep;
		}
		System.out.println();
		System.out.println("로그인에 실패 하였습니다..");

		return nextStep;

	}

	public static void logout() { // 로그아웃
		cons.ConsoleDB.currentUser = null;
		System.out.println();
		System.out.println("로그아웃 되었습니다..");

	}

	public static int register() { // 2번 회원가입

		PreparedStatement pstmt = ConsoleDB.pstmt;
		Connection conn = ConsoleDB.conn;

		System.out.println("---------------------------------");
		System.out.println("순서대로 회원 정보를 입력하세요.");
		System.out.println();

		System.out.print("Fname : ");
		String Fname = scanner.nextLine();
		System.out.print("Lname : ");
		String Lname = scanner.nextLine();
		System.out.print("Email : ");
		String Email = scanner.nextLine();
		System.out.print("Password : ");
		String Password = scanner.nextLine();
		System.out.print("Phone_num ex(010-xxxx-xxxx) : ");
		String Phone_num = scanner.nextLine();
		System.out.print("Country : ");
		String Country = scanner.nextLine();
		System.out.print("State : ");
		String State = scanner.nextLine();
		System.out.print("City : ");
		String City = scanner.nextLine();
		System.out.print("Street : ");
		String Street = scanner.nextLine();
		System.out.print("Nickname : ");
		String Nickname = scanner.nextLine();
		System.out.print("Gender (남자,여자) : ");
		String Gender = scanner.nextLine();

		String ProfileImg = "";

		int nextStep = 0;

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
			pstmt.setInt(5, initRestriction);
			pstmt.setString(6, Phone_num);
			pstmt.setString(7, sdf.format(timeStamp).toString());
			pstmt.setString(8, Country);
			pstmt.setString(9, State);
			pstmt.setString(10, City);
			pstmt.setString(11, Street);
			pstmt.setString(12, Nickname);
			pstmt.setString(13, Gender);
			pstmt.setString(14, ProfileImg);
			pstmt.setInt(15, GeneralUser);

			int res = pstmt.executeUpdate();

			if (res == 1) {
				System.out.println();
				System.out.println(Lname + " " + Fname + "님 회원가입에 성공하였습니다! 로그인을 진행해주세요.");
			} else {
				System.out.println();
				System.out.println("회원가입에 실패 하였습니다..");
				System.out.println("동일한 Email이나 Nickname을 가진 유저가 존재하거나, 양식에 맞지 않게 작성하였습니다.");
			}
		} catch (SQLException e) {
			System.out.println();
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("회원가입에 실패 하였습니다..");
			System.out.println("동일한 Email이나 Nickname을 가진 유저가 존재하거나, 양식에 맞지 않게 작성하였습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextStep;

	}

	public static void updateMyAccountInfo() { // 내 인적사항 보기(6번)의 내장 함수 , 회원정보 수정

		PreparedStatement pstmt = ConsoleDB.pstmt;
		Connection conn = ConsoleDB.conn;
		ProfileDTO currentUser = cons.ConsoleDB.currentUser;

		System.out.println("---------------------------------");
		System.out.println("수정 가능한 목록입니다....");
		System.out.println();
		System.out.println("1. 비밀번호, 2. 핸드폰 번호, 3. 성별, 4. 수정 취소");
		System.out.println();
		System.out.print("입력(번호) : ");
		int chosen;

		String SQL = "";
		String ChangedVal = "";

		try {
			chosen = scanner.nextInt();
			scanner.nextLine();
		} catch (Exception e) {
			scanner.next();
			scanner.nextLine();
			System.out.println("잘못된 입력 값 입니다.");
			return;// myAccountInfo
		}
		if (chosen != 1 && chosen != 2 && chosen != 3 && chosen != 4) {
			System.out.println("잘못된 입력 값 입니다.");
			return;// myAccountInfo
		}
		scanner.nextLine();
		if (chosen == 1) {
			System.out.print("변경할 비밀번호 : ");
			ChangedVal = scanner.nextLine();
			SQL = "UPDATE PROFILE SET PASSWORD = ? WHERE EMAIL = ?";
		} else if (chosen == 2) {
			System.out.print("변경할 핸드폰 번호 : ");
			ChangedVal = scanner.nextLine();
			SQL = "UPDATE PROFILE SET PHONE_NUM = ? WHERE EMAIL = ?";
		} else if (chosen == 3) {
			System.out.print("변경할 성별 (남자,여자) : ");
			ChangedVal = scanner.nextLine();
			SQL = "UPDATE PROFILE SET GENDER = ? WHERE EMAIL = ?";
		} else {
			System.out.println();
			System.out.println("수정 취소 되었습니다.");
			return;
		}
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, ChangedVal);
			pstmt.setString(2, currentUser.getEmail());
			System.out.println();
			pstmt.executeUpdate();
			System.out.println("회원정보 수정이 완료되었습니다.");
			String Email = currentUser.getEmail();
			cons.ConsoleDB.currentUser = null; // user비우고 다시 받기
			cons.ConsoleDB.currentUser = getUserByEmail(Email);

		} catch (SQLException e) {
			System.out.println("회원정보 수정이 실패하였습니다.");
		} catch (Exception e) {
			System.out.println("회원정보 수정이 실패하였습니다.");
		}

		return;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if (conn == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(URL, USER_NAME, USER_PASSWD);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.err.println("Cannot setAutoCommit : " + e.getLocalizedMessage());
				System.err.println("Cannot setAutoCommit : " + e.getMessage());
				System.exit(1);
			}

		}
		return conn;
	}

}
