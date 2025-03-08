package users_manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App_services {
    public static void list_users(){
        String query = "SELECT * FROM users";
        
        try (
            Connection conn = Database_conn.connect();
            PreparedStatement stmt = (conn != null) ? conn.prepareStatement(query) : null;
            ResultSet rs = (stmt != null) ? stmt.executeQuery() : null
        ) {
            if (rs != null) {
                while(rs.next()) {
                    System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("email"));
                }
            }
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void add_user(String input_name, String input_email){
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        try(Connection conn = Database_conn.connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, input_name);
                stmt.setString(2, input_email);
                stmt.executeUpdate();
                } catch(SQLException e) {
                    System.out.println("Error: " + e.getMessage());
        }
    }

    public static void delete_user(int id){
        String query = "DELETE FROM users WHERE id = ?";
        try(Connection conn = Database_conn.connect();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            } catch(SQLException e) {
                System.out.println("Error: " + e.getMessage());
                }
    }

    public static void edit_user(int id, String input_name, String input_email){
        String query = "Update users Set name = ?,email = ? where id = ?";
        try (Connection conn = Database_conn.connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, input_name);
                stmt.setString(2, input_email);
                stmt.setInt(3, id);
                stmt.executeUpdate();
                } catch(SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                    }
    }

}
