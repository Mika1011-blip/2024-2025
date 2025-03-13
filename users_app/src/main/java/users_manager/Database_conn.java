package users_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database_conn {
    public static Connection connect(){
        String url = "jdbc:mysql://localhost:3306/user_manager"; // Replace "user_manager" with your database name
        String username = "root";
        String pswd = ""; 
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(url, username, pswd);
            System.out.println("Connected to the database");
            return conn;
        } catch(SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        } 
        return null;
    }
}
