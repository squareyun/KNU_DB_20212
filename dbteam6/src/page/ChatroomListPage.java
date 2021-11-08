package page;

import java.util.InputMismatchException;

import cons.ConsoleDB;
import function.MyChatroom;

public class ChatroomListPage {
	public static int show() {
		MyChatroom cr = new MyChatroom(cons.ConsoleDB.currentUser);
		
		System.out.println("---------------------------------");
		cr.showMyChatroomList();
		System.out.println();
		System.out.println("---------------------------------");
		System.out.println();
		System.out.println("1.채팅방 들어가기");
		System.out.println("2.채팅방 이름 수정");
		System.out.println("3.채팅방 삭제하기");
		System.out.println("4.채팅방 생성하기");
		System.out.println("5.메인으로 돌아가기");
		System.out.println();
		System.out.print("입력(번호) : ");
		int input = 0;
		while(true) {
			try {
				String input2 = cons.ConsoleDB.scanner.nextLine();
				input = Integer.parseInt(input2);
				if (input < 1 || input > 5)
					throw new InputMismatchException();
				else
					break;
			} catch (InputMismatchException e) {
				System.out.println("잘못된 값입니다.");
				return cons.ConsoleDB.CHATROOMLISTPAGE;
			} catch (Exception e) {
				System.out.println("잘못된 값입니다.");
				return cons.ConsoleDB.CHATROOMLISTPAGE;
			}
		}
		switch (input) {
		case 1:
			cr.EnterChatroom();
			return cons.ConsoleDB.CHATROOMLISTPAGE;
		case 2:
			cr.UpdateChatroom();
			return cons.ConsoleDB.CHATROOMLISTPAGE;
		case 3:
			cr.DeleteChatroom();
			return cons.ConsoleDB.CHATROOMLISTPAGE;
		case 4:
			cr.CreateChatroom();
			return cons.ConsoleDB.CHATROOMLISTPAGE;
		case 5:
			return cons.ConsoleDB.mainPage;
		default:
			System.out.println("Error Occured\n");
			return cons.ConsoleDB.mainPage;
		}
	}
}
