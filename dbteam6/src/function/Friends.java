package function;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chatroom.ChatroomDAO;
import chatroom.ChatroomDTO;
import friend.FriendDAO;
import friend.FriendDTO;
import profile.ProfileDAO;
import profile.ProfileDTO;

public class Friends {
	/*
	 * 친구 목록 친구와 채팅방 생성 친구 삭제 닉네임으로 친구추가 친구추가요청 수락
	 */
	private FriendDAO dao;
	private ProfileDTO PRdto;

	public Friends(ProfileDTO dto) {
		dao = new FriendDAO(dto);
		PRdto = new ProfileDTO(dto);
	}

	public void ShowFriendsList() {
		System.out.println("나의 친구 목록");
		List<FriendDTO> list = dao.GetFriendList();
		if (list != null || list.size() > 0) {
			System.out.println("No.\t 친구 이름\n");
			int n = 0;
			for (FriendDTO dto : list) {
				n++;
				System.out.printf("%d\t %s\n", n, dto);
			}
		} else {
			System.out.println("현재 친구가 없습니다.");
		}
	}

	public void CreateChatroom_FR() {
		if (!dao.hasFriend()) {
			System.out.println("현재 친구가 없습니다. 먼저 친구를 추가해 주세요.");
		} else {
			List<FriendDTO> list = dao.GetFriendList();
			System.out.printf("어떤 친구와 채팅방을 생성하시겠습니까? [1-%d]\n", list.size());
			int inputint = 0;
			try {
				inputint = cons.ConsoleDB.scanner.nextInt();
				cons.ConsoleDB.scanner.nextLine();
				if (inputint < 1 || inputint > list.size())
					throw new InputMismatchException();
			} catch (InputMismatchException e) {
				System.out.println("잘못된 입력 값입니다.");
			}
			FriendDTO dto = list.get(inputint - 1);
			ChatroomDAO CRdao = new ChatroomDAO(PRdto);
			if (CRdao.hasChatroom(new ProfileDAO().getNickname_PRid(dto.getFriend_id())))
				System.out.println("이미 채팅방이 존재 합니다.");
			else {
				System.out.println("새로운 채팅방의 정보를 입력 해주세요.");
				System.out.println("채팅방 이름");
				String name = "";
				try {
					name = cons.ConsoleDB.scanner.nextLine().trim();
					if (CRdao.hasChatroomName(name))
						throw new InputMismatchException();
					else if (name.equals(""))
						throw new Exception("공백을 채팅방의 이름으로 할 수 없습니다.");
				} catch (InputMismatchException e) {
					System.out.println("중복된 이름입니다.");
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				System.out.println("위의 정보로 채팅방을 생성 하시겠습니까? (Y/N)");
				String input = cons.ConsoleDB.scanner.nextLine().trim();
				if (input.equalsIgnoreCase("y")) {
					ChatroomDTO CRdto = new ChatroomDTO(PRdto.getPRid(), dto.getFriend_id(), name);
					if (CRdao.CreateChatroom(CRdto))
						System.out.println("채팅방이 성공적으로 생성되었습니다.\n\n\n");
					else
						System.out.println("채팅방을 생성하지 못하였습니다.\n\n\n");
				} else
					System.out.println("채팅방 생성 작업을 취소 하였습니다.\n\n\n");
			}
		}
	}

	public void DeleteFriend() {
		if (!dao.hasFriend()) {
			System.out.println("현재 친구가 없습니다. 먼저 친구를 추가해 주세요.");
		} else {
			List<FriendDTO> list = dao.GetFriendList();
			System.out.printf("어떤 친구를 삭제 하시겠습니까? [1-%d]\n", list.size());
			int input = 0;
			while (true) {
				try {
					input = cons.ConsoleDB.scanner.nextInt();
					cons.ConsoleDB.scanner.nextLine();
					if (input < 1 || input > list.size())
						throw new Exception("잘못된 입력 값입니다.");
					else
						break;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			System.out.println("위의 친구를 정말 삭제하시겠습니까? (Y/N)");
			String input2 = cons.ConsoleDB.scanner.nextLine().trim();
			if (input2.equalsIgnoreCase("y")) {
				FriendDTO dto = list.get(input - 1);
				if (dao.DeleteFriend(dto))
					System.out.printf("친구 %s가 정상적으로 삭제 되었습니다\n\n\n\n", input);
				else
					System.out.printf("친구 %s를 삭제하지 못했습니다.\n\n\n\n", input);
			} else
				System.out.println("친구삭제 작업을 취소 하였습니다.\n\n\n");
		}
	}

	public void CreateFriendRequest_Nickname() {
		System.out.println("새로운 친구의 정보를 입력 해주세요.");
		System.out.println("친구의 Nickname");
		String nickname = "";
		ProfileDAO PRdao = new ProfileDAO();
		while (true) {
			try {
				nickname = cons.ConsoleDB.scanner.nextLine().trim();
				if (!PRdao.hasNickname(nickname))
					throw new Exception("해당 닉네임이 존재하지 않습니다.");
				else if (dao.hasFriend_Nickname(nickname))
					throw new Exception("중복된 친구입니다.");
				else if (PRdto.getNickname().equals(nickname))
					throw new Exception("자기 자신과 친구를 맺을 수 없습니다.");
				else if (nickname.equals(""))
					throw new Exception("공백을 친구의 닉네임으로 할 수 없습니다.");
				else
					break;
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

		System.out.println("위의 정보로 친구를 추가 신청하시겠습니까? (Y/N)");
		String input = cons.ConsoleDB.scanner.nextLine().trim();
		if (input.equalsIgnoreCase("y")) {
			if (dao.CreateFriendRequest(nickname))
				System.out.println("친구추가 신청을 성공적으로 전송하었습니다.\n\n\n");
			else
				System.out.println("친구추가 신청을 전송하지 못하였습니다.\n\n\n");
		} else
			System.out.println("친구추가 작업을 취소 하였습니다.\n\n\n");

	}

	public void ShowFriendRequestsList() {
		List<FriendDTO> list = dao.getFriendRequestList();
		System.out.println("친구 요청 목록");
		if (list != null && list.size() > 0) {
			System.out.println("No.\t 친구 이름\n");
			int n = 0;
			for (FriendDTO dto : list) {
				n++;
				System.out.printf("%d\t %s\n", n, dto);
			}
		} else {
			System.out.println("현재 친구 요청이 없습니다.");
		}
	}

	public void AcceptFriendRequest() {
		if (dao.hasFriendRequest()) {
			List<FriendDTO> list = dao.getFriendRequestList();
			System.out.printf("누구의 친구 추가 요청을 수락 하시겠습니까? [1-%d]\n", list.size());
			int input = 0;
			try {
				input = cons.ConsoleDB.scanner.nextInt();
				cons.ConsoleDB.scanner.nextLine();
				if (input < 1 || input > list.size())
					throw new InputMismatchException();
			} catch (InputMismatchException e) {
				System.out.println("잘못된 입력 값입니다.");
			}
			System.out.println("위의 친구요청을 수락하시겠습니까? (수락 Y/ 거절 N / 작업 취소)");
			String input2 = cons.ConsoleDB.scanner.nextLine().trim();
			if (input2.equalsIgnoreCase("y")) {
				FriendDTO dto = list.get(input - 1);
				if (dao.AcceptFriendRequest(dto))
					System.out.printf("친구 %s가 정상적으로 추가 되었습니다\n\n\n\n", input);
				else
					System.out.printf("친구 %s를 추가하지 못했습니다.\n\n\n\n", input);
			} else if (input2.equalsIgnoreCase("n")) {
				FriendDTO dto = list.get(input - 1);
				if (dao.DenyFriendRequest(dto))
					System.out.printf("친구 %s의 친구추가요청이 거절 되었습니다\n\n\n\n", input);
				else
					System.out.printf("친구 %s의 친구추가요청을 정상적으로 거절하지 못햇습니다.\n\n\n\n", input);
			} else
				System.out.println("친구 추가 요청수락 작업을 취소 하였습니다.\n\n\n");
		}
	}
}
