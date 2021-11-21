package friend;

public class FriendDTO {
	
	private int User_id;
	private int Friend_id;
	
	public FriendDTO() {
		// TODO Auto-generated constructor stub
		User_id = 0;
		Friend_id = 0;
	}

	public FriendDTO(int user_id2, int friend_id2) {
		// TODO Auto-generated constructor stub
		User_id = user_id2;
		Friend_id = friend_id2;
	}

	public int getFriend_id() {
		// TODO Auto-generated method stub
		return Friend_id;
	}
	
	public int getUser_id() {
		return User_id;
	}

}