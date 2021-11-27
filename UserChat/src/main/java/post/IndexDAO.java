package post;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.dbInfo;

public class IndexDAO {
	private ResultSet rs;
	private PreparedStatement pstmt;
	private Connection conn;
	
	

	public IndexDAO() {
		try {
			dbInfo db = new dbInfo();
			Class.forName(db.getDriverName());
			conn = DriverManager.getConnection(db.getDbURL(), db.getDbID(), db.getDbPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public PostDTO getIndexPost() {
		String sql;
		try {
			sql = "select * from post where pid = -1";

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				PostDTO post = new PostDTO();
				post.setPid(rs.getInt("PID"));
				post.setCreator_id(rs.getInt("Creator_id"));
				post.setTitle(rs.getString("TITLE"));
				post.setCreate_date(null);
				post.setContents(rs.getString("Content"));
				post.setCategory_id(-1);
				return post;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public int update(String postContent) {
		int res = -1;
		String sql;
		try {
			sql = "select * from post where pid = -1 for update NOWAIT";
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getErrorCode() + " : "+ e.getMessage());
			return res;
		}
		try {
			sql = "UPDATE post SET content = ? where pid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setCharacterStream(1, new StringReader(postContent));
			pstmt.setInt(2, -1);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return res;
		}		
		return res;
	}

}
