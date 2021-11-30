package chatroom;
import java.sql.*;
import java.util.ArrayList;
import db.dbInfo;
import profile.ProfileDAO;
import profile.ProfileDTO;

public class ChatroomDAO {
   private Connection conn;
   private PreparedStatement pstmt;
   private ProfileDAO PRdao= new ProfileDAO();
   private ProfileDTO PRdto;
   
   public ChatroomDAO() {
      try {   
         dbInfo db = new dbInfo();
         Class.forName(db.getDriverName());
         conn = DriverManager.getConnection(db.getDbURL(), db.getDbID(), db.getDbPassword());
         System.out.println("DB에 연결 되었습니다.\n");
         
      }catch(ClassNotFoundException e) {
         System.out.println("DB 드라이버 로딩 실패 :" +e.toString());
      }catch(SQLException sqle) {
         System.out.println("DB 접속실패 :"+sqle.toString());
      }catch(Exception e) {
         System.out.println("Unkonwn error");
         e.printStackTrace();
      }
   }
   
   public ChatroomDAO(ProfileDTO dto) {
      // TODO Auto-generated constructor stub
      PRdto = new ProfileDTO(dto);
      try {   
         dbInfo db = new dbInfo();
         Class.forName(db.getDriverName());
         conn = DriverManager.getConnection(db.getDbURL(), db.getDbID(), db.getDbPassword());
         System.out.println("DB에 연결 되었습니다.\n");
         
      }catch(ClassNotFoundException e) {
         System.out.println("DB 드라이버 로딩 실패 :" +e.toString());
      }catch(SQLException sqle) {
         System.out.println("DB 접속실패 :"+sqle.toString());
      }catch(Exception e) {
         System.out.println("Unkonwn error");
         e.printStackTrace();
      }
   }
   
   public ChatroomDTO getChatroomDTO(int CRid) {
      ChatroomDTO cDTO = null;
      try {
         String sql = "SELECT * FROM CHAT_ROOM WHERE CRid = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, CRid);
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
            int CRId = rs.getInt("CRId");
            int Host_id = rs.getInt("Host_id");
            int participant_id = rs.getInt("Participant_id");
            String CRname = rs.getString("CRname");
            cDTO = new ChatroomDTO(CRId, Host_id, participant_id,CRname);
         }
      } catch (SQLException e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
      } catch (Exception e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
         System.out.println("Error in getFriendRequestList Function");
      }
      return cDTO;
   }
   
   public ArrayList<ChatroomDTO> GetChatroomList(){
      ArrayList<ChatroomDTO> list = new ArrayList<ChatroomDTO>();
      try {
         String sql = "SELECT * FROM CHAT_ROOM WHERE Host_id = ? OR Participant_id = ?";
         int user_id = PRdto.getPRid();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, user_id);
         pstmt.setInt(2, user_id);
         ResultSet rs = pstmt.executeQuery();

         while (rs.next()) {
            int CRid = rs.getInt("CRId");
            int Host_id = rs.getInt("Host_id");
            int participant_id = rs.getInt("Participant_id");
            String CRname = rs.getString("CRname");
            list.add(new ChatroomDTO(CRid, Host_id, participant_id,CRname));
         }
      } catch (SQLException e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
      } catch (Exception e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
         System.out.println("Error in getFriendRequestList Function");
      }
      
      return list;
   }
   
   public boolean DeleteChatroom(int user_id, int op_user_id) {
      // TODO Auto-generated method stub
      boolean result = false;
      try {
         String sql = "DELETE FROM CHAT_ROOM WHERE (Host_id = ? AND Participant_id = ?) OR (Host_id = ? AND Participant_id = ?)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, user_id);
         pstmt.setInt(2, op_user_id);
         pstmt.setInt(3, op_user_id);
         pstmt.setInt(4, user_id);
         int rs = pstmt.executeUpdate();
         if(rs > 0)
            result = true;   
      } catch (SQLException e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
      } catch (Exception e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
         System.out.println("Error in getFriendRequestList Function");
      }
      return result;
   }
   
   public boolean CreateChatroom(ChatroomDTO dto) {
      // TODO Auto-generated method stub
      boolean result = false;
      try {
         String sql = "INSERT INTO CHAT_ROOM VALUES (Chat_room_SEQ.NEXTVAL, ?, ?, ?)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, dto.getHost_id());
         pstmt.setInt(2, dto.getParticipant_id());
         pstmt.setString(3, dto.getCRname());
         int rs = pstmt.executeUpdate();
         if(rs > 0)
            result = true;   
      } catch (SQLException e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
      } catch (Exception e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
         System.out.println("Error in getFriendRequestList Function");
      }
      return result;
   }
   
   public boolean hasChatroom(int op_prid) {
      boolean result = false;
      try {
         String sql = "SELECT * FROM CHAT_ROOM WHERE Host_id = ? AND Participant_id = ? OR Host_id = ? AND Participant_id = ?";
         int PRId = PRdto.getPRid();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, PRId);
         pstmt.setInt(2, op_prid);
         pstmt.setInt(3, op_prid);
         pstmt.setInt(4, PRId);
         int rs = pstmt.executeUpdate();
         if(rs > 0)
            result = true;
      } catch (SQLException e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
      } catch (Exception e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
         System.out.println("Error in getFriendRequestList Function");
      }
      return result;
   }
   
   public boolean hasChatroom_OP(String nickname) {
      boolean result = false;
      try {
         String sql = "WITH IDT AS (SELECT Host_id, Participant_id FROM CHAT_ROOM WHERE Host_id = ? OR Participant_id = ?) (SELECT Host_id  AS id FROM IDT,PROFILE WHERE Host_id = PRid AND Nickname = ? ) UNION ALL (SELECT Participant_id AS id FROM IDT,PROFILE WHERE Participant_id = PRid AND Nickname = ?)";
         int PRId = PRdto.getPRid();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, PRId);
         pstmt.setInt(2, PRId);
         pstmt.setString(3, nickname);
         pstmt.setString(4, nickname);
         int rs = pstmt.executeUpdate();
         if(rs > 0)
            result = true;
      } catch (SQLException e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
      } catch (Exception e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
         System.out.println(e.getLocalizedMessage());
         System.out.println("Error in getFriendRequestList Function");
      }
      return result;
   }
}