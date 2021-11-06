package dbteam6;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Post {

	public static void show(int category, Connection conn, Statement stmt) {
		Scanner sc = new Scanner(System.in);
		ResultSet rs = null;
		
		while(true) {
			System.out.println("<<<<<<<<<<게시글 목록>>>>>>>>>>");
			System.out.println();
			
			try {
				String sql = "select * from post where category_id = " + category;
				rs = stmt.executeQuery(sql);
					
				System.out.println("게시글번호		제목				만든 날짜");
				System.out.println("----------------------------------------");
				
				while(rs.next()) {
					int pid = rs.getInt(1);
					String title = rs.getString(4);
					Date create_date = rs.getTimestamp(5);
					System.out.printf("%d		%s		%s\n", pid, title, create_date);
				}
				rs.close();
				
				System.out.println("----------------------------------------");
				System.out.println("1	> 게시물 보기");
				System.out.println("2	> 게시물 수정");
				System.out.println("-1	> 뒤로 가기");
				
				System.out.print("input>> ");
				int input = sc.nextInt();
					
				if (input == -1) {
					break;
				}

				switch (input) {
				case 1:
					System.out.print("게시물 번호>> ");
					int input_pid = sc.nextInt();
					PostContent.show(input_pid, conn, stmt);
					break;
				case 2:
					System.out.print("[제목] >> ");
					sc.nextLine();
					String input_title = sc.nextLine();
					
					System.out.print("[내용] >> ");
					String input_contents = sc.nextLine();
					
					Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					sql = String.format("insert into post values(1001, 10, '%s', '%s', to_date('%s', 'yyyy-mm-dd hh24:mi:ss'), %d)",
							input_title, input_contents, sdf.format(timeStamp).toString(), category);
					stmt.executeUpdate(sql);
					break;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
