package com.example.demo;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Dashboard extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        this.primaryStage = primaryStage;
        Image backgroundImage = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\university-background.jpg");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(2000); // Set the width of the image as per your requirement
        backgroundView.setFitHeight(1000); // Set the width of the image as per your requirement
        Button button1=new Button("Student Details");
        button1.setPrefWidth(200); // Set preferred width
        button1.setPrefHeight(60);
        button1.setOnAction(e ->goToStudentSplashScreen());
        button1.setStyle("-fx-background-color: gray; " + // Custom background color
                "-fx-text-fill: white; " + // Text color
                "-fx-border-radius: 10; " + // Border radius
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;"); // Font size
        Button button2=new Button("Calculate GPA");
        button2.setPrefWidth(200); // Set preferred width
        button2.setPrefHeight(60);
        button2.setOnAction(e ->goToCalcGpaPage());
        button2.setStyle("-fx-background-color: gray; " +
                "-fx-text-fill: white; " +
                "-fx-border-radius: 10; " +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;");
        Button button3=new Button("Exams Date");
        button3.setPrefWidth(200); // Set preferred width
        button3.setPrefHeight(60);
        button3.setStyle("-fx-background-color: gray; " + // Aqua color
                "-fx-text-fill: white; " + // Text color
                "-fx-border-radius: 10; " + // Border radius
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;");
        button3.setOnAction(e ->goToCoursesCodes());
        Button button4=new Button("Logout");
        button4.setPrefWidth(200); // Set preferred width
        button4.setPrefHeight(60);
        button4.setStyle("-fx-background-color: gray; " + // Aqua color
                "-fx-text-fill: white; " + // Text color
                "-fx-border-radius: 10; " + // Border radius
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;");
        button4.setOnAction(e ->logout(button4));
        VBox menuButtons = new VBox(130);
        menuButtons.setPadding(new Insets(0,8,0,0));
        menuButtons.setAlignment(Pos.CENTER);
        menuButtons.getChildren().addAll(
                button1,
                button2,
                button3,
                button4
        );

        root.getChildren().addAll(backgroundView, menuButtons);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Centered Menu Buttons");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button button = new Button(text);
        button.setPrefWidth(200); // Set preferred width
        button.setPrefHeight(60);
        button.setOnAction(handler);
        button.setStyle("-fx-background-color: gray; " +
                "-fx-text-fill: white; " +
                "-fx-border-radius: 10; " +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;");
        return button;
    }

    private void handleExtraAction() {
        // Handle the action of the extra button
        System.out.println("Extra button action performed!");
    }

    private void logout(Button button) {
        // Perform logout actions here, such as closing the current window or showing a login screen
        System.out.println("Logout action performed!");
        // For example:
        LoginApp loginApp = new LoginApp();
        loginApp.start(new Stage());
        primaryStage.close();
        // Or you can open a login window:
        // LoginWindow loginWindow = new LoginWindow();
        // loginWindow.show();
    }

    private void goToStudentSplashScreen() {
        SplashScreenStudentDetails studentPage = new SplashScreenStudentDetails();
        studentPage.start(new Stage());
        primaryStage.close();
    }

    private void goToCalcGpaPage() {
        SplashScreenGpaCalc calculatorPage = new SplashScreenGpaCalc();
        calculatorPage.start(new Stage());
        primaryStage.close();
    }
    private void goToCoursesCodes() {
        SplashScreenCoursesCode coursesCode = new SplashScreenCoursesCode();
        coursesCode.start(new Stage());
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
