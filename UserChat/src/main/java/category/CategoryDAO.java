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
}
