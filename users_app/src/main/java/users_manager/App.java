package users_manager;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

public class App extends Application 
{
    private String[] options_str = {"List Users", "Add User", "Delete User", "Edit User", "Exit","Submit"};

    private int button_positionX = 10;
    private int button_positionY = 0;
    private int button_pacing = 30;
    private int text_positionY = 200;
    private int text_positionX = 250;
    
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        Text text = new Text();

        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 16px;");
        
        // ✅ Center text on the Y-axis and position on the right side
        text.setTranslateX(text_positionX);  // ✅ Move text to the right side
        text.setTranslateY(text_positionY);  // ✅ Centered on Y-axis (relative to window height)

        Image pixel_man = new Image("/pixel_man.png");
        
        stage.getIcons().add(pixel_man);
        stage.setTitle("Users Manager App");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setResizable(false);

        // ✅ Buttons on the left side, centered on the Y-axis
        Button button_listUsers = new Button(options_str[0]);
        button_listUsers.setLayoutX(button_positionX); // ✅ Move buttons to the left
        button_listUsers.setLayoutY(button_positionY + button_pacing*1); // ✅ Centered on Y-axis

        Button button_addUser = new Button(options_str[1]);
        button_addUser.setLayoutX(button_positionX);
        button_addUser.setLayoutY(button_positionY + button_pacing*2);
        
        Button button_deleteUser = new Button(options_str[2]);
        button_deleteUser.setLayoutX(button_positionX);
        button_deleteUser.setLayoutY(button_positionY + button_pacing*3);
        
        Button button_editUser = new Button(options_str[3]);
        button_editUser.setLayoutX(button_positionX);
        button_editUser.setLayoutY(button_positionY + button_pacing*4);
        
        Button button_exit = new Button(options_str[4]);
        button_exit.setLayoutX(button_positionX);
        button_exit.setLayoutY(button_positionY + button_pacing*10);

        Button button_submit = new Button(options_str[5]);
        button_submit.setLayoutX(button_positionX);
        button_submit.setLayoutY(button_positionY + button_pacing*4);
        button_submit.setVisible(false);

        TextField id_textfield = new TextField();
        id_textfield.setPromptText("Input Id Here");
        id_textfield.setLayoutX(button_positionX);
        id_textfield.setLayoutY(button_positionY + button_pacing*1);
        id_textfield.setVisible(false);

        TextField name_textfield = new TextField();
        name_textfield.setPromptText("Input Your Name Here");
        name_textfield.setLayoutX(button_positionX);
        name_textfield.setLayoutY(button_positionY + button_pacing*2);
        name_textfield.setVisible(false);

        TextField email_textfield = new TextField();
        email_textfield.setPromptText("Input Your Email Here");
        email_textfield.setLayoutX(button_positionX);
        email_textfield.setLayoutY(button_positionY + button_pacing*3);
        email_textfield.setVisible(false);


        button_listUsers.setOnAction(event ->{
            text.setText(App_services.list_users());
        });

        button_addUser.setOnAction(event ->{
            text.setText("Adding User...");
            button_listUsers.setVisible(false);
            button_addUser.setVisible(false);
            button_deleteUser.setVisible(false);
            button_editUser.setVisible(false);
            name_textfield.setVisible(true);
            email_textfield.setVisible(true);
            button_submit.setVisible(true);

            button_submit.setOnAction(SubmitEvent -> {
                String name = name_textfield.getText();
                String email = email_textfield.getText();
                App_services.add_user(name, email);
                button_listUsers.setVisible(true);
                button_addUser.setVisible(true);
                button_deleteUser.setVisible(true);
                button_editUser.setVisible(true);
                name_textfield.setVisible(false);
                email_textfield.setVisible(false);
                button_submit.setVisible(false);
                text.setText("Added User " + name + "\nwith email " + email);
            });
        });

        button_deleteUser.setOnAction(event ->{
            text.setText("Deleting User...");
            button_listUsers.setVisible(false);
            button_addUser.setVisible(false);
            button_deleteUser.setVisible(false);
            button_editUser.setVisible(false);
            id_textfield.setVisible(true);
            button_submit.setVisible(true);

            button_submit.setOnAction(SubmitEvent ->{
                try {
                    int id = Integer.parseInt(id_textfield.getText());
                    App_services.delete_user(id);
                    button_listUsers.setVisible(true);
                    button_addUser.setVisible(true);
                    button_deleteUser.setVisible(true);
                    button_editUser.setVisible(true);
                    id_textfield.setVisible(false);
                    button_submit.setVisible(false);
                    text.setText("Deleted User with id " + id);
                    } catch (NumberFormatException e) {
                        text.setText("❌ Invalid number! Please enter digits only.");
                }
            });

        });   

        button_editUser.setOnAction(event ->{
            text.setText("Editing User...");
            button_listUsers.setVisible(false);
            button_addUser.setVisible(false);
            button_deleteUser.setVisible(false);
            button_editUser.setVisible(false);
            id_textfield.setVisible(true);
            name_textfield.setVisible(true);
            email_textfield.setVisible(true);
            button_submit.setVisible(true);
            button_submit.setOnAction(SubmitEvent ->{
                try {
                    int id = Integer.parseInt(id_textfield.getText());
                    String name = name_textfield.getText();
                    String email = email_textfield.getText();
                    App_services.edit_user(id, name, email);
                    button_listUsers.setVisible(true);
                    button_addUser.setVisible(true);
                    button_deleteUser.setVisible(true);
                    button_editUser.setVisible(true);
                    id_textfield.setVisible(false);
                    name_textfield.setVisible(false);
                    email_textfield.setVisible(false);
                    button_submit.setVisible(false);
                    text.setText("Edited User with id " + id);
                    } catch (NumberFormatException e) {
                        text.setText("❌ Invalid number! Please enter digits only.");
                        }
                    });

        });

        button_exit.setOnAction(event ->{
            System.exit(0);
        });

        // ✅ Add elements to the root
        root.getChildren().addAll(button_listUsers,
                                button_addUser,
                                button_deleteUser,
                                button_editUser,
                                button_exit,
                                button_submit,
                                text,
                                id_textfield,
                                name_textfield,
                                email_textfield);

        stage.setScene(scene);
        stage.show();
    }
}
