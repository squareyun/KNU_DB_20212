package category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.dbInfo;
import post.PostDTO;

public class CategoryDAO {
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;

	public boolean hasPageNext = true;
	
	public CategoryDAO() {
		try {
			dbInfo db = new dbInfo();
			Class.forName(db.getDriverName());
			conn = DriverManager.getConnection(db.getDbURL(), db.getDbID(), db.getDbPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<CategoryDTO> getList() {
		List<CategoryDTO> list = new ArrayList<>();

		try {
			String sql = "select * from category";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CategoryDTO category = new CategoryDTO();
				category.setCname(rs.getString(1));
				category.setCid(rs.getInt(2));
				category.setCimg(rs.getString(3));
				
				list.add(category);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public boolean updatePostCategory(int pid, String cname) {
		boolean result = false;
		
		try {
			String sql = "UPDATE POST SET CATEGORY_ID = (SELECT CID FROM CATEGORY WHERE CNAME = ?)  WHERE PID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cname);
			pstmt.setInt(2, pid);
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
			System.out.println("Error in category.updatePostCategory Function");
			result = false;
		}
		return result;
	}
}
