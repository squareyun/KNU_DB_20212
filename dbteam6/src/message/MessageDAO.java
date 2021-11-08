package message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import chatroom.ChatroomDTO;
import profile.ProfileDAO;
import profile.ProfileDTO;

public class MessageDAO {
	private Connection conn;
	// private Statement stmt;
	private PreparedStatement pstmt;
	// private CallabelStatement cstmt;
	private ProfileDAO PRdao = new ProfileDAO();
	private ProfileDTO PRdto;
	private ChatroomDTO CRdto;

	public MessageDAO(ChatroomDTO dto, ProfileDTO prdto) {
		// TODO Auto-generated constructor stub
		CRdto = new ChatroomDTO(dto);
		PRdto = new ProfileDTO(prdto);
	}

	public List<MessageDTO> getMessageList() {
		// TODO Auto-generated method stub
		List<MessageDTO> list = new ArrayList<MessageDTO>();
		try {
			conn = PRdao.getConnection();
			String sql = "SELECT * FROM MESSAGE WHERE CRid = ? ORDER BY Create_date";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, CRdto.getCRId());
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
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in AcceptFriendRequest Function");
			System.exit(1);
		}
		return list;
	}

	public boolean CreateMessage(MessageDTO mdto) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "INSERT INTO MESSAGE VALUES(Message_SEQ.NEXTVAL,?,?,?,?,?,TO_DATE(?,'YYYYMMDDHH24MISS'))";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mdto.getCRid());
			pstmt.setInt(2, mdto.getSender_id());
			pstmt.setInt(3, mdto.getCR_Host_id());
			pstmt.setInt(4, mdto.getCR_Participant_id());
			pstmt.setString(5, mdto.getContents());
			pstmt.setString(6, mdto.getCreate_date());
			int rs = pstmt.executeUpdate();
			if (rs > 0)
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
			System.out.println("Error in AcceptFriendRequest Function");
			System.exit(1);
		}
		return result;
	}

	public boolean hasNewReplyMessage(List<MessageDTO> newreplymlist) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "SELECT COUNT(*) AS M_N FROM MESSAGE WHERE CRid = ? AND Sender_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, CRdto.getCRId());
			int sender_id = CRdto.getHost_id();
			if(sender_id == PRdto.getPRid())
				sender_id = CRdto.getParticipant_id();
			pstmt.setInt(2,sender_id);
			
			ResultSet rs = pstmt.executeQuery();
			int N = 0;
			while(rs.next())
				N = rs.getInt("M_N");
			if(newreplymlist.size() < N)
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
			System.out.println("Error in AcceptFriendRequest Function");
			System.exit(1);
		}
		return result;
	}

	public List<MessageDTO> getNewReplyMessageList(List<MessageDTO> newreplymlist) {
		// TODO Auto-generated method stub
		List<MessageDTO> list = new ArrayList<MessageDTO>();
		try {
			conn = PRdao.getConnection();
			String sql = "SELECT * FROM MESSAGE WHERE CRid = ? AND Sender_id = ? ORDER BY Create_date";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, CRdto.getCRId());
			int sender_id = CRdto.getHost_id();
			if(sender_id == PRdto.getPRid())
				sender_id = CRdto.getParticipant_id();
			pstmt.setInt(2,sender_id);
			
			MessageDTO dto= new MessageDTO();
			ResultSet rs = pstmt.executeQuery();
			int n = 0;
			while (rs.next()) {
				n++;
				int Mid = rs.getInt("Mid");
				int CRid = rs.getInt("CRid");
				int Sender_id = rs.getInt("Sender_id");
				int CR_Host_id = rs.getInt("CR_Host_id");
				int CR_participant_id = rs.getInt("CR_Participant_id");
				String Contents = rs.getString("Contents");
				Timestamp Create_date = rs.getTimestamp("Create_date");
				dto = new MessageDTO(Mid,CRid,Sender_id,CR_Host_id,CR_participant_id,Contents,Create_date);
				if(newreplymlist != null && newreplymlist.size() < n)
					list.add(dto);
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
			System.out.println("Error in AcceptFriendRequest Function");
			System.exit(1);
		}
		return list;
	}

}
