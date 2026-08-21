import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {
        String dbHost = System.getenv("DB_HOST");
        if (dbHost == null) {
            dbHost = "localhost";
        }
        String dbPort = System.getenv("DB_PORT");
        if (dbPort == null) {
            dbPort = "5432";
        }
        String dbName = System.getenv("DB_NAME");
        if (dbName == null) {
            dbName = "emotrack";
        }
        String dbUser = System.getenv("DB_USER");
        if (dbUser == null) {
            dbUser = "postgres";
        }
        String dbPassword = System.getenv("DB_PASSWORD");
        if (dbPassword == null) {
            dbPassword = "password123";
        }

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(
            "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName,
            dbUser,
            dbPassword
        );
    }
}
