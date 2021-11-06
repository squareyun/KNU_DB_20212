package cons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import page.PostContentPage;
import page.PostPage;
import profile.ProfileDTO;

public class ConsoleDB {

	public static final String URL = "jdbc:oracle:thin:@192.168.219.105:1521:orcl";
	public static final String USER_NAME = "userchat";
	public static final String USER_PASSWD = "1234";
	public static Connection conn = null; // Connection object
	public static Statement stmt = null; // Statement object
	public static PreparedStatement pstmt;
	public static ResultSet rs;

	public static final Scanner scanner = new Scanner(System.in);

	public static ProfileDTO currentUser;

	public static int choosenCategory;
	public static String choosenCategoryName = "";
	public static int choosenPid;

	public static int accountPage = 0;
	public static int mainPage = 4; // 메인페이지
	public static int myAccountInfoPage = 5;
	public static int CATEGORYPAGE = 6;
	public static int POSTPAGE = 7;
	public static int POSTCONTENTPAGE = 8;

	public ConsoleDB() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver Loading success!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Cannot setAutoCommit : " + e.getLocalizedMessage());
			System.err.println("Cannot setAutoCommit : " + e.getMessage());
			System.exit(1);
		}

		try {
			conn = DriverManager.getConnection(URL, USER_NAME, USER_PASSWD);
			stmt = conn.createStatement();
			System.out.println("Oracle Connected!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
			System.exit(1);
		}

	}

	public static int accountPage() { // 0번 계정 페이지

		System.out.println("---------------------------------");
		System.out.println("로그인 혹은 회원가입을 진행하십시오.");
		System.out.println();
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("3. 프로그램 종료");
		System.out.println();
		System.out.print("입력(번호) : ");
		int nextStep;
		try {
			nextStep = scanner.nextInt();
		} catch (Exception e) {
			System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
			scanner.next();
			return accountPage;
		}
		if (nextStep != 1 && nextStep != 2 && nextStep != 3) {
			System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
			return accountPage;
		}
		return nextStep;
	}

	public static void programExit() { // 3번 프로그램 종료
		System.out.println("---------------------------------");
		System.out.println("프로그램이 종료 됩니다 ....");
		System.exit(0);
	}

	public static int mainPage() { // 4번 메인 페이지
		System.out.println("---------------------------------");
		System.out.println("메인 페이지 입니다 ...");
		System.out.println();
		System.out.println("1. 내 정보 페이지"); // 5
		System.out.println("2. 나의 채팅방 페이지"); //
		System.out.println("3. 게시판 페이지");//
		System.out.println("4. 로그아웃 하기");// 0
		System.out.println();
		System.out.print("입력(번호) : ");
		int nextStep;
		try {
			nextStep = scanner.nextInt();
		} catch (Exception e) {
			scanner.next();
			System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
			return accountPage;
		}
		if (nextStep != 1 && nextStep != 2 && nextStep != 3 && nextStep != 4) {
			System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
			return mainPage;// 메인페이지로
		}
		if (nextStep == 1) {
			return 5;
		} else if (nextStep == 2) {
			return mainPage;
		} else if (nextStep == 3) {
			return CATEGORYPAGE;
		} else { // 로그아웃
			currentUser = null;
			System.out.println();
			System.out.println("로그아웃 되었습니다..");
			return accountPage;
		}
	}

	public static int AboutMePage() { // 5번 내 정보 페이지
		System.out.println("---------------------------------");
		System.out.println("내 정보 열람 페이지 입니다 ...");
		System.out.println();
		System.out.println("1. 내 인적사항 보기"); // 6
		System.out.println("2. 메인 페이지로 돌아가기");// 4
		System.out.println();
		System.out.print("입력(번호) : ");
		int nextStep;
		try {
			nextStep = scanner.nextInt();
		} catch (Exception e) {
			scanner.next();
			System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
			return accountPage;
		}
		if (nextStep != 1 && nextStep != 2 && nextStep != 3 && nextStep != 4) {
			System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
			return mainPage;// 메인페이지로
		}
		if (nextStep == 1) {
			return 6;
		} else {
			return mainPage;// 메인페이지
		}

	}

	public static int myAccountInfoPage() { // 5번 내 인적사항 보기

		ProfileDTO currentUser = cons.ConsoleDB.currentUser;

		System.out.println("---------------------------------");
		System.out.println("내 인적 사항 입니다 ...");
		System.out.println();

		if (currentUser.getRole_id() == 1)
			System.out.println("** 일반 유저입니다 **");
		else if (currentUser.getRole_id() == 2)
			System.out.println("** 관리자 계정입니다 **");
		else
			System.out.println("** 사용 금지된 계정입니다 **");

		System.out.println();
		System.out.println(" Email" + ": " + currentUser.getEmail());
		System.out.println(" 이름" + ": " + currentUser.getLname() + " " + currentUser.getFname());
		System.out.println(" 성별" + ": " + currentUser.getGender());
		System.out.println(" 핸드폰 번호" + ": " + currentUser.getPhone_num());
		System.out.println(" 닉네임 " + ": " + currentUser.getNickname());
		System.out.println(" 주소 " + ": " + currentUser.getCountry() + " " + currentUser.getState() + " "
				+ currentUser.getCity() + " " + currentUser.getStreet());
		System.out.println(" 회원 가입일 " + ": " + currentUser.getCreate_date().toString().split(" ")[0]);
		System.out.println(" 경고 누적 횟수 " + ": " + (currentUser.getRestrction() - 1) + "번");
		System.out.println();

		System.out.println();
		System.out.println("1. 수정하기");
		System.out.println("2. 메인 페이지로 돌아가기");
		System.out.println();
		System.out.print("입력(번호) : ");
		int nextStep;
		try {
			nextStep = scanner.nextInt();
		} catch (Exception e) {
			scanner.next();
			System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
			return 6;
		}
		if (nextStep != 1 && nextStep != 2 && nextStep != 3) {
			System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
			return 6;// myAccountInfo
		}
		if (nextStep == 1) {
			profile.ProfileDAO.updateMyAccountInfo();
			return myAccountInfoPage;// myAccountInfo
		} else {
			return cons.ConsoleDB.mainPage;// 메인페이지
		}
	}

	public static void main(String[] args) {

		new ConsoleDB(); // DB 연결

		int nextStep = accountPage();

		while (true) {

			switch (nextStep) {
			case 0 : // accountPage
				nextStep = accountPage();
				break;
				
			case 1 : // accountPage -> login()
				nextStep = profile.ProfileDAO.login();
				break;
				
			case 2 : // accountPage -> register()
				nextStep = profile.ProfileDAO.register();
				break;
				
			case 3 : // programExit
				programExit();
				break;
				
			case 4 : // mainPage
				nextStep = mainPage();
				break;
				
			case 5 : // mainPage -> 내 정보 보기 페이지
				nextStep = myAccountInfoPage();
				break;

			case 6: // mainPage --> 카테고리 페이지
				nextStep = page.CategoryPage.show();
				break;

			case 7: // mainPage --> 카테고리 페이지 --> 게시판 페이지
				nextStep = PostPage.show();
				break;

			case 8: // mainPage --> 카테고리 페이지 --> 게시판 페이지 --> 게시판 내용
				nextStep = PostContentPage.show();
				break;

			default:
				break;

			}
		}

	}

}
