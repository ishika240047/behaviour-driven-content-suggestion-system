import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterFrame extends JFrame {

    JTextField nameField;
    JTextField emailField;
    JPasswordField passwordField;

    public RegisterFrame(){

        setTitle("Register");
        setSize(350,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2,10,10));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        panel.add(registerButton);
        panel.add(backButton);

        add(panel);

        /* REGISTER BUTTON */

        registerButton.addActionListener(e -> {

            try{

                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                Connection con = DBConnection.getConnection();

                // check if email already exists
                String checkSql = "SELECT USER_ID FROM USERS WHERE EMAIL = ?";
                PreparedStatement psCheck = con.prepareStatement(checkSql);
                psCheck.setString(1,email);

                ResultSet rs = psCheck.executeQuery();

                if(rs.next()){
                    JOptionPane.showMessageDialog(this,"Email already registered!");
                    return;
                }

                // insert new user
                String insertSql = "INSERT INTO USERS (USER_ID,NAME,EMAIL,PASSWORD) VALUES (USER_SEQ.NEXTVAL,?,?,?)";

                PreparedStatement psInsert = con.prepareStatement(insertSql);

                psInsert.setString(1,name);
                psInsert.setString(2,email);
                psInsert.setString(3,password);

                psInsert.executeUpdate();

                JOptionPane.showMessageDialog(this,"Registration Successful!");

                con.close();

                dispose();
                new LoginFrame();

            }
            catch(Exception ex){
                ex.printStackTrace();
            }

        });

        /* BACK BUTTON */

        backButton.addActionListener(e -> {

            dispose();
            new LoginFrame();

        });

        setVisible(true);
    }
}