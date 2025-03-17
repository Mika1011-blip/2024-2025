package users_manager;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;

public class App extends Application {
    private TableView<User> tableView;
    private ObservableList<User> userList;
    private TextField addNameField, addEmailField;
    private TextField searchField;
    
    public static void main(String[] args) {
         launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        Image pixel_man = new Image("/pixel_man.png");
        
        stage.getIcons().add(pixel_man);
        stage.setTitle("Users Manager App");

         // Main layout container
         VBox root = new VBox(10);
         root.setPadding(new Insets(10));
         
         // Add User Form
         HBox addUserBox = new HBox(10);
         addNameField = new TextField();
         addNameField.setPromptText("Name");
         addEmailField = new TextField();
         addEmailField.setPromptText("Email");
         Button addUserButton = new Button("Add User");
         addUserBox.getChildren().addAll(new Label("Add User:"), addNameField, addEmailField, addUserButton);
         
         // Find User by Email
         HBox searchBox = new HBox(10);
         searchField = new TextField();
         searchField.setPromptText("Enter Email to Search");
         Button searchButton = new Button("Search");
         Button refreshButton = new Button("Refresh");
         searchBox.getChildren().addAll(new Label("Find User:"), searchField, searchButton, refreshButton);
         
         // TableView to display users
         tableView = new TableView<>();
         TableColumn<User, Integer> idCol = new TableColumn<>("ID");
         idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
         TableColumn<User, String> nameCol = new TableColumn<>("Name");
         nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
         TableColumn<User, String> emailCol = new TableColumn<>("Email");
         emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
         
         // Edit Button Column
         TableColumn<User, Void> editCol = new TableColumn<>("Edit");
         editCol.setCellFactory(col -> new TableCell<User, Void>() {
             private final Button editButton = new Button("Edit");
             
             {
                editButton.setOnAction(event -> {
                     User user = getTableView().getItems().get(getIndex());
                     showEditDialog(user);
                });
             }
             
             @Override
             protected void updateItem(Void item, boolean empty) {
                 super.updateItem(item, empty);
                 setGraphic(empty ? null : editButton);
             }
         });
         
         // Delete Button Column
         TableColumn<User, Void> deleteCol = new TableColumn<>("Delete");
         deleteCol.setCellFactory(col -> new TableCell<User, Void>() {
             private final Button deleteButton = new Button("Delete");
             
             {
                deleteButton.setOnAction(event -> {
                     User user = getTableView().getItems().get(getIndex());
                     App_services.delete_user(user.getId());
                     refreshTable();
                });
             }
             
             @Override
             protected void updateItem(Void item, boolean empty) {
                 super.updateItem(item, empty);
                 setGraphic(empty ? null : deleteButton);
             }
         });
         
         tableView.getColumns().addAll(idCol, nameCol, emailCol, editCol, deleteCol);
         refreshTable();
         
         // Add User Button functionality
         addUserButton.setOnAction(event -> {
             String name = addNameField.getText().trim();
             String email = addEmailField.getText().trim();
             if(!name.isEmpty() && !email.isEmpty()){
                 App_services.add_user(name, email);
                 addNameField.clear();
                 addEmailField.clear();
                 refreshTable();
             }
         });
         
         // Search Button functionality
         searchButton.setOnAction(event -> {
             String email = searchField.getText().trim();
             if(!email.isEmpty()){
                 User user = App_services.getUserByEmail(email);
                 if(user != null){
                     tableView.setItems(FXCollections.observableArrayList(user));
                 } else {
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Search Result");
                     alert.setHeaderText(null);
                     alert.setContentText("No user found with email: " + email);
                     alert.showAndWait();
                 }
             }
         });
         
         // Refresh Button resets the table to show all users
         refreshButton.setOnAction(event -> refreshTable());
         
         // Assemble the layout
         root.getChildren().addAll(addUserBox, searchBox, tableView);
         
         Scene scene = new Scene(root, 600, 400);
         stage.setScene(scene);
         stage.setTitle("Users Manager App");
         stage.show();
    }
    
    // Reloads user data into the table
    private void refreshTable(){
         userList = App_services.getAllUsers();
         tableView.setItems(userList);
    }
    
    // Opens a dialog for editing user details
    private void showEditDialog(User user){
         Stage dialog = new Stage();
         dialog.initModality(Modality.APPLICATION_MODAL);
         dialog.setTitle("Edit User");
         
         VBox dialogVBox = new VBox(10);
         dialogVBox.setPadding(new Insets(10));
         
         TextField nameField = new TextField(user.getName());
         TextField emailField = new TextField(user.getEmail());
         Button updateButton = new Button("Update");
         
         updateButton.setOnAction(e -> {
             String newName = nameField.getText().trim();
             String newEmail = emailField.getText().trim();
             if(!newName.isEmpty() && !newEmail.isEmpty()){
                 App_services.edit_user(user.getId(), newName, newEmail);
                 dialog.close();
                 refreshTable();
             }
         });
         
         dialogVBox.getChildren().addAll(new Label("Name:"), nameField, new Label("Email:"), emailField, updateButton);
         Scene dialogScene = new Scene(dialogVBox, 300, 200);
         dialog.setScene(dialogScene);
         dialog.showAndWait();
    }
}
