package com.example.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.*;

import static com.example.demo.LoginApp.currentusername;


public class StudentPage extends Application {

    private Stage primaryStage;
    private final ObservableList<Student> students = FXCollections.observableArrayList();
    @Override
    public void start(Stage primaryStage) {
        // Create a sample student
        this.primaryStage=primaryStage;
        Image icon = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\loginsymbol.png");
        // Set the icon for the stage
        primaryStage.getIcons().add(icon);
        StackPane stackPane = new StackPane();

        TableView<Student> table = new TableView<>();

        table.setRowFactory(tv -> new TableRow<Student>() {
            @Override
            protected void updateItem(Student item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    getStyleClass().add("table-row-hidden");
                } else {
                    getStyleClass().remove("table-row-hidden");
                }
            }
        });

        //


        TableColumn<Student, Integer> studentIdColumn = new TableColumn<>("Student_ID");
        studentIdColumn.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty().asObject());
        studentIdColumn.setPrefWidth(240);
        TableColumn<Student, String> studentNameColumn = new TableColumn<>("Student Name");
        studentNameColumn.setCellValueFactory(cellData -> cellData.getValue().studentNameProperty());
        studentNameColumn.setPrefWidth(270);
        TableColumn<Student, Integer> studentLevelColumn = new TableColumn<>("Student Level");
        studentLevelColumn.setCellValueFactory(cellData -> cellData.getValue().studentLevelProperty().asObject());
        studentLevelColumn.setPrefWidth(220);
        TableColumn<Student, Float> studentGpaColumn = new TableColumn<>("Student GPA");
        studentGpaColumn.setCellValueFactory(cellData -> cellData.getValue().studentGpaProperty().asObject());
        studentGpaColumn.setPrefWidth(220);
        TableColumn<Student, String> stduentNationalColumn = new TableColumn<>("Student National");
        stduentNationalColumn.setCellValueFactory(cellData -> cellData.getValue().stduentNationalProperty());
        stduentNationalColumn.setPrefWidth(240);
        TableColumn<Student, String> studentNationalIdColumn = new TableColumn<>("Student NationalID");
        studentNationalIdColumn.setCellValueFactory(cellData -> cellData.getValue().studentNationalIdProperty());
        studentNationalIdColumn.setPrefWidth(240);
        TableColumn<Student, String> genderColumn = new TableColumn<>("Student Gender");
        genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        genderColumn.setPrefWidth(240);
        TableColumn<Student, String> studentBirthdateColumn = new TableColumn<>("Student Birthdate");
        studentBirthdateColumn.setCellValueFactory(cellData -> cellData.getValue().studentBirthdateProperty());
        studentBirthdateColumn.setPrefWidth(250);
        table.getColumns().addAll(studentIdColumn, studentNameColumn, studentLevelColumn,studentGpaColumn,stduentNationalColumn,studentNationalIdColumn,genderColumn,studentBirthdateColumn);
        fetchDataFromDataBase();
        table.setItems(students);
        Button button1=new Button("Back");
        button1.setPrefWidth(180); // Set preferred width
        button1.setPrefHeight(45);
        button1.setOnAction(e ->goToDashBoardPage());
        button1.setStyle("-fx-background-color: #64B5F6; " + // Custom background color
                "-fx-text-fill: white; " + // Text color
                "-fx-border-radius: 10;" +
                "-fx-font-weight: bold; " + // Border radius
                "-fx-font-size: 20px;");// Font size
        stackPane.getChildren().add(table);
        stackPane.getChildren().add(button1);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        Scene scene = new Scene(stackPane, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        table.getStyleClass().add("table-background");
        table.getStyleClass().add("table-cell-centered");
        primaryStage.setTitle("Student Details");
        primaryStage.setScene(scene);
        primaryStage.show();




    }
    private void  fetchDataFromDataBase(){
        String url = "jdbc:mysql://localhost:3306/login";
        String dbUsername = "root";
        String dbPassword = "";
        // Change this to your database password
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
            String query = "SELECT  c.name, c.gpa, c.level,c.national,c.gender,c.birthdate,c.nationalid,c.studentid " +
                    "FROM user u INNER JOIN studentdetails c ON u.id = c.studentid " +
                    "WHERE u.id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, currentusername);

            ResultSet resultSet = statement.executeQuery();
            while  (resultSet.next()) {
                String studentName = resultSet.getString("name");
                int studentId = resultSet.getInt("studentid");
                float studentGpa = resultSet.getFloat("gpa");
                int studentLevel = resultSet.getInt("level");
                String studentNationalId=resultSet.getString("nationalid");
                String studentNational =resultSet.getString("national");
                String studentBirthdate=resultSet.getString("birthdate");
                String studentGender=resultSet.getString("gender");

                students.add(new Student(studentId,studentName,studentLevel,studentGpa,studentNational,studentNationalId,studentGender,studentBirthdate));

            }
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
