package Persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KetNoiCSDL_DAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/qlgdnhadat";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "17052004hung";

    // Method to get a connection to the database
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Load the database driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
