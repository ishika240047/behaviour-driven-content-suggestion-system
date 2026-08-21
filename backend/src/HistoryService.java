import java.sql.*;

public class HistoryService {

    
    public static int startSession(Connection con, int userId, String emotionId, String contentId) throws Exception {

        String seqQuery = "SELECT nextval('HISTORY_SEQ')";
        PreparedStatement psSeq = con.prepareStatement(seqQuery);
        ResultSet rsSeq = psSeq.executeQuery();

        int historyId = 0;

        if (rsSeq.next()) {
            historyId = rsSeq.getInt(1);
        }

        String insertSql =
                "INSERT INTO USER_HISTORY " +
                "(HISTORY_ID, USER_ID, EMOTION_ID, CONTENT_ID, START_TIME) " +
                "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";

        PreparedStatement psInsert = con.prepareStatement(insertSql);

        psInsert.setInt(1, historyId);
        psInsert.setInt(2, userId);
        psInsert.setString(3, emotionId);
        psInsert.setString(4, contentId);

        psInsert.executeUpdate();

        return historyId;
    }


    // END SESSION
    public static void endSession(Connection con, int historyId) throws Exception {

        String updateSql =
                "UPDATE USER_HISTORY SET END_TIME = CURRENT_TIMESTAMP, " +
                "TIME_SPENT_MIN = EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - START_TIME)) / 60 " +
                "WHERE HISTORY_ID = ?";

        PreparedStatement psUpdate = con.prepareStatement(updateSql);

        psUpdate.setInt(1, historyId);

        psUpdate.executeUpdate();
    }
}