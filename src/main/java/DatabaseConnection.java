import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{


    private static final String URL =
            System.getenv("TASK_DB_URL");

    private static final String USER =
            System.getenv("TASK_DB_USER");

    private static final String PASSWORD =
        System.getenv("TASK_DB_PASSWORD");

    public static Connection getConnection() throws SQLException
    {

        if (URL == null || USER == null || PASSWORD == null)
        {
            throw new IllegalStateException
                    (
                            "Database environment variables are missing."
                    );
        }


        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}