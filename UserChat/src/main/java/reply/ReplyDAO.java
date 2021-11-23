package reply;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import db.dbInfo;

public class ReplyDAO {
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;

	public boolean hasPageNext = true;
	
	public ReplyDAO() {
		try {
			dbInfo db = new dbInfo();
			Class.forName(db.getDriverName());
			conn = DriverManager.getConnection(db.getDbURL(), db.getDbID(), db.getDbPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ReplyDTO> getReplyListInPost(int pid) {
		List<ReplyDTO> list = new ArrayList<>();

		try {
			String sql = "select r.*, p.nickname, p.email from reply r join profile p on r.creator_id = p.prid where post_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReplyDTO reply = new ReplyDTO();
				reply.setRid(rs.getInt(1));
				reply.setPost_id(rs.getInt(2));
				reply.setPost_creator_id(rs.getInt(3));
				reply.setCreator_id(rs.getInt(4));
				reply.setContents(rs.getString(5));
				reply.setCreate_date(rs.getTimestamp(6).toString());
				reply.setNickname(rs.getString(7));
				reply.setEmail(rs.getString(8));
				
				list.add(reply);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean delete(int rid) {
		boolean result = false;
		try {
			String sql = "DELETE FROM REPLY WHERE RID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rid);
			int rs = pstmt.executeUpdate();
			if (rs > 0)
				result = true;
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			result = false;
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in Reply delete Function");
			result = false;
		}
		return result;
	}
	
	public boolean write(int pid, int post_creator_id, String creator_email, String contents) {
		boolean result = false;
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		
		try {
			String sql = "insert into reply values(Reply_SEQ.nextval, ?, ?, (select prid from profile where email = ?), ?, to_date(?, 'yyyy-mm-dd hh24:mi:ss'))";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pid);
			pstmt.setInt(2, post_creator_id);
			pstmt.setString(3,  creator_email);
			pstmt.setString(4,  contents);
			pstmt.setString(5, sdf.format(timeStamp).toString());
			
			int rs = pstmt.executeUpdate();
			if (rs > 0)
				result = true;
			
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			result = false;
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in Reply write Function");
			result = false;
		}
		return result;
	}
	
	public boolean update(int rid, String contents) {
		boolean result = false;
		try {
			String sql = "UPDATE REPLY SET CONTENTS = ? WHERE RID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, contents);
			pstmt.setInt(2, rid);
			int rs = pstmt.executeUpdate();
			if (rs > 0)
				result = true;
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			result = false;
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in Reply update Function");
			result = false;
		}
		return result;
	}
}