package users_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
         Parent root = FXMLLoader.load(getClass().getResource("/users_manager/App.fxml"));
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Users Manager App");
         stage.show();
    }
    
    public static void main(String[] args) {
         launch(args);
    }
}
