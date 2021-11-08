package page;

import java.util.InputMismatchException;

import function.Friends;

public class FriendManagementPage {
	public static int show() {
		Friends fr = new Friends(cons.ConsoleDB.currentUser);
		
		System.out.println("---------------------------------");
		fr.ShowFriendsList();
		System.out.println();
		System.out.println("---------------------------------");
		System.out.println();
		System.out.println("1.친구와 채팅방 생성");
		System.out.println("2.닉네임으로 친구추가");
		System.out.println("3.친구추가 요청확인");
		System.out.println("4.친구 삭제");
		System.out.println("5.메인으로 돌아가기");
		System.out.println();
		System.out.print("입력(번호) : ");
		int input = 0;
		try {
			input = cons.ConsoleDB.scanner.nextInt();
			cons.ConsoleDB.scanner.nextLine();
			if (input < 1 || input > 5)
				throw new InputMismatchException();
		} catch (InputMismatchException e) {
			System.out.println("잘못된 값입니다.");
			return cons.ConsoleDB.FRIENDMANAGEMENTPAGE;
		} catch (Exception e) {
			System.out.println("잘못된 값입니다.");
			return cons.ConsoleDB.FRIENDMANAGEMENTPAGE;
		}
		switch (input) {
		case 1:
			fr.CreateChatroom_FR();
			return cons.ConsoleDB.FRIENDMANAGEMENTPAGE;
		case 2:
			fr.CreateFriendRequest_Nickname();
			return cons.ConsoleDB.FRIENDMANAGEMENTPAGE;
		case 3:
			fr.ShowFriendRequestsList();
			fr.AcceptFriendRequest();
			return cons.ConsoleDB.FRIENDMANAGEMENTPAGE;
		case 4:
			fr.DeleteFriend();
			return cons.ConsoleDB.FRIENDMANAGEMENTPAGE;
		case 5:
			return cons.ConsoleDB.mainPage;
		}
		
		return cons.ConsoleDB.mainPage;
	}
}
