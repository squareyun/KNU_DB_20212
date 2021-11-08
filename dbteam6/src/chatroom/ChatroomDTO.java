package chatroom;
import profile.ProfileDAO;

public class ChatroomDTO {
	private int CRId;
	private int Host_id;
	private int Participant_id;
	private String CRname;
	
	public ChatroomDTO(ChatroomDTO dto) {
		// TODO Auto-generated constructor stub
		CRId = dto.getCRId();
		Host_id = dto.getHost_id();
		Participant_id = dto.getParticipant_id();
		CRname = dto.getCRname();
	}
	
	public String getCRname() {
		// TODO Auto-generated method stub
		return CRname;
	}

	public int getParticipant_id() {
		// TODO Auto-generated method stub
		return Participant_id;
	}

	public int getHost_id() {
		// TODO Auto-generated method stub
		return Host_id;
	}
	
	public int getCRId() {
		// TODO Auto-generated method stub
		return CRId;
	}

	public ChatroomDTO(int Host_id, String Pariticipant_nickname, String CRname) {
		// TODO Auto-generated constructor stub
		this.Host_id = Host_id;
		Participant_id = new ProfileDAO().getPRid_Nickname(Pariticipant_nickname);
		this.CRname = CRname;
	}

	public ChatroomDTO(int Host_id, int Participant_id, String CRname) {
		// TODO Auto-generated constructor stub
		this.Host_id = Host_id;
		this.Participant_id = Participant_id;
		this.CRname = CRname;
	}

	public ChatroomDTO(int CRid, int Host_id, int Participant_id, String CRname) {
		// TODO Auto-generated constructor stub
		this.CRId = CRid;
		this.Host_id = Host_id;
		this.Participant_id = Participant_id;
		this.CRname = CRname;
	}
}
