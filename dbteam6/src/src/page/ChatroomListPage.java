package page;

import java.util.InputMismatchException;
import java.util.Scanner;

import function.MyChatroom;

public class ChatroomListPage {
	public static int show() {
		MyChatroom cr = new MyChatroom(cons.ConsoleDB.currentUser);
		
		System.out.println("---------------------------------");
		cr.showMyChatroomList();
		System.out.println("1.채팅방 들어가기 2.채팅방 이름 수정 3.채팅방 삭제하기 4.채팅방 생성하기 5.메인으로 돌아가기");
		int input = 0;
		while(true) {
			try {
				String input2 = cons.ConsoleDB.scanner.nextLine();
				input = Integer.parseInt(input2);
				if (input < 1 && input > 5)
					throw new InputMismatchException();
				else
					break;
			} catch (InputMismatchException e) {
				System.out.println("잘못된 값입니다.");
			} catch (Exception e) {
				System.out.println("잘못된 값입니다.");
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
