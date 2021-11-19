package post;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import db.dbInfo;
import profile.ProfileDAO;

public class PostDAO {
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;

	public boolean hasPageNext = true;
	
	public PostDAO() {
		try {
			dbInfo db = new dbInfo();
			Class.forName(db.getDriverName());
			conn = DriverManager.getConnection(db.getDbURL(), db.getDbID(), db.getDbPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public String getDate(String format) {
//		// format이 YYYY-MM-DD이라면 2021-11-19와 같이 반환됨
//		String sql = "SELECT TO_CHAR(SYSDATE, '" + format + "') FROM DUAL";
//		try {
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			if (rs.next()) {
//				return rs.getString(1);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}

//	public int makePost(int prid, String input_title, String input_contents) {
//		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		try {
//			Statement stmt = conn.createStatement();
//			
//			String sql = String.format(
//					"insert into post values(Post_SEQ.nextval, %d, '%s', '%s', to_date('%s', 'yyyy-mm-dd hh24:mi:ss'), %d)",
//					prid, input_contents, input_title, sdf.format(timeStamp).toString(), cons.ConsoleDB.choosenCategory);
//			stmt.executeUpdate(sql);
//		} catch (SQLException e) {
//			System.out.println("PostDAO.makePost() 오류");
//			e.printStackTrace();
//		}
//	}

	public List<PostDTO> getPostListInCategory(int category, int page) {
		List<PostDTO> list = new ArrayList<>();

		try {
			String sql = "select * from (SELECT ROW_NUMBER() OVER(ORDER BY p.create_date desc) row_num, p.* FROM post p where category_id = ? ORDER BY p.create_date desc) WHERE row_num BETWEEN ? AND ?";
			int start = 10 * page - 9;
			int end = 10 * page;

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PostDTO post = new PostDTO();
				post.setPid(rs.getInt(2));
				post.setCreator_id(rs.getInt(3));
				post.setContents(rs.getString(4));
				post.setTitle(rs.getString(5));
				post.setCreate_date(rs.getTimestamp(6).toString());
				post.setCategory_id(rs.getInt(7));
				list.add(post);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		hasPageNext = list.size() < 10 ? false : true;

		return list;
	}
}