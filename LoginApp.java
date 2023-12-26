package com.example.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginApp extends Application {

    private Button loginButton;
    private TextField usernameInput;
    private PasswordField passwordInput;
    static public String currentusername="";
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Login");
        Image icon = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\loginsymbol.png"); // Replace 'path/to/your/icon.png' with the actual path to your icon file
        primaryStage.getIcons().add(icon);
        // Load the background image
        Image backgroundImage = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\university-background.jpg");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(2000); // Set the width of the image as per your requirement
        backgroundView.setFitHeight(1000); // Set the width of the image as per your requirement

        // Create StackPane for background and login fields
        StackPane stackPane = new StackPane();

        // Add background image to the bottom layer of StackPane
        stackPane.getChildren().add(backgroundView);

        // Create the login button, username, and password fields
        loginButton = new Button("Login");
        loginButton.setStyle("-fx-text-fill: black; -fx-background-color: #817C7B;-fx-font-size:15;-fx-font-weight: bold;");
        loginButton.setDisable(true); // Initially disable the button
        loginButton.setOnAction(e -> onLogin());
        usernameInput = new TextField();
        usernameInput.setPromptText("Enter your username");
        usernameInput.setMinWidth(250);
        usernameInput.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        usernameInput.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter your password");
        passwordInput.setMinWidth(250);
        passwordInput.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        passwordInput.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        // Create labels for username and password
        Label usernameLabel = new Label("Student Id:");
        usernameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Create GridPane for login elements and set positions
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10); // Vertical gap between elements
        gridPane.setHgap(10); // Horizontal gap between elements
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameInput, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordInput, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.setAlignment(Pos.CENTER);

        // Add login elements above the background
        stackPane.getChildren().add(gridPane);

        // Create the top-right logo
        Image topRightLogo = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\fci_en.png");
        ImageView topRightView = new ImageView(topRightLogo);
        StackPane.setAlignment(topRightView, Pos.TOP_RIGHT);
        stackPane.getChildren().add(topRightView);

        // Create the bottom-left logo
        Image bottomLeftLogo = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\uni_en.png");
        ImageView bottomLeftView = new ImageView(bottomLeftLogo);
        StackPane.setAlignment(bottomLeftView, Pos.BOTTOM_LEFT);
        stackPane.getChildren().add(bottomLeftView);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        Scene scene = new Scene(stackPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void checkFields() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        boolean isFilled = !username.isEmpty() && !password.isEmpty();
        loginButton.setDisable(!isFilled);
        if (isFilled) {
            // Change button color to blue after fields are filled
            Platform.runLater(() -> loginButton.setStyle("-fx-background-color: #336699; -fx-text-fill: white;-fx-font-size:15; -fx-font-weight: bold;"));
        } else {
            // Reset button style if fields are empty
            Platform.runLater(() -> loginButton.setStyle("-fx-text-fill: white; -fx-background-color: #817C7B;-fx-font-size:15; -fx-font-weight: bold;"));
        }
    }

    private void onLogin() {
        String id = usernameInput.getText();
        String password = passwordInput.getText();
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/login";
        String dbUsername = "root";
        String dbPassword = ""; // Change this to your database password
        currentusername=usernameInput.getText();
        try {
            // Establish a connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Prepared statement to prevent SQL injection
            PreparedStatement preparedStatement = con.prepareStatement("SELECT password FROM user WHERE id = ?");
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("User Not Found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username or password is incorrect");
                alert.show();
            } else {
                while (rs.next()) {
                    String retrievedPassword = rs.getString("password");
                    if (retrievedPassword.equals(password)) {
                        goToDashboardtPage();
                        primaryStage.close();
                        // Open new stage or perform further actions here upon successful login
                    } else {
                        System.out.println("Password incorrect");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Username or password is incorrect");
                        alert.show();
                    }
                }
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void goToDashboardtPage() {

        Dashboard dashboardPage = new Dashboard();
        dashboardPage.start(new Stage());
        primaryStage.close();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
