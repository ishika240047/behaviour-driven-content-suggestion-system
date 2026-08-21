import java.sql.*;

public class AuthService {

    public static int login(Connection con, String email, String password) throws Exception {

        String sql = "SELECT USER_ID FROM USERS WHERE EMAIL=? AND PASSWORD=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("USER_ID");
        }

        return -1;
    }
    public static void register(Connection con, String name, String email, String password) throws Exception {

    String checkSql = "SELECT USER_ID FROM USERS WHERE EMAIL=?";
    PreparedStatement psCheck = con.prepareStatement(checkSql);
    psCheck.setString(1, email);

    ResultSet rs = psCheck.executeQuery();

    if (rs.next()) {
        System.out.println("Email already registered.");
        return;
    }

    String sql = "INSERT INTO USERS VALUES (USER_SEQ.NEXTVAL, ?, ?, ?)";

    PreparedStatement ps = con.prepareStatement(sql);
    ps.setString(1, name);
    ps.setString(2, email);
    ps.setString(3, password);

    ps.executeUpdate();

    System.out.println("Registration successful!");
}

}