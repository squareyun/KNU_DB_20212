package friend;

import java.util.ArrayList;
import java.util.List;

import profile.ProfileDAO;
import profile.ProfileDTO;

import java.sql.*;

public class FriendDAO {
	private Connection conn;
	// private Statement stmt;
	private PreparedStatement pstmt;
	// private CallabelStatement cstmt;
	private ProfileDAO PRdao = new ProfileDAO();
	private ProfileDTO PRdto;

	public FriendDAO() {

	}

	public FriendDAO(ProfileDTO dto) {
		// TODO Auto-generated constructor stub
		PRdto = new ProfileDTO(dto);
	}

	public List<FriendDTO> GetFriendList() {
		// TODO Auto-generated method stub
		List<FriendDTO> list = new ArrayList<FriendDTO>();
		try {
			conn = PRdao.getConnection();
			String sql = "(SELECT Friend_id FROM FRIEND WHERE PRid = ?) INTERSECT (SELECT PRid AS Friend_id FROM FRIEND WHERE Friend_id = ?)";
			int user_id = PRdto.getPRid();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, user_id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int Friend_id = rs.getInt("Friend_id");
				list.add(new FriendDTO(user_id, Friend_id));
			}
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in GetFriendList SQLException Function");
			System.exit(1);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println("Error in GetFriendList Function");
			System.exit(1);
		}

		return list;
	}

	public boolean DeleteFriend(FriendDTO dto) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "DELETE FROM FRIEND WHERE PRid = ? AND Friend_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUser_id());
			pstmt.setInt(2, dto.getFriend_id());
			int rs1 = pstmt.executeUpdate();
			pstmt.setInt(1, dto.getFriend_id());
			pstmt.setInt(2, dto.getUser_id());
			int rs2 = pstmt.executeUpdate();
			if (rs1 > 0 && rs2 > 0)
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
			System.out.println("Error in DeleteFriend Function");
			System.exit(1);
		}
		return result;
	}
	
	public boolean DenyFriendRequest(FriendDTO dto) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "DELETE FROM FRIEND WHERE PRid = ? AND Friend_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getFriend_id());
			pstmt.setInt(2, dto.getUser_id());
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
			System.out.println("Error in DenyFriendRequest Function");
			System.exit(1);
		}
		return result;
	}

	public boolean hasFriend_Nickname(String nickname) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "(SELECT f.PRid FROM FRIEND f ,PROFILE p WHERE p.PRid = f.PRid AND f.Friend_id = ? AND p.Nickname = ? ) INTERSECT (SELECT p.PRid FROM FRIEND f,PROFILE p WHERE f.Friend_id = p.PRid AND f.PRid = ?  AND p.Nickname = ?)";
			pstmt = conn.prepareStatement(sql);
			int user_id = PRdto.getPRid();
			pstmt.setInt(1, user_id);
			pstmt.setString(2, nickname);
			pstmt.setInt(3, user_id);
			pstmt.setString(4, nickname);
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
			System.out.println("Error in hasFriend_Nickname Function");
			System.exit(1);
		}
		return result;
	}

	public boolean CreateFriendRequest(String nickname) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "INSERT INTO FRIEND VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, PRdto.getPRid());
			pstmt.setInt(2, PRdao.getPRid_Nickname(nickname));
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
			System.out.println("Error in CreateFriendRequest Function");
			System.exit(1);
		}
		return result;
	}

	public List<FriendDTO> getFriendRequestList() {
		// TODO Auto-generated method stub
		List<FriendDTO> list = new ArrayList<FriendDTO>();
		try {
			conn = PRdao.getConnection();
			String sql = "(SELECT PRid FROM FRIEND WHERE Friend_id = ?) MINUS (SELECT Friend_id AS PRid FROM FRIEND WHERE PRid = ?)";
			int user_id = PRdto.getPRid();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, user_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int FriendRequest_id = rs.getInt("PRid");
				list.add(new FriendDTO(user_id, FriendRequest_id));
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

	public boolean AcceptFriendRequest(FriendDTO dto) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "INSERT INTO FRIEND VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUser_id());
			pstmt.setInt(2, dto.getFriend_id());
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

	public boolean hasFriend() {
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "(SELECT Friend_id FROM FRIEND WHERE PRid = ?) INTERSECT (SELECT PRid AS Friend_id FROM FRIEND WHERE Friend_id = ?)";
			int PRid = PRdto.getPRid();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, PRid);
			pstmt.setInt(2, PRid);
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
			System.out.println("Error in getFriendRequestList Function");
			System.exit(1);
		}
		return result;
	}

	public boolean hasFriendRequest() {
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "(SELECT PRid FROM FRIEND WHERE Friend_id = ?) MINUS (SELECT Friend_id AS PRid FROM FRIEND WHERE PRid = ?)";
			int PRid = PRdto.getPRid();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, PRid);
			pstmt.setInt(2, PRid);
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
			System.out.println("Error in getFriendRequestList Function");
			System.exit(1);
		}
		return result;
	}
	
	public boolean hasFriendRequest(String nickname) {
		boolean result = false;
		try {
			conn = PRdao.getConnection();
			String sql = "(SELECT PRid FROM FRIEND WHERE PRid = ? AND Friend_id = ?) UNION ALL (SELECT PRid FROM FRIEND WHERE PRid = ? AND Friend_id = ?)";
			int PRid = PRdto.getPRid();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, PRid);
			pstmt.setInt(2, new ProfileDAO().getPRid_Nickname(nickname));
			pstmt.setInt(3, new ProfileDAO().getPRid_Nickname(nickname));
			pstmt.setInt(4, PRid);
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
			System.out.println("Error in getFriendRequestList Function");
			System.exit(1);
		}
		return result;
	}
	
	

}
