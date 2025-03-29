package users_manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class App_services {
    public static User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = Database_conn.connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String userEmail = rs.getString("email");
                return new User(id, name, userEmail);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM users";
        try (Connection conn = Database_conn.connect();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                users.add(new User(id, name, email));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return users;
    }

    public static void add_user(String input_name, String input_email) {
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection conn = Database_conn.connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, input_name);
            stmt.setString(2, input_email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void delete_user(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = Database_conn.connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void edit_user(int id, String input_name, String input_email) {
        String query = "Update users Set name = ?,email = ? where id = ?";
        try (Connection conn = Database_conn.connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, input_name);
            stmt.setString(2, input_email);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
