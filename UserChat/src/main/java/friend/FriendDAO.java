package friend;

import java.util.ArrayList;
import java.util.List;

import db.dbInfo;
import profile.ProfileDAO;
import profile.ProfileDTO;

import java.sql.*;

public class FriendDAO {
	
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;

	public FriendDAO() {
		try {
			dbInfo db = new dbInfo();
			Class.forName(db.getDriverName());
			conn = DriverManager.getConnection(db.getDbURL(), db.getDbID(), db.getDbPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public List<FriendDTO> GetFriendList(int current_User_Prid) {
		// TODO Auto-generated method stub
		List<FriendDTO> list = new ArrayList<FriendDTO>();
		
		int user_id = current_User_Prid;
		try {
			String sql = "(SELECT Friend_id FROM FRIEND WHERE PRid = ?) INTERSECT (SELECT PRid AS Friend_id FROM FRIEND WHERE Friend_id = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, user_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int Friend_id = rs.getInt("Friend_id");
				FriendDTO friend = new FriendDTO(user_id , Friend_id);
				list.add(friend);
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

	public boolean DeleteFriend(int userPRid , int frinedPRid ) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			String sql = "DELETE FROM FRIEND WHERE PRid = ? AND Friend_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userPRid);
			pstmt.setInt(2, frinedPRid);
			int rs1 = pstmt.executeUpdate();
			pstmt.setInt(1, frinedPRid);
			pstmt.setInt(2, userPRid);
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
	
	public boolean alreadyFriend(int userPRid , int frinedPRid ) {
		boolean result = false;
		List<FriendDTO> list = new ArrayList<FriendDTO>();
		list = GetFriendList(userPRid);
		for(FriendDTO f : list) {
			if(f.getFriend_id() == frinedPRid)
				return true;
		}
		return result;
	}
	public boolean alreadyRequestedFriend(int userPRid , int frinedPRid ) {
		boolean result = false;
		List<Integer> list = getFriendRequestList(userPRid);
		for(Integer f : list) { //받은 요청중에 있는지 확인
			if(f == frinedPRid) {
				System.out.println("받은 친구요청 목록에 이미 존재.");
				return true;
			}
		}
		//보낸 요청중에 있는 지 확인 
		try {
			String sql = " select prid from friend where prid = ? and friend_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userPRid);
			pstmt.setInt(2, frinedPRid);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("보낸 친구요청 목록에 이미 존재.");
				return true;
			}
			else
				return false;
		}catch(SQLException e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			result = false;
		}
		
		return result;
	}

	public boolean CreateFriendRequest(int userPrid,String firendNickname) {
		// TODO Auto-generated method stub
		boolean result = false;
		ProfileDTO friend = new ProfileDAO().getUserByNickname(firendNickname);
		
		if(friend == null)
			return result;
		if( alreadyFriend(userPrid,friend.getPRid()) == true) {
			System.out.println("친구목록에 이미 존재.");
			return result;
		}
		if( alreadyRequestedFriend(userPrid,friend.getPRid()) == true) {
			return result;
		}
		try {
			String sql = "INSERT INTO FRIEND VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userPrid);
			pstmt.setInt(2, friend.getPRid());
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
			System.out.println("Error in CreateFriendRequest Function");
			result = false;
		}
		return result;
	}

	public List<Integer> getFriendRequestList(int userPrid) {
		// TODO Auto-generated method stub
		List<Integer> friendRequesList = new ArrayList<Integer>();
		try {
			String sql = "(SELECT PRid FROM FRIEND WHERE Friend_id = ?) MINUS (SELECT Friend_id AS PRid FROM FRIEND WHERE PRid = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userPrid);
			pstmt.setInt(2, userPrid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int FriendRequest_id = rs.getInt("PRid");
				friendRequesList.add(FriendRequest_id);
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
		return friendRequesList;
	}

	public boolean AcceptFriendRequest(int userPRid,int friendPRid) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			String sql = "INSERT INTO FRIEND VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userPRid);
			pstmt.setInt(2, friendPRid);
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
			System.out.println("Error in AcceptFriendRequest Function");
			result = false;
		}
		return result;
	}
	public boolean DenyFriendRequest(int userPRid,int friendPRid) {
	// TODO Auto-generated method stub
	boolean result = false;
	try {
		String sql = "DELETE FROM FRIEND WHERE PRid = ? AND Friend_id = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, friendPRid);
		pstmt.setInt(2, userPRid);
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
		System.out.println("Error in DenyFriendRequest Function");
		result = false;
	}
	return result;
}
	

}
