package message;

import java.sql.*;
import java.text.SimpleDateFormat;

import chatroom.ChatroomDTO;
public class MessageDTO {
	private int Mid;
	private int CRid;
	private int Sender_id;
	private int CR_Host_id;
	private int CR_participant_id;
	private String Contents;
	private Timestamp Create_date;
	
	public MessageDTO(ChatroomDTO dto,int sender_id ,String contents, Timestamp create_date) {
		// TODO Auto-generated constructor stub
		setCRid(dto.getCRId());
		setCR_Host_id(dto.getHost_id());
		setCR_participant_id(dto.getParticipant_id());
		setSender_id(sender_id);
		setContents(contents);
		setCreate_date(create_date);
	}

	public MessageDTO(int mid, int cRid, int sender_id, int cR_Host_id, int cR_participant_id, String contents,Timestamp create_date) {
		// TODO Auto-generated constructor stub
		setMid(mid);
		setCRid(cRid);
		setCR_Host_id(cR_Host_id);
		setCR_participant_id(cR_participant_id);
		setSender_id(sender_id);
		setContents(contents);
		setCreate_date(create_date);
	}
	
	public String getContents() {
		return Contents;
	}
	public void setContents(String contents) {
		Contents = contents;
	}
	public int getCR_participant_id() {
		return CR_participant_id;
	}
	public void setCR_participant_id(int cR_participant_id) {
		CR_participant_id = cR_participant_id;
	}
	public int getCR_Host_id() {
		return CR_Host_id;
	}
	public void setCR_Host_id(int cR_Host_id) {
		CR_Host_id = cR_Host_id;
	}
	public int getSender_id() {
		return Sender_id;
	}
	public void setSender_id(int sender_id) {
		Sender_id = sender_id;
	}
	public int getCRid() {
		return CRid;
	}
	public void setCRid(int cRid) {
		CRid = cRid;
	}
	public int getMid() {
		return Mid;
	}
	public void setMid(int mid) {
		Mid = mid;
	}

	public String getCreate_date_str() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(Create_date).toString();
	}
	public Timestamp getCreate_date() {
		return Create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		Create_date = create_date;
	}

	public int getReceiver_id() {
		if(Sender_id == CR_Host_id)
			return CR_participant_id;
		else
			return CR_Host_id;
	}

	public int ishost() {
		if(Sender_id == CR_Host_id)
			return 1;
		else
			return 0;
	}
}
