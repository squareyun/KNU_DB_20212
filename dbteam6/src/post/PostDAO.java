package post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {
	public List<PostDTO> getPostListInCategory(int category) {
		List<PostDTO> list = new ArrayList<>();

		try {
			String sql = "select * from post where category_id = " + category + " order by pid";
			ResultSet rs = cons.ConsoleDB.stmt.executeQuery(sql);

			while (rs.next()) {
				list.add(new PostDTO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
						rs.getTimestamp(5).toString(), category));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("PostDAO.getList() 오류");
			e.printStackTrace();
		}

		return list;
	}

	public static PostDTO getPostInfo(int pid) {
		PostDTO dto = null;

		try {
			String sql = "select * from post where pid = " + pid;
			ResultSet rs = cons.ConsoleDB.stmt.executeQuery(sql);

			while (rs.next()) {
				dto = new PostDTO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
						rs.getTimestamp(5).toString(), rs.getInt(6));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("PostDAO.getPostInfo() 오류");
			e.printStackTrace();
		}

		return dto;
	}

	public static void makePost(String input_title, String input_contents) {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			String sql = String.format(
					"insert into post values(Post_SEQ.nextval, %d, '%s', '%s', to_date('%s', 'yyyy-mm-dd hh24:mi:ss'), %d)",
					cons.ConsoleDB.currentUser.getPRid(), input_contents, input_title, sdf.format(timeStamp).toString(), cons.ConsoleDB.choosenCategory);
			cons.ConsoleDB.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("PostDAO.makePost() 오류");
			e.printStackTrace();
		}

	}
	
	public static void deletePost(int pid) {
		try {
			String sql = String.format("delete from post where pid = %d", pid);
			cons.ConsoleDB.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("PostDAO.deletePost() 오류");
			e.printStackTrace();
		}
	}
	
	public static void updatePostTitle(String input_title, int pid) {
		try {
			String sql = String.format("update post set title = '%s' where pid = %d", input_title, pid);
			cons.ConsoleDB.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("PostDAO.updatePostTitle() 오류");
			e.printStackTrace();
		}
	}
	
	public static void updatePostContents(String input_title, int pid) {
		try {
			String sql = String.format("update post set content = '%s' where pid = %d", input_title, pid);
			cons.ConsoleDB.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("PostDAO.updatePostContents() 오류");
			e.printStackTrace();
		}
	}
}
