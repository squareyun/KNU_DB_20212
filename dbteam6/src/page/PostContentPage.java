package page;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import post.PostDAO;
import post.PostDTO;

public class PostContentPage {

	public static int show() {
		Scanner sc = new Scanner(System.in);

		int input_pid = cons.ConsoleDB.choosenPid;

		PostDTO postInfo = PostDAO.getPostInfo(input_pid);

		System.out.println("---------------------------------");
		System.out.println(input_pid + "번 게시글 정보입니다....");
		System.out.println();

		System.out.printf("[작성자] %s\n", profile.ProfileDAO.getUserByPRid(postInfo.getCreator_id()).getNickname());
		System.out.printf("[제목] %s\n", postInfo.getTitle());
		System.out.printf("[작성 날짜] %s\n", postInfo.getCreate_date());
		System.out.printf("[내용]\n %s\n\n", postInfo.getContents());

//		rs.close();
//
//		System.out.println("<<<<<<<<<<댓글 정보>>>>>>>>>>");
//		sql = "select p.nickname, r.contents, r.create_date " + "from reply r, profile p where r.post_id = " + input_pid
//				+ " and p.prid = r.post_creator_id";
//		rs = stmt.executeQuery(sql);
//		System.out.println("작성자		내용				날짜");
//		System.out.println("----------------------------------------");
//
//		while (rs.next()) {
//			System.out.printf("%s		%s		%s\n", rs.getString(1), rs.getString(2), rs.getTimestamp(3));
//		}
//		rs.close();
//
//		System.out.println("----------------------------------------");
//		System.out.println("1	> 댓글 달기");
//		System.out.println("2	> 글쓴이와 채팅방 만들기");
//		System.out.println("3	> 글쓴이와 친구 추가");
//		System.out.println("-1  > 뒤로 가기");
//
//		System.out.print("input>> ");
//		int input = sc.nextInt();
//
//		if (input == -1) {
//			break;
//		}
//

		System.out.println("---------------------------------");

		// 글 작성자일 때 수정 및 삭제 가능
		if (cons.ConsoleDB.currentUser.getPRid() == postInfo.getCreator_id()) {
			System.out.println("1. 댓글 달기");
			System.out.println("2. 게시글 수정");
			System.out.println("3. 게시글 삭제");
			System.out.println("4. 뒤로 가기");
			System.out.println();
			System.out.print("입력(번호) : ");
			int choosen;
			try {
				choosen = sc.nextInt();
			} catch (Exception e) {
				sc.next();
				System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
				return cons.ConsoleDB.CATEGORYPAGE;
			}

			switch (choosen) {
			case 1:
				// todo
				return cons.ConsoleDB.POSTCONTENTPAGE;
			case 2:
				System.out.println("---------------------------------");
				System.out.println("수정 가능한 목록입니다....");
				System.out.println("1. 제목, 2. 내용");
				System.out.println();
				System.out.print("입력(번호) : ");

				int choosen2;
				try {
					choosen2 = sc.nextInt();
				} catch (Exception e) {
					sc.next();
					System.out.println("잘못된 입력 값 입니다.");
					return cons.ConsoleDB.POSTCONTENTPAGE;
				}

				String updateTitle;
				String updateContents;
				if (choosen2 == 1) {
					System.out.println("수정할 제목을 작성하세요 : ");
					sc.nextLine();
					updateTitle = sc.nextLine();
					post.PostDAO.updatePostTitle(updateTitle, input_pid);
					System.out.println("★★ 게시물이 수정되었습니다. ★★");
					return cons.ConsoleDB.POSTCONTENTPAGE;
				} else if (choosen2 == 2) {
					System.out.println("수정할 내용을 작성하세요 : ");
					sc.nextLine();
					updateContents = sc.nextLine();
					post.PostDAO.updatePostContents(updateContents, input_pid);
					System.out.println("★★ 게시물이 수정되었습니다. ★★");
					return cons.ConsoleDB.POSTCONTENTPAGE;
				} else {
					System.out.println("잘못된 입력 값 입니다.");
					return cons.ConsoleDB.POSTCONTENTPAGE;
				}

			case 3:
				post.PostDAO.deletePost(input_pid);
				System.out.println("★★ 게시물이 삭제되었습니다. ★★");

				return cons.ConsoleDB.POSTPAGE;
			case 4:
				return cons.ConsoleDB.POSTPAGE;
			default:
				System.out.println("다시 입력하세요.");
				return cons.ConsoleDB.POSTCONTENTPAGE;
			}
		}
		// 관리자 일 때
		else if (cons.ConsoleDB.currentUser.getRole_id() == 2) {
			System.out.println("1. 댓글 달기");
			System.out.println("2. 게시글 삭제");
			System.out.println("3. 글쓴이와 채팅방 만들기");
			System.out.println("4. 뒤로 가기");
			System.out.println();
			System.out.print("입력(번호) : ");
			int choosen;
			try {
				choosen = sc.nextInt();
			} catch (Exception e) {
				sc.next();
				System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
				return cons.ConsoleDB.CATEGORYPAGE;
			}

			switch (choosen) {
			case 1:
				// todo
				return cons.ConsoleDB.POSTCONTENTPAGE;

			case 2:
				post.PostDAO.deletePost(input_pid);
				System.out.println("★★ 게시물이 삭제되었습니다. ★★");

				return cons.ConsoleDB.POSTPAGE;

			case 3:
				// todo
				System.out.println("채팅방이 생성되었습니다... 채팅을 하려면 메인페이지에서 채팅방 목록으로 이동하세요.");
				return cons.ConsoleDB.POSTCONTENTPAGE;

			case 4:
				// todo
				return cons.ConsoleDB.POSTPAGE;

			default:
				System.out.println("다시 입력하세요.");
				return cons.ConsoleDB.POSTCONTENTPAGE;
			}
		} else {
			System.out.println("1. 댓글 달기");
			System.out.println("2. 글쓴이와 채팅방 만들기");
			System.out.println("3. 글쓴이와 친구 추가");
			System.out.println("4. 뒤로 가기");
			System.out.println();
			System.out.print("입력(번호) : ");
			int choosen;
			try {
				choosen = sc.nextInt();
			} catch (Exception e) {
				sc.next();
				System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
				return cons.ConsoleDB.CATEGORYPAGE;
			}

			switch (choosen) {
			case 1:
				// todo
				return cons.ConsoleDB.POSTCONTENTPAGE;
			case 2:
				// todo
				System.out.println("채팅방이 생성되었습니다... 채팅을 하려면 메인페이지에서 채팅방 목록으로 이동하세요.");
				return cons.ConsoleDB.POSTCONTENTPAGE;
			case 3:
				// todo
				System.out.println("친구 추가가 되었습니다...");
				return cons.ConsoleDB.POSTCONTENTPAGE;
			case 4:
				// todo
				return cons.ConsoleDB.POSTPAGE;
			default:
				System.out.println("다시 입력하세요.");
				return cons.ConsoleDB.POSTCONTENTPAGE;
			}
		}

//		switch (input) {
//		case 1:
//			System.out.println("댓글을 작성하세요. 엔터를 누르면 등록됩니다.");
//			sc.nextLine();
//			String input_reply = sc.nextLine();
//			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			sql = String.format(
//					"insert into reply values(10, %d, %d, %d, '%s', to_date('%s', 'yyyy-mm-dd hh24:mi:ss'))", input_pid,
//					post_creator_id, 1, input_reply, sdf.format(timeStamp).toString());
//			stmt.executeUpdate(sql);
//			break;
//
//		case 2:
//			sql = String.format("insert into chat_room values(10, 11, %d, '%s')", post_creator_id,
//					post_creator_nickname + "님과의 채팅방");
//			stmt.executeUpdate(sql);
//
//			System.out.println(post_creator_nickname + "님과의 채팅방이 생성되었습니다.");
//			break;
//
//		case 3:
//			sql = String.format("insert into friend values(11, %d)", post_creator_id);
//			stmt.executeUpdate(sql);
//
//			sql = String.format("insert into friend values(%d, 11)", post_creator_id);
//			stmt.executeUpdate(sql);
//
//			System.out.println(post_creator_nickname + "님과 친구 추가가 되었습니다.");
//			break;
//		}

	}

}
