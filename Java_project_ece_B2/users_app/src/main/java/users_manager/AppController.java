package users_manager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.geometry.Insets;

public class AppController {

    @FXML private TextField addNameField;
    @FXML private TextField addEmailField;
    @FXML private Button addUserButton;
    
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button refreshButton;
    
    @FXML private TableView<User> tableView;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, Void> editColumn;
    @FXML private TableColumn<User, Void> deleteColumn;
    
    private ObservableList<User> userList;
    
    @FXML
    public void initialize() {
        // Set up the columns for data binding
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        // Configure custom columns with buttons
        addEditButtonToTable();
        addDeleteButtonToTable();
        
        // Load the initial data into the table
        refreshTable();
    }
    
    @FXML
    private void handleAddUser(ActionEvent event) {
        String name = addNameField.getText().trim();
        String email = addEmailField.getText().trim();
        if (!name.isEmpty() && !email.isEmpty()) {
            App_services.add_user(name, email);
            addNameField.clear();
            addEmailField.clear();
            refreshTable();
        }
    }
    
    @FXML
    private void handleSearchUser(ActionEvent event) {
        String email = searchField.getText().trim();
        if (!email.isEmpty()) {
            User user = App_services.getUserByEmail(email);
            if (user != null) {
                tableView.setItems(FXCollections.observableArrayList(user));
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("No user found with email: " + email);
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    private void handleRefresh(ActionEvent event) {
        refreshTable();
    }
    
    private void refreshTable() {
        userList = App_services.getAllUsers();
        tableView.setItems(userList);
    }
    
    private void addEditButtonToTable() {
        editColumn.setCellFactory(col -> new TableCell<User, Void>() {
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
    }
    
    private void addDeleteButtonToTable() {
        deleteColumn.setCellFactory(col -> new TableCell<User, Void>() {
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
    }
    
    private void showEditDialog(User user) {
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
            if (!newName.isEmpty() && !newEmail.isEmpty()){
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
