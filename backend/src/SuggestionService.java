import java.sql.*;

public class SuggestionService {

    public static ResultSet getSuggestions(Connection con, String emotion, String contentType) throws Exception {

        String sql =
            "SELECT s.SUGGESTION_ID, s.title, s.link, s.EMOTION_ID, s.CONTENT_ID " +
            "FROM SUGGESTIONS s " +
            "JOIN EMOTIONS e ON s.EMOTION_ID = e.EMOTION_ID " +
            "JOIN CONTENT_TYPES c ON s.CONTENT_ID = c.CONTENT_ID " +
            "WHERE LOWER(e.EMOTION_NAME)=? AND LOWER(c.CONTENT_NAME)=?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, emotion);
        ps.setString(2, contentType);

        return ps.executeQuery();
    }

}
