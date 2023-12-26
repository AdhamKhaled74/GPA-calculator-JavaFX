package com.example.demo;
import com.example.demo.LoginApp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SplashScreen extends Application {
    private  Stage primaryStage;
    private static final int SPLASH_DURATION = 6000; // Duration in milliseconds
    private static final int TOTAL_TASKS = 100; // Number of tasks to complete
    private int completedTasks = 0;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;
        Image backgroundImage = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\university-background.jpg"); // Replace with your image path
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(2000);
        backgroundView.setFitHeight(1000);
        Image iconImage = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\fci_en.png"); // Replace with your icon path
        ImageView iconView = new ImageView(iconImage);
        StackPane root = new StackPane();
        Label progressLabel = new Label("Loading... 0%");
        progressLabel.setStyle("-fx-font-size: 20px;");
        StackPane.setAlignment(progressLabel, Pos.CENTER);
        root.getChildren().add(progressLabel);
        VBox vbox = new VBox(10, iconView, progressLabel);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(backgroundView, vbox);
        Scene scene = new Scene(root, 800, 800);
        primaryStage.getIcons().add(new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\fci_en.png")); // Replace with your icon path
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        primaryStage.setTitle("Splash Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
        simulateLoading(progressLabel, primaryStage);
    }
    private void simulateLoading(Label progressLabel, Stage primaryStage) {
        Thread loadingThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            while (completedTasks < TOTAL_TASKS) {
                // Simulate loading tasks
                // Perform some tasks here

                // Update progress label
                completedTasks++;
                double progress = (double) completedTasks / TOTAL_TASKS * 100;
                progressLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
                updateLabel(progressLabel, "Loading.." + String.format("%.0f%%", progress));

                try {
                    Thread.sleep(SPLASH_DURATION / TOTAL_TASKS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Once tasks are completed, navigate to login screen
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime < SPLASH_DURATION) {
                try {
                    Thread.sleep(SPLASH_DURATION - elapsedTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            closeSplashScreen(primaryStage);
        });

        loadingThread.start();
    }
    private void updateLabel(Label label, String text) {
        Platform.runLater(() -> label.setText(text));
    }

    private void closeSplashScreen(Window window) {

        Platform.runLater(() -> {
            window.hide();
            LoginApp loginScreen = new LoginApp(); // Replace with your LoginScreen class
            Stage loginStage = new Stage();
            loginScreen.start(loginStage);
            this.primaryStage.close();
        });
    }
    public static void main(String[] args) {
        launch(args);
        }
}
