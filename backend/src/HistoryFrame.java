import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class HistoryFrame extends JFrame {

    int userId;

    JTable table;
    DefaultTableModel model;

    public HistoryFrame(int userId) {

        this.userId = userId;

        setTitle("User History");
        setSize(600,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();

        model.addColumn("Emotion");
        model.addColumn("Content");
        model.addColumn("Start Time");
        model.addColumn("End Time");
        model.addColumn("Time Spent");

        table = new JTable(model);

        add(new JScrollPane(table));

        loadHistory();

        setVisible(true);
    }

    private void loadHistory() {

        try {

            Connection con = DBConnection.getConnection();

            String sql =
            "SELECT e.emotion_name, c.content_name, h.start_time, h.end_time, h.time_spent_min " +
            "FROM USER_HISTORY h " +
            "JOIN EMOTIONS e ON h.emotion_id = e.emotion_id " +
            "JOIN CONTENT_TYPES c ON h.content_id = c.content_id " +
            "WHERE h.user_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,userId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                model.addRow(new Object[]{
                        rs.getString("emotion_name"),
                        rs.getString("content_name"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time"),
                        rs.getInt("time_spent_min")
                });

            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

}