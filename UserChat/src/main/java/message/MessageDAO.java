package message;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import chatroom.ChatroomDAO;
import chatroom.ChatroomDTO;
import db.dbInfo;
import profile.ProfileDAO;
import profile.ProfileDTO;

public class MessageDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	
	public MessageDAO() {
		try {
			dbInfo db = new dbInfo();
			Class.forName(db.getDriverName());
			conn = DriverManager.getConnection(db.getDbURL(), db.getDbID(), db.getDbPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int submit(String CRid, String fromID, String toID, String chatContent) {
		// TODO Auto-generated method stub 
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			String sql = "INSERT INTO MESSAGE VALUES(Message_SEQ.NEXTVAL,?,?,?,?,?,TO_DATE(?,'YYYYMMDDHH24MISS'))";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(CRid));
			pstmt.setInt(2, Integer.parseInt(fromID));
			ChatroomDAO chrdao = new ChatroomDAO();
			pstmt.setInt(3, chrdao.getChatroomDTO(Integer.parseInt(CRid)).getHost_id() );
			pstmt.setInt(4, chrdao.getChatroomDTO(Integer.parseInt(CRid)).getParticipant_id() );
			pstmt.setString(5, chatContent);
			pstmt.setString(6, sdf.format(timeStamp));
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in AcceptFriendRequest Function");
		}
		
		return -1;
	}
	

	public ArrayList<MessageDTO> getMessageListByRecent(String CRid, String fromID, String toID, String listType) {
		// TODO Auto-generated method stub
		ArrayList<MessageDTO> messageList = new ArrayList<MessageDTO>();
		try {
		String sql = "SELECT * FROM MESSAGE WHERE (CRid = ? AND (Sender_id = ? OR Sender_id = ?)) AND Mid > ? ORDER BY Create_date";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(CRid));
		pstmt.setInt(2, Integer.parseInt(fromID));
		pstmt.setInt(3, Integer.parseInt(toID));
		pstmt.setInt(4, Integer.parseInt(listType));
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int Mid = rs.getInt("Mid");
			int Crid = rs.getInt("CRid");
			int Sender_id = rs.getInt("Sender_id");
			int CR_Host_id = rs.getInt("CR_Host_id");
			int CR_participant_id = rs.getInt("CR_Participant_id");
			String Contents = rs.getString("Contents");
			Timestamp Create_date = rs.getTimestamp("Create_date");
			messageList.add(new MessageDTO(Mid,Crid,Sender_id,CR_Host_id,CR_participant_id,Contents,Create_date));
		}
		}catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in getMessageListByRecent Function");
		}
		return messageList;
	}
	
	public String getLabel(MessageDTO dto1, MessageDTO dto2) {
		if(dto1.getCreate_date().toString().charAt(9) != dto2.getCreate_date().toString().charAt(9)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
			return sdf.format(dto2.getCreate_date()).toString();
		}
		else 
			return "0";
	}
	
	public String getLabel(MessageDTO dto) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
			return sdf.format(dto.getCreate_date()).toString();
	}
	
	public ArrayList<MessageDTO> getMessageList(String Crid) {
		// TODO Auto-generated method stub
		ArrayList<MessageDTO> list = new ArrayList<MessageDTO>();
		try {
			String sql = "SELECT * FROM MESSAGE WHERE CRid = ? ORDER BY Create_date";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(Crid));
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int Mid = rs.getInt("Mid");
				int CRid = rs.getInt("CRid");
				int Sender_id = rs.getInt("Sender_id");
				int CR_Host_id = rs.getInt("CR_Host_id");
				int CR_participant_id = rs.getInt("CR_Participant_id");
				String Contents = rs.getString("Contents");
				Timestamp Create_date = rs.getTimestamp("Create_date");
				list.add(new MessageDTO(Mid,CRid,Sender_id,CR_Host_id,CR_participant_id,Contents,Create_date));
			}
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in AcceptFriendRequest Function");
		}
		return list;
	}
	
	public MessageDTO getRecentMessageDTO(int crid) {
		try {
			String sql = "SELECT * FROM MESSAGE WHERE CRid = ? ORDER BY Create_date DESC LIMIT 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, crid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int Mid = rs.getInt("Mid");
				int CRid = rs.getInt("CRid");
				int Sender_id = rs.getInt("Sender_id");
				int CR_Host_id = rs.getInt("CR_Host_id");
				int CR_participant_id = rs.getInt("CR_Participant_id");
				String Contents = rs.getString("Contents");
				Timestamp Create_date = rs.getTimestamp("Create_date");
				return new MessageDTO(Mid, CRid, Sender_id, CR_Host_id, CR_participant_id, Contents, Create_date);
			}
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in getRecentMessageDTO Function");
			System.exit(1);
		}
		return null;
	}
	public MessageDTO getMessagebyMID(String MID) {
		try {
			String sql = "SELECT * FROM MESSAGE WHERE Mid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(MID));
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int Mid = rs.getInt("Mid");
				int CRid = rs.getInt("CRid");
				int Sender_id = rs.getInt("Sender_id");
				int CR_Host_id = rs.getInt("CR_Host_id");
				int CR_participant_id = rs.getInt("CR_Participant_id");
				String Contents = rs.getString("Contents");
				Timestamp Create_date = rs.getTimestamp("Create_date");
				return new MessageDTO(Mid, CRid, Sender_id, CR_Host_id, CR_participant_id, Contents, Create_date);
			}
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in getRecentMessageDTO Function");
			System.exit(1);
		}
		return null;
	}
}
