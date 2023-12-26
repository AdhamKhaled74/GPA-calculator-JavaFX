package com.example.demo;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Objects;

public class CourseTableFromDatabase extends Application {

    private final ObservableList<Course> courses = FXCollections.observableArrayList();
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        final int maxRows = 6;
        this.primaryStage=primaryStage;
        TableView<Course> table = new TableView<>();
        table.setRowFactory(tv -> new TableRow<Course>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    getStyleClass().add("table-row-hidden");
                } else {
                    getStyleClass().remove("table-row-hidden");
                }
            }
        });

        //

        TableColumn<Course, Integer> examidColumn = new TableColumn<>("Exam_ID");
        examidColumn.setCellValueFactory(cellData -> cellData.getValue().examidProperty().asObject());
        examidColumn.setPrefWidth(650);
        TableColumn<Course, String> courseNameColumn = new TableColumn<>("Course Name");
        courseNameColumn.setCellValueFactory(cellData -> cellData.getValue().courseNameProperty());
        courseNameColumn.setPrefWidth(650);
        TableColumn<Course, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        dateColumn.setPrefWidth(650);
        table.getColumns().addAll(examidColumn, courseNameColumn, dateColumn);
        fetchDataFromDatabase();
        table.setItems(courses);
        StackPane stackPane=new StackPane();
        Button button1=new Button("Back");
        button1.setPrefWidth(180); // Set preferred width
        button1.setPrefHeight(45);
        button1.setOnAction(e ->goToDashBoardPage());
        button1.setStyle("-fx-background-color: #817C7B; " + // Custom background color
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" + // Text color
                "-fx-border-radius: 10; " + // Border radius
                "-fx-font-size: 20px;");// Font size
        stackPane.getChildren().add(table);
        stackPane.getChildren().add(button1);


        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());

        Scene scene = new Scene(stackPane, 1000, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/demo/styles.css")).toExternalForm());
        table.getStyleClass().add("table-background");
        table.getStyleClass().add("table-cell-centered");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Course Table from Database");
        primaryStage.show();

    }

    private void fetchDataFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/login";
        String dbUsername = "root";
        String dbPassword = "";

        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            String query = "SELECT e.examid, e.coursename, e.date  FROM exam e Join user u using(id)"; // Replace with your table name
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                int examid = resultSet.getInt("examid");
                String coursename = resultSet.getString("coursename");
                String courseDate = resultSet.getString("date");
                courses.add(new Course(examid, coursename,courseDate));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void goToDashBoardPage() {

        Dashboard dashboardPage = new Dashboard();
        dashboardPage.start(new Stage());
        primaryStage.close();
    }

    public static void main(String[] args) {

        launch(args);
        }
}
