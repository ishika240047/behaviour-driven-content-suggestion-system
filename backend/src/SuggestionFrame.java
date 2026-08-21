import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.net.URI;

public class SuggestionFrame extends JFrame {

    int userId;

    JComboBox<String> emotionBox;
    JComboBox<String> contentBox;

JEditorPane resultArea;
    public SuggestionFrame(int userId){

        this.userId = userId;

        setTitle("Get Suggestion");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2,10,10));

        panel.add(new JLabel("Emotion:"));

emotionBox = new JComboBox<>();

try {

    Connection con = DBConnection.getConnection();

    String sql = "SELECT EMOTION_NAME FROM EMOTIONS";
    PreparedStatement ps = con.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();

    while(rs.next()){
        emotionBox.addItem(rs.getString("EMOTION_NAME"));
    }

} catch(Exception ex){
    ex.printStackTrace();
}
        panel.add(emotionBox);

        panel.add(new JLabel("Content Type:"));

contentBox = new JComboBox<>();

try {

    Connection con = DBConnection.getConnection();

    String sql = "SELECT CONTENT_NAME FROM CONTENT_TYPES";
    PreparedStatement ps = con.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();

    while(rs.next()){
        contentBox.addItem(rs.getString("CONTENT_NAME"));
    }

} catch(Exception ex){
    ex.printStackTrace();
}
        panel.add(contentBox);

        JButton getSuggestionButton = new JButton("Get Suggestion");
        panel.add(getSuggestionButton);

        JButton closeButton = new JButton("Close");
        panel.add(closeButton);

        add(panel,BorderLayout.NORTH);

       resultArea = new JEditorPane();
resultArea.setEditable(false);
resultArea.setContentType("text/html");

add(new JScrollPane(resultArea),BorderLayout.CENTER);

           resultArea.addHyperlinkListener(e -> {

    if(e.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED){

        try{
            Desktop.getDesktop().browse(new URI(e.getURL().toString()));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }

});

        /* BUTTON ACTION */

        getSuggestionButton.addActionListener(e -> {

            try{

                String emotion = emotionBox.getSelectedItem().toString();
                String content = contentBox.getSelectedItem().toString();

                Connection con = DBConnection.getConnection();

                ResultSet rs = SuggestionService.getSuggestions(con,emotion,content);

resultArea.setText("");

boolean found = false;

StringBuilder htmlContent = new StringBuilder();
htmlContent.append("<html>");

String emotionId = null;
String contentId = null;

while(rs.next()){

    found = true;

    String title = rs.getString("title");
    String link = rs.getString("link");

    emotionId = rs.getString("EMOTION_ID");
    contentId = rs.getString("CONTENT_ID");

    htmlContent.append("<b>Title:</b> ")
               .append(title)
               .append("<br>")
               .append("<a href='")
               .append(link)
               .append("'>")
               .append(link)
               .append("</a><br><br>");
}

htmlContent.append("</html>");

if(found){

    resultArea.setText(htmlContent.toString());

    int historyId = HistoryService.startSession(con,userId,emotionId,contentId);

    JOptionPane.showMessageDialog(this,"Click OK when you finish viewing suggestions");

    HistoryService.endSession(con,historyId);

}
else{

    resultArea.setText("No suggestions found.");

}

            }
            catch(Exception ex){
                ex.printStackTrace();
            }

        });

        closeButton.addActionListener(e -> dispose());

        setVisible(true);

    }

}