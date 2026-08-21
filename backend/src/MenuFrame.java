import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    int userId;

    public MenuFrame(int userId){

        this.userId = userId;

        setTitle("Emotrack Menu");
        setSize(300,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Emotrack Dashboard", SwingConstants.CENTER);
title.setFont(new Font("Arial", Font.BOLD, 18));

JPanel panel = new JPanel();
panel.setLayout(new GridLayout(3,1,10,10));



        JButton suggestionButton = new JButton("Get Suggestion");
        suggestionButton.addActionListener(e -> {

              new SuggestionFrame(userId);

                                                });
        JButton historyButton = new JButton("View History");
        historyButton.addActionListener(e -> {

              new HistoryFrame(userId);

                                             });
        JButton logoutButton = new JButton("Logout");
panel.add(suggestionButton);
panel.add(historyButton);
panel.add(logoutButton);

setLayout(new BorderLayout());
add(title, BorderLayout.NORTH);
add(panel, BorderLayout.CENTER);

        logoutButton.addActionListener(e -> {

            new LoginFrame();
            dispose();

        });

        setVisible(true);
    }
}