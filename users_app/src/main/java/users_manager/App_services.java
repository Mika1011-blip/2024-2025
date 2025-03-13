package users_manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App_services {
    public static String list_users() {
        String query = "SELECT * FROM users";
        StringBuilder usersList = new StringBuilder();

        try (Connection conn = Database_conn.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                // ✅ Append user details, separated by \n
                usersList.append(id).append(" - ").append(name).append(" (").append(email).append(")\n");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return "Error fetching users!";
        }

        return usersList.toString(); // ✅ Return as a formatted String
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
