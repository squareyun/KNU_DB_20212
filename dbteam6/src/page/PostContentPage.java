package page;

import java.util.List;
import java.util.Scanner;

import chatroom.ChatroomDAO;
import friend.FriendDAO;
import function.MyChatroom;
import post.PostDAO;
import post.PostDTO;
import reply.ReplyDAO;
import reply.ReplyDTO;

public class PostContentPage {

	public static int show() {
		Scanner sc = new Scanner(System.in);

		int input_pid = cons.ConsoleDB.choosenPid;

		PostDTO postInfo = PostDAO.getPostInfo(input_pid);

		System.out.println("---------------------------------");
		System.out.println(input_pid + "번 게시글 정보입니다....");
		System.out.println();
		
		String post_creator_nicname = profile.ProfileDAO.getUserByPRid(postInfo.getCreator_id()).getNickname();

		System.out.printf("[작성자] %s\n", post_creator_nicname);
		System.out.printf("[제목] %s\n", postInfo.getTitle());
		System.out.printf("[작성 날짜] %s\n", postInfo.getCreate_date());
		System.out.printf("[내용]\n %s\n\n", postInfo.getContents());

		System.out.println("---------------------------------");
		System.out.println("댓글 정보입니다....");
		
		ReplyDAO dao = new ReplyDAO();
		List<ReplyDTO> list = dao.getReplyListInPost(postInfo.getPid());

		System.out.println();
		System.out.println("작성자			내용				날짜");
		System.out.println("---------------------------------");

		for (ReplyDTO dto : list) {
			System.out.printf("%s		%s		%s\n", profile.ProfileDAO.getUserByPRid(dto.getCreator_id()).getNickname(),
					dto.getContents(), dto.getCreate_date());
		}

		System.out.println();
		System.out.println("---------------------------------");

		// 글 작성자일 때 수정 및 삭제 가능
		if (cons.ConsoleDB.currentUser.getPRid() == postInfo.getCreator_id()) {
			System.out.println("1. 댓글 달기");
			System.out.println("2. 게시글 수정");
			System.out.println("3. 게시글 삭제");
			System.out.println("4. 뒤로 가기");
			System.out.println("5. 메인으로 가기");
			System.out.println();
			System.out.print("입력(번호) : ");
			int choosen;
			try {
				choosen = sc.nextInt();
			} catch (Exception e) {
				sc.next();
				System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
				return cons.ConsoleDB.POSTCONTENTPAGE;
			}

			switch (choosen) {
			case 1:
				System.out.println("댓글을 작성하세요. 엔터를 누르면 등록됩니다.");
				sc.nextLine();
				String input_reply = sc.nextLine();
				reply.ReplyDAO.makeReply(input_reply, postInfo);

				System.out.println("★★ 댓글이 작성되었습니다. ★★");
				
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
				
			case 5:
				return cons.ConsoleDB.mainPage;
				
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
			System.out.println("5. 메인으로 가기");
			System.out.println();
			System.out.print("입력(번호) : ");
			int choosen;
			try {
				choosen = sc.nextInt();
			} catch (Exception e) {
				sc.next();
				System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
				return cons.ConsoleDB.POSTCONTENTPAGE;
			}

			switch (choosen) {
			case 1:
				System.out.println("댓글을 작성하세요. 엔터를 누르면 등록됩니다.");
				sc.nextLine();
				String input_reply = sc.nextLine();
				reply.ReplyDAO.makeReply(input_reply, postInfo);

				System.out.println("★★ 댓글이 작성되었습니다. ★★");
				
				return cons.ConsoleDB.POSTCONTENTPAGE;

			case 2:
				post.PostDAO.deletePost(input_pid);
				System.out.println("★★ 게시물이 삭제되었습니다. ★★");

				return cons.ConsoleDB.POSTPAGE;

			case 3:
				MyChatroom cr = new MyChatroom(cons.ConsoleDB.currentUser);
				if(new ChatroomDAO(cons.ConsoleDB.currentUser).hasChatroom(post_creator_nicname))
					System.out.println("이미 글쓴이와의 채팅방이 존재합니다.");
				else
				{
					cr.CreateChatroom(post_creator_nicname);
					System.out.println("채팅을 하려면 메인페이지에서 채팅방 목록으로 이동하세요.");
				}
				return cons.ConsoleDB.POSTCONTENTPAGE;

			case 4:
				return cons.ConsoleDB.POSTPAGE;
				
			case 5:
				return cons.ConsoleDB.mainPage;

			default:
				System.out.println("다시 입력하세요.");
				return cons.ConsoleDB.POSTCONTENTPAGE;
			}
		} else {
			System.out.println("1. 댓글 달기");
			System.out.println("2. 글쓴이와 채팅방 만들기");
			System.out.println("3. 글쓴이와 친구 추가");
			System.out.println("4. 뒤로 가기");
			System.out.println("5. 메인으로 가기");
			System.out.println();
			System.out.print("입력(번호) : ");
			int choosen;
			try {
				choosen = sc.nextInt();
			} catch (Exception e) {
				sc.next();
				System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
				return cons.ConsoleDB.POSTCONTENTPAGE;
			}

			switch (choosen) {
			case 1:
				System.out.println("댓글을 작성하세요. 엔터를 누르면 등록됩니다.");
				sc.nextLine();
				String input_reply = sc.nextLine();
				reply.ReplyDAO.makeReply(input_reply, postInfo);

				System.out.println("★★ 댓글이 작성되었습니다. ★★");
				
				return cons.ConsoleDB.POSTCONTENTPAGE;
				
			case 2:
				MyChatroom cr = new MyChatroom(cons.ConsoleDB.currentUser);
				if(new ChatroomDAO(cons.ConsoleDB.currentUser).hasChatroom(post_creator_nicname))
					System.out.println("이미 글쓴이와의 채팅방이 존재합니다.");
				else
				{
					cr.CreateChatroom(post_creator_nicname);
					System.out.println("채팅을 하려면 메인페이지에서 채팅방 목록으로 이동하세요.");
				}
				return cons.ConsoleDB.POSTCONTENTPAGE;
				
			case 3:
				FriendDAO fr = new FriendDAO(cons.ConsoleDB.currentUser);
				if(fr.hasFriendRequest(post_creator_nicname))
					System.out.println("이미 글쓴이에게 친구요청을 보냈거나 이미 친구 입니다.");
				else {
					fr.CreateFriendRequest(post_creator_nicname);
					System.out.println("★★ 친구추가 신청을 성공적으로 전송하었습니다. ★★");
				}
				return cons.ConsoleDB.POSTCONTENTPAGE;
				
			case 4:
				return cons.ConsoleDB.POSTPAGE;
				
			case 5:
				return cons.ConsoleDB.mainPage;
				
			default:
				System.out.println("다시 입력하세요.");
				return cons.ConsoleDB.POSTCONTENTPAGE;
			}
		}

	}

}