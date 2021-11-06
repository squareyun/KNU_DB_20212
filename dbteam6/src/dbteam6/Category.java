package dbteam6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Category {
	public static final String URL = "jdbc:oracle:thin:@192.168.219.105:1521:orcl";
	public static final String USER_UNIVERSITY ="dbteam6";
	public static final String USER_PASSWD ="comp322";
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER_UNIVERSITY, USER_PASSWD);
//			System.out.println("Connected.");
		} catch(SQLException ex1) {
			ex1.printStackTrace();
			System.err.println("Cannot get a connection: " + ex1.getLocalizedMessage());
			System.err.println("Cannot get a connection: " + ex1.getMessage());
			System.exit(1);
		}
		
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Scanner sc = new Scanner(System.in);
		ResultSet rs = null;
		
		while(true) {
			System.out.println("카테고리를 선택하세요. (뒤로가기는 -1)");
			
			try {
			String sql = "select * from category";
			rs = stmt.executeQuery(sql);
			
			int index = 1;
			while(rs.next()) {
				String cname = rs.getString(1);
				System.out.printf("%d. %s\n", index++, cname);
			}
			rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("입력>> ");
			int input = sc.nextInt();
			
			if(input == -1) {
				break;
			} else {
				Post.show(input, conn, stmt);
			}
		}
		
		System.out.println("프로그램을 종료합니다.");
		sc.close();
		// Release database resources.
		try {
			// Close the Statement object.
			stmt.close(); 
			// Close the Connection object.
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
