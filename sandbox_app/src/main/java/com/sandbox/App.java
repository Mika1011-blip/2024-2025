package com.sandbox;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

public class App extends Application 
{
    private boolean isFullScreen = false;
    private String[] options_str = {"List Users","Add User","Delete User","Edit User","Exit"};

    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);

        Image pixel_man = new Image("/pixel_man.png");
        
        stage.getIcons().add(pixel_man);
        stage.setTitle("Users Manager App");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setResizable(false);

        //stage.setFullScreen(true);

        Button button_listUsers = new Button(options_str[0]);
        button_listUsers.setLayoutX(100);
        button_listUsers.setLayoutY(100);
        Button button_addUser = new Button(options_str[1]);
        button_addUser.setLayoutX(100);
        button_addUser.setLayoutY(150);
        Button button_deleteUser = new Button(options_str[2]);
        button_deleteUser.setLayoutX(100);
        button_deleteUser.setLayoutY(200);
        Button button_editUser = new Button(options_str[3]);
        button_editUser.setLayoutX(100);
        button_editUser.setLayoutY(250);
        Button button_exit = new Button(options_str[4]);
        button_exit.setLayoutX(100);
        button_exit.setLayoutY(300);
/* 
        toggleButton.setOnAction(event -> {
            isFullScreen = !isFullScreen; // Toggle state
            stage.setFullScreen(isFullScreen); // Apply full-screen mode
        });
        */

        button_exit.setOnAction(event ->{
            System.exit(0);
        });
        

        root.getChildren().add(button_listUsers);
        root.getChildren().add(button_addUser);
        root.getChildren().add(button_deleteUser);
        root.getChildren().add(button_editUser);
        root.getChildren().add(button_exit);

        stage.setScene(scene);
        stage.show();
    }
}
