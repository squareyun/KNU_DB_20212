package message;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import chatroom.ChatroomDTO;
import profile.ProfileDAO;

public class MessageDTO {
	private int Mid;
	private int CRid;
	private int Sender_id;
	private int CR_Host_id;
	private int CR_participant_id;
	private String Contents;
	Timestamp Create_date;
	
	public MessageDTO(ChatroomDTO dto,int sender_id ,String contents, Timestamp create_date) {
		// TODO Auto-generated constructor stub
		CRid = dto.getCRId();
		CR_Host_id = dto.getHost_id();
		CR_participant_id = dto.getParticipant_id();
		Sender_id = sender_id;
		Contents = contents;
		Create_date = create_date;
	}

	public MessageDTO(int mid, int cRid, int sender_id, int cR_Host_id, int cR_participant_id, String contents,Timestamp create_date) {
		// TODO Auto-generated constructor stub
		Mid = mid;
		CRid = cRid;
		CR_Host_id = cR_Host_id;
		CR_participant_id = cR_participant_id;
		Sender_id = sender_id;
		Contents = contents;
		Create_date = create_date;
	}

	public MessageDTO() {
		// TODO Auto-generated constructor stub
	}

	public void getInfo(int cur_user_id) {
	      // TODO Auto-generated method stub
	      String sender_nickname = new ProfileDAO().getNickname_PRid(Sender_id);
	      sender_nickname = "["+ sender_nickname + "]님";
	      if(Sender_id == cur_user_id)
	         System.out.printf("%-20s : %-20s\n",sender_nickname,Contents);
	      else
	         System.out.printf("%-20s : %-20s\n",sender_nickname,Contents);
	}
	public int getMid() {
		return Mid;
	}
	public int getCRid() {
		// TODO Auto-generated method stub
		return CRid;
	}

	public int getSender_id() {
		// TODO Auto-generated method stub
		return Sender_id;
	}

	public int getCR_Host_id() {
		// TODO Auto-generated method stub
		return CR_Host_id;
	}

	public int getCR_Participant_id() {
		// TODO Auto-generated method stub
		return CR_participant_id;
	}

	public String getContents() {
		// TODO Auto-generated method stub
		return Contents;
	}

	public String getCreate_date() {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(Create_date).toString();
	}
}
