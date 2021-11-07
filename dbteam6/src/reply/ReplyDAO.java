package reply;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import post.PostDTO;

public class ReplyDAO {
	public List<ReplyDTO> getReplyListInPost(int pid) {
		List<ReplyDTO> list = new ArrayList<>();

		try {
			String sql = "select * " + "from reply r where post_id = " + pid;
			ResultSet rs = cons.ConsoleDB.stmt.executeQuery(sql);
			
			while (rs.next()) {
				list.add(new ReplyDTO(rs.getInt(1), pid, rs.getInt(3), rs.getInt(4),
						rs.getString(5), rs.getTimestamp(6).toString()));;
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("ReplyDAO.getReplyListInPost() 오류");
			e.printStackTrace();
		}

		return list;
	}
	
	public static void makeReply(String input_contents, PostDTO dto) {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			String sql = String.format(
					"insert into reply values(Reply_SEQ.nextval, %d, %d, %d, '%s', to_date('%s', 'yyyy-mm-dd hh24:mi:ss'))", dto.getPid(),
					dto.getCreator_id(), cons.ConsoleDB.currentUser.getPRid(), input_contents, sdf.format(timeStamp).toString());
			cons.ConsoleDB.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("PostDAO.makePost() 오류");
			e.printStackTrace();
		}
	}
}
