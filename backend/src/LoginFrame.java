import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class LoginFrame extends JFrame {

    JTextField emailField;
    JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Emotrack Login");
        setSize(350,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2,10,10));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        /*REGISTER BUTTON ACTION */
        registerButton.addActionListener(e -> {

    dispose();          // closes login window
    new RegisterFrame(); // opens register window

});
        /* LOGIN BUTTON ACTION */

        loginButton.addActionListener(e -> {  // this e is not an exception it is a event listed in java.awt.event and we are currently in lambda function

            try {

                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                Connection con = DBConnection.getConnection();

                int userId = AuthService.login(con,email,password);

                if(userId != -1){

                    JOptionPane.showMessageDialog(this,"Login Successful!");

                    new MenuFrame(userId);
                    dispose();

                }
                else{
                    JOptionPane.showMessageDialog(this,"Invalid credentials");
                }

            }
            catch(Exception ex){
                ex.printStackTrace();
            }

        });

        setVisible(true);
    }

}