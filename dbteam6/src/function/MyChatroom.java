package function;

import java.util.List;
import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Scanner;

import chatroom.ChatroomDAO;
import chatroom.ChatroomDTO;
import message.MessageDAO;
import message.MessageDTO;
import profile.ProfileDAO;
import profile.ProfileDTO;

public class MyChatroom {
	/*
	 * 채팅방 리스트 보기 -> 수정, 나가기(삭제), 채팅방 들어가기, 생성하기 -> 채팅 쓰기, 나가기
	 */

	private ChatroomDAO dao;
	private ProfileDTO PRdto;

	public MyChatroom(ProfileDTO dto) {
		// ProfileDAO();
		dao = new ChatroomDAO(dto);// 어떤 user의 채팅 방인지.
		PRdto = new ProfileDTO(dto);
	}

	public void showMyChatroomList() {
		System.out.println("나의 채팅방\n");
		List<ChatroomDTO> list = dao.GetChatroomList();
		if (list != null && list.size() > 0) {
			System.out.println("No.\t 채팅방 이름\t 채팅 대상");
			int n = 0;
			for (ChatroomDTO dto : list) {
				n++;
				if(PRdto.getPRid() == dto.getHost_id())
					System.out.printf("%d\t %s %s\n", n,dto.getCRname(),new ProfileDAO().getNickname_PRid(dto.getParticipant_id()));
				else
					System.out.printf("%d\t %s %S\n", n,dto.getCRname(),new ProfileDAO().getNickname_PRid(dto.getHost_id()));
			}
		} else {
			System.out.println("현재 생성된 채팅방이 없습니다.");
		}
	}

	public void DeleteChatroom() {
		if (!dao.hasChatroom()) {
			System.out.println("현재 생성된 채팅방이 없습니다. 먼저 채팅방을 생성해주세요.");

		} else {
			List<ChatroomDTO> list = dao.GetChatroomList();
			System.out.printf("어떤 채팅방을 삭제 하시겠습니까? [1-%d]\n", list.size());
			int input = 0;
			try {
				input = cons.ConsoleDB.scanner.nextInt();
				cons.ConsoleDB.scanner.nextLine();
				if (input < 1 && input > list.size())
					throw new InputMismatchException();
			} catch (InputMismatchException e) {
				System.out.println("잘못된 입력 값입니다.");
			}

			ChatroomDTO dto = list.get(input - 1);
			if (dao.DeleteChatroom(dto))
				System.out.printf("채팅방 %s가 정상적으로 삭제 되었습니다\n\n\n\n", input);
			else
				System.out.printf("채팅방 %s를 삭제하지 못했습니다.\n\n\n\n", input);
			// 메인으로 가기 추가
		}
	}

	public void UpdateChatroom() {
		if (!dao.hasChatroom()) {
			System.out.println("현재 생성된 채팅방이 없습니다. 먼저 채팅방을 생성해주세요.");
		} else {
			List<ChatroomDTO> list = dao.GetChatroomList();
			System.out.printf("어떤 채팅방의 이름을 수정 하시겠습니까? [1-%d]\n", list.size());
			String input_up_name = "";
			int input = 0;
			try {
				input = cons.ConsoleDB.scanner.nextInt();
				cons.ConsoleDB.scanner.nextLine();
				if (input < 1 && input > list.size())
					throw new InputMismatchException();
			} catch (InputMismatchException e) {
				System.out.println("잘못된 입력 값입니다.");
			}

			System.out.println("채팅방의 이름을 입력해 주세요 (# 공백시 본래 이름 유지)");
			input_up_name = cons.ConsoleDB.scanner.nextLine().trim();
			ChatroomDTO dto = list.get(input - 1);
			;
			if (!input_up_name.equals("")) {
				if (dao.UpdateChatroom(dto, input_up_name)) // 필요시 수정//
					System.out.println("채팅방의 이름을 성공적으로 수정 하였습니다.\n\n\n");
				else
					System.out.println("채팅방의 이름을 수정하지 못하였습니다.\n\n\n");
			} else
				System.out.println("채팅방의 본래 이름을 유지 합니다.\n\n\n");
			// 메인으로 가기 추가
		}
	}

