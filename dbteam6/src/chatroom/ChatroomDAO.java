package chatroom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import profile.ProfileDAO;
import profile.ProfileDTO;

public class ChatroomDAO{
	private Connection conn;
	//private Statement stmt;
	private PreparedStatement pstmt;
	// private CallabelStatement cstmt;
	private ProfileDAO PRdao= new ProfileDAO();
	private ProfileDTO PRdto;
	public ChatroomDAO() {
		
	}
	
	public ChatroomDAO(ProfileDTO dto) {
		// TODO Auto-generated constructor stub
		PRdto = new ProfileDTO(dto);
	}


	public List<ChatroomDTO> GetChatroomList() {
		// TODO Auto-generated method stub
		List<ChatroomDTO> list = new ArrayList<ChatroomDTO>();
		try {
			conn = PRdao.getConnection();
			String sql = "SELECT * FROM CHAT_ROOM WHERE Host_id = ? OR Participant_id = ?";
			int user_id = PRdto.getPRid();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, user_id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int CRid = rs.getInt("CRId");
				int Host_id = rs.getInt("Host_id");
				int participant_id = rs.getInt("Participant_id");
				String CRname = rs.getString("CRname");
				list.add(new ChatroomDTO(CRid, Host_id, participant_id,CRname));
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
			System.out.println("Error in getFriendRequestList Function");
			System.exit(1);
		}
		
		return list;
	}
	
	public boolean UpdateChatroom(ChatroomDTO dto, String CRname) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "UPDATE CHAT_ROOM SET CRname = ? WHERE CRId = ?";
			int CRId = dto.getCRId();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,CRname);
			pstmt.setInt(2, CRId);
			int rs = pstmt.executeUpdate();
			if(rs > 0)
				result = true;	
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in getFriendRequestList Function");
			System.exit(1);
		}
		return result;
	}

	public boolean DeleteChatroom(ChatroomDTO dto) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "DELETE FROM CHAT_ROOM WHERE CRId = ?";
			int CRId = dto.getCRId();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, CRId);
			int rs = pstmt.executeUpdate();
			if(rs > 0)
				result = true;	
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in getFriendRequestList Function");
			System.exit(1);
		}
		return result;
	}

	public boolean hasChatroomName(String name) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = cons.ConsoleDB.conn;
			String sql = "SELECT * FROM CHAT_ROOM WHERE (Host_id = ? OR Participant_id = ?) AND CRname = ?";
			pstmt = conn.prepareStatement(sql);
			int user_id = PRdto.getPRid();
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, user_id);
			pstmt.setString(3, name);
			int rs = pstmt.executeUpdate();
			if(rs > 0)
				result = true;	
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in getFriendRequestList Function");
			System.exit(1);
		}
		return result;
	}

	public boolean CreateChatroom(ChatroomDTO dto) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = cons.ConsoleDB.conn;
			String sql = "INSERT INTO CHAT_ROOM VALUES (Chat_room_SEQ.NEXTVAL, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getHost_id());
			pstmt.setInt(2, dto.getParticipant_id());
			pstmt.setString(3, dto.getCRname());
			int rs = pstmt.executeUpdate();
			if(rs > 0)
				result = true;	
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in getFriendRequestList Function");
			System.exit(1);
		}
		return result;
	}
	
	public boolean hasChatroom() {
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "SELECT * FROM CHAT_ROOM WHERE Host_id = ? OR Participant_id = ?";
			int PRId = PRdto.getPRid();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, PRId);
			pstmt.setInt(2, PRId);
			int rs = pstmt.executeUpdate();
			if(rs >0)
				result = true;
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in getFriendRequestList Function");
			System.exit(1);
		}
		return result;
	}
	
	public boolean hasChatroom(String nickname) {
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "WITH IDT AS (SELECT Host_id, Participant_id FROM CHAT_ROOM WHERE Host_id = ? OR Participant_id = ?) (SELECT Host_id  AS id FROM IDT,PROFILE WHERE Host_id = PRid AND Nickname = ? ) UNION ALL (SELECT Participant_id AS id FROM IDT,PROFILE WHERE Participant_id = PRid AND Nickname = ?)";
			int PRId = PRdto.getPRid();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, PRId);
			pstmt.setInt(2, PRId);
			pstmt.setString(3, nickname);
			pstmt.setString(4, nickname);
			int rs = pstmt.executeUpdate();
			if(rs > 0)
				result = true;
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in getFriendRequestList Function");
			System.exit(1);
		}
		return result;
	}
	
}