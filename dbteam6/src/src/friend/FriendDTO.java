package friend;
import profile.ProfileDTO;
import profile.ProfileDAO;

public class FriendDTO {
	ProfileDTO PRdto;
	private int User_id;
	private int Friend_id;
	
	public FriendDTO(int user_id, int friend_id) {
		// TODO Auto-generated constructor stub
		User_id = user_id;
		Friend_id = friend_id;
	}

	public int getFriend_id() {
		// TODO Auto-generated method stub
		return Friend_id;
	}
	
	public int getUser_id() {
		return User_id;
	}
	
	@Override
	public String toString() {
		ProfileDAO PRdao = new ProfileDAO(); 
		return PRdao.getNickname_PRid(Friend_id);
	}

}
