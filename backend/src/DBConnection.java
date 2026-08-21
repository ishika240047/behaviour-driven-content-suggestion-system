import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {

        return DriverManager.getConnection(
"jdbc:oracle:thin:@gogo:1521/XE",
        "system",
        "Ishu123"
        );
    }
}
