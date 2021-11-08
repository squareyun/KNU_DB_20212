package page;

import java.util.List;
import java.util.Scanner;

import post.PostDAO;
import post.PostDTO;

public class PostPage {
	public static int show() {
		Scanner sc = new Scanner(System.in);

		int choosenCid = cons.ConsoleDB.choosenCategory;
		String choosenCname = cons.ConsoleDB.choosenCategoryName;

		System.out.println("---------------------------------");
		System.out.println(choosenCname + " 카테고리 게시물 목록입니다....");
		System.out.println();

		PostDAO dao = new PostDAO();
		List<PostDTO> list = dao.getPostListInCategory(choosenCid);

		System.out.println();
		System.out.println("게시글번호		제목				글작성자		만든 날짜");
		System.out.println("----------------------------------------");

		for (PostDTO dto : list) {
			System.out.printf("%d		%s		%s		%s\n", dto.getPid(), dto.getTitle(), profile.ProfileDAO.getUserByPRid(dto.getCreator_id()).getNickname(), dto.getCreate_date());
		}

		System.out.println("----------------------------------------");
		System.out.println();
		System.out.println("1. 게시물 열람하기");
		System.out.println("2. 게시물 작성하기");
		System.out.println("3. 뒤로 가기");
		System.out.print("입력(번호) : ");

		int choosen;
		int choosen2;

		try {
			choosen = sc.nextInt();
		} catch (Exception e) {
			sc.next();
			System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
			return cons.ConsoleDB.POSTPAGE;
		}

		switch (choosen) {
		case 1:
			System.out.print("입력(열람할 게시물 번호) : ");
			
			try {
				choosen = sc.nextInt();
			} catch (Exception e) {
				sc.next();
				System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
				return cons.ConsoleDB.POSTPAGE;
			}
			
			cons.ConsoleDB.choosenPid = choosen;
			return cons.ConsoleDB.POSTCONTENTPAGE;
		case 2:
			System.out.print("[제목] >> ");
			sc.nextLine();
			String input_title = sc.nextLine();

			System.out.print("[내용] >> ");
			String input_contents = sc.nextLine();
			
			post.PostDAO.makePost(input_title, input_contents);
			
			System.out.println("★★ 게시물이 작성되었습니다. ★★");
			
			return cons.ConsoleDB.POSTPAGE;
			
		case 3:
			return cons.ConsoleDB.CATEGORYPAGE;
		}
		return 0;

	}
}