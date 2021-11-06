package dbteam6;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PostContent {

	public static void show(int input_pid, Connection conn, Statement stmt) {
		Scanner sc = new Scanner(System.in);
		ResultSet rs = null;

		while (true) {
			System.out.println("<<<<<<<<<<게시물 정보>>>>>>>>>>");
			System.out.println();

			try {
				String sql = "select nickname from profile where prid = (select creator_id from post where pid = "
						+ input_pid + ")";
				String post_creator_nickname = "";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					post_creator_nickname = rs.getString(1);
					System.out.printf("[작성자] %s\n", post_creator_nickname);
				}
				rs.close();

				sql = "select * from post where pid = " + input_pid;
				rs = stmt.executeQuery(sql);
				int post_creator_id = 0;
				while (rs.next()) {
					post_creator_id = rs.getInt(2);
					System.out.printf("[제목] %s\n", rs.getString(4));
					System.out.printf("[작성 날짜] %s\n", rs.getTimestamp(5));
					System.out.printf("[내용]\n %s\n\n", rs.getString(3));
				}
				rs.close();

				System.out.println("<<<<<<<<<<댓글 정보>>>>>>>>>>");
				sql = "select p.nickname, r.contents, r.create_date " + "from reply r, profile p where r.post_id = "
						+ input_pid + " and p.prid = r.post_creator_id";
				rs = stmt.executeQuery(sql);
				System.out.println("작성자		내용				날짜");
				System.out.println("----------------------------------------");

				while (rs.next()) {
					System.out.printf("%s		%s		%s\n", rs.getString(1), rs.getString(2), rs.getTimestamp(3));
				}
				rs.close();

				System.out.println("----------------------------------------");
				System.out.println("1	> 댓글 달기");
				System.out.println("2	> 글쓴이와 채팅방 만들기");
				System.out.println("3	> 글쓴이와 친구 추가");
				System.out.println("-1  > 뒤로 가기");

				System.out.print("input>> ");
				int input = sc.nextInt();

				if (input == -1) {
					break;
				}

				switch (input) {
				case 1:
					System.out.println("댓글을 작성하세요. 엔터를 누르면 등록됩니다.");
					sc.nextLine();
					String input_reply = sc.nextLine();
					Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					sql = String.format(
							"insert into reply values(10, %d, %d, %d, '%s', to_date('%s', 'yyyy-mm-dd hh24:mi:ss'))",
							input_pid, post_creator_id, 1, input_reply, sdf.format(timeStamp).toString());
					stmt.executeUpdate(sql);
					break;

				case 2:
					sql = String.format("insert into chat_room values(10, 11, %d, '%s')", post_creator_id, post_creator_nickname + "님과의 채팅방");
					stmt.executeUpdate(sql);
					
					System.out.println(post_creator_nickname + "님과의 채팅방이 생성되었습니다.");
					break;
					
				case 3:
					sql = String.format("insert into friend values(11, %d)", post_creator_id);
					stmt.executeUpdate(sql);
					
					sql = String.format("insert into friend values(%d, 11)", post_creator_id);
					stmt.executeUpdate(sql);
					
					System.out.println(post_creator_nickname + "님과 친구 추가가 되었습니다.");
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
