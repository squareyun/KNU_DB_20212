package category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
	public List<CategoryDTO> getList() {
		List<CategoryDTO> list = new ArrayList<>();

		try {
			String sql = "select * from category";
			ResultSet rs = cons.ConsoleDB.stmt.executeQuery(sql);

			while (rs.next()) {
				String cname = rs.getString(1);
				int cid = rs.getInt(2);
				list.add(new CategoryDTO(cid, cname));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("CategoryDAO.getList() 오류");
			e.printStackTrace();
		}
		
		return list;
	}
}