	public void CreateChatroom() {
		System.out.println("새로운 채팅방의 정보를 입력 해주세요.");
		System.out.println("채팅방 이름");
		String name = "";
		try {
			name = cons.ConsoleDB.scanner.nextLine();
			if (dao.hasChatroomName(name))
				throw new InputMismatchException();
			else if (name.equals(""))
				throw new Exception("공백을 채팅방의 이름으로 할 수 없습니다.");
		} catch (InputMismatchException e) {
			System.out.println("중복된 이름입니다.");
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		System.out.println("채팅할 상대방의 Nickname을 입력해주세요.");
		String Nickname = "";
		try {
			Nickname = cons.ConsoleDB.scanner.nextLine().trim();
			if (dao.hasChatroomName(name))
				throw new InputMismatchException();
		} catch (InputMismatchException e) {
			System.out.println("해당 사용자의 Nickname이 존재하지 않습니다.");
		}

		System.out.println("위의 정보로 채팅방을 생성 하시겠습니까? (Y/N)");
		String input = cons.ConsoleDB.scanner.nextLine().trim();
		if (input.equalsIgnoreCase("y")) {
			ChatroomDTO dto = new ChatroomDTO(PRdto.getPRid(), Nickname, name);
			if (dao.CreateChatroom(dto))
				System.out.println("채팅방이 성공적으로 생성되었습니다.\n\n\n");
			else
				System.out.println("채팅방을 생성하지 못하였습니다.\n\n\n");
		} else
			System.out.println("채팅방 생성 작업을 취소 하였습니다.\n\n\n");
		// 메인으로 가기 이전 화면으로 가기 추가
	}

	public void EnterChatroom() {
		if (!dao.hasChatroom()) {
			System.out.println("현재 생성된 채팅방이 없습니다. 먼저 채팅방을 생성해주세요.");
		} else {
			List<ChatroomDTO> list = dao.GetChatroomList();
			System.out.printf("어떤 채팅방으로 들어가시겠습니까? [1-%d]\n", list.size());

			int input = 0;
			try {
				input = cons.ConsoleDB.scanner.nextInt();
				cons.ConsoleDB.scanner.nextLine();
				if (input < 1 && input > list.size())
					throw new InputMismatchException();
			} catch (InputMismatchException e) {
				System.out.println("잘못된 입력 값입니다.");
			}

			ChatroomDTO dto = list.get(input - 1);
			System.out.printf("%s 채팅방 \n\n", dto.getCRname());
			MessageDAO Mdao = new MessageDAO(dto, PRdto);
			List<MessageDTO> Mlist = Mdao.getMessageList();
			for (MessageDTO Mdto : Mlist) {
				Mdto.getInfo(PRdto.getPRid());
			} // 수정? 최근 5개 10개
			List<MessageDTO> NewReplyMlist = Mdao.getNewReplyMessageList(null);
			while (true) {
				if (Mdao.hasNewReplyMessage(NewReplyMlist)) {
					NewReplyMlist = Mdao.getNewReplyMessageList(NewReplyMlist);
					for (MessageDTO Mdto : Mlist) {
						Mdto.getInfo(PRdto.getPRid());
					}
				}
				System.out.println("메시지 입력(공백 입력시 채팅방 나가기)");
				String message = cons.ConsoleDB.scanner.nextLine();
				if (message.trim().equals(""))
					break;
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				MessageDTO Mdto = new MessageDTO(dto, PRdto.getPRid(), message, timestamp);
				if (Mdao.CreateMessage(Mdto))
					System.out.printf("%20s\n", "전송완료");
				else
					System.out.printf("%20s\n", "전송실패");
			}
			System.out.println("채팅을 종료 합니다.\n\n\n");
			// 메인으로 가기 이전 화면으로 가기 추가
		}

	}
	
	public void CreateChatroomWithPostCreator() {
		Scanner sc = new Scanner(System.in);
		System.out.println("채팅방 이름을 무엇으로 하시습니까?");
		String name = "";
		try {
			name = sc.nextLine();
			if (dao.hasChatroomName(name))
				throw new InputMismatchException();
			else if (name.equals(""))
				throw new Exception("공백을 채팅방의 이름으로 할 수 없습니다.");
		} catch (InputMismatchException e) {
			System.out.println("중복된 이름입니다.");
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		System.out.println("위의 정보로 채팅방을 생성 하시겠습니까? (Y/N)");
		String input = sc.nextLine().trim();
		if (input.equalsIgnoreCase("y")) {
			ChatroomDTO dto = new ChatroomDTO(PRdto.getPRid(), PRdto.getNickname(), name);
			if (dao.CreateChatroom(dto))
				System.out.println("채팅방이 성공적으로 생성되었습니다.\n\n\n");
			else
				System.out.println("채팅방을 생성하지 못하였습니다.\n\n\n");
		} else
			System.out.println("채팅방 생성 작업을 취소 하였습니다.\n\n\n");
	}

}
