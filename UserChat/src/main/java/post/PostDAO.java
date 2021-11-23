package post;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
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

	public int write(int category, int creator_id, String postTitle, String postContent) {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		
		try {
			String sql = "insert into post values(Post_SEQ.nextval, ?, ?, ?, to_date(?, 'yyyy-mm-dd hh24:mi:ss'), ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, creator_id);
			
			pstmt.setCharacterStream(2, new StringReader(postContent));
			
			
			pstmt.setString(3, postTitle);
			pstmt.setString(4, sdf.format(timeStamp).toString());
			pstmt.setInt(5, category);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // 작성 오류
	}

	public List<PostDTO> getPostListInCategory(int category, int page) {
		List<PostDTO> list = new ArrayList<>();

		try {
			String sql = "select * from (SELECT ROW_NUMBER() OVER(ORDER BY p.create_date desc) row_num, p.*, pr.nickname FROM post p join profile pr on p.creator_id = pr.prid where category_id = ? ORDER BY p.create_date desc) WHERE row_num BETWEEN ? AND ?";
			int start = 10 * page - 9;
			int end = 10 * page;

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				PostDTO post = new PostDTO();
				post.setPid(rs.getInt(2));
				post.setCreator_id(rs.getInt(3));
				post.setContents(rs.getString(4));
				post.setTitle(rs.getString(5));
				post.setCreate_date(rs.getTimestamp(6).toString());
				post.setCategory_id(rs.getInt(7));
				post.setNickname(rs.getString(8));
				list.add(post);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		hasPageNext = list.size() < 10 ? false : true;

		return list;
	}
	
	public PostDTO getPost(int pid) throws IOException {
		try {
			String sql = "select p.*, pr.nickname, pr.email from profile pr, post p where p.creator_id = pr.prid and pid = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				PostDTO post = new PostDTO();
				post.setPid(rs.getInt(1));
				post.setCreator_id(rs.getInt(2));
				post.setContents(readClobData(rs.getCharacterStream(3)));
				post.setTitle(rs.getString(4));
				post.setCreate_date(rs.getTimestamp(5).toString());
				post.setCategory_id(rs.getInt(6));
				post.setNickname(rs.getString(7));
				post.setEmail(rs.getString(8));
				return post;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int pid, String postTitle, String postContent) {
		try {
			String sql = "UPDATE post SET title = ?, content = ? where pid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, postTitle);
			pstmt.setCharacterStream(2, new StringReader(postContent));
			pstmt.setInt(3, pid);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // 오류
	}
	
	public int delete(int pid) {
		try {
			conn.setAutoCommit(false);
			String sql = "delete from post where pid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			conn.commit();
			conn.setAutoCommit(true);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // 오류
	}
	
	
	public static String readClobData(Reader reader) throws IOException {
        StringBuffer data = new StringBuffer();
        char[] buf = new char[1024];
        int cnt = 0;
        if (null != reader) {
            while ( (cnt = reader.read(buf)) != -1) {
                data.append(buf, 0, cnt);
            }
        }
        return data.toString();
        // 출처: https://rainny.tistory.com/87
	}
	
}