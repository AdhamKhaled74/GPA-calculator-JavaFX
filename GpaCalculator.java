package com.example.demo;
import com.example.project1.Data;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GpaCalculator extends Application {
    private Stage primaryStage;
    private ComboBox<String>[] gradeComboBoxes;
    private ComboBox<Integer> hoursComboBox;

    private String[] grades = {"A+", "A", "B+", "B", "C+", "C", "D+", "D", "F"};

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;
        primaryStage.setTitle("GPA Calculator");
        Image icon = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\gpaSymbolpng11.png"); // Replace 'path/to/your/icon.png' with the actual path to your icon file
        primaryStage.getIcons().add(icon);

        Image backgroundImage = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\university-background.jpg");

        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false));
        Button button1=new Button("Back to Dashboard");
        button1.setPrefWidth(200); // Set preferred width
        button1.setPrefHeight(60);
        button1.setOnAction(e ->goToDashboardtPage());
        button1.setStyle("-fx-background-color: grey; " + // Custom background color
                "-fx-text-fill: white; " + // Text color
                "-fx-border-radius: 10; " + // Border radius
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;"); // Font size
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(background));
        gridPane.add(button1,1,9);
        gradeComboBoxes = new ComboBox[6];

        for (int i = 0; i < 6; i++) {
            Label subjectLabel = new Label("Subject " + (i + 1));
            subjectLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;-fx-font-size: 20px;");
            gridPane.add(subjectLabel, 0, i);

            gradeComboBoxes[i] = new ComboBox<>();
            gradeComboBoxes[i].getItems().addAll(grades);
            gradeComboBoxes[i].setStyle("-fx-text-fill: white;-fx-font-weight: bold;-fx-border-radius: 6;-fx-border-color: black;-fx-border-width: 2;-fx-background-color: white; -fx-font-size: 20px;");
            gridPane.add(gradeComboBoxes[i], 1, i);
            hoursComboBox = new ComboBox<>();
            hoursComboBox.getItems().add(3);
            hoursComboBox.setValue(3);
            hoursComboBox.setDisable(false);
            hoursComboBox.setStyle("-fx-text-fill: white; -fx-font-weight: bold;-fx-border-color: black;-fx-border-radius: 6;-fx-border-width: 2;-fx-background-color: white; -fx-font-size: 20px;");
            gridPane.add(hoursComboBox, 2, i);
        }

        Label hoursLabel = new Label("Number of Hours");
        hoursLabel.setStyle("-fx-text-fill: black;-fx-font-weight: bold; -fx-font-size: 14px;");
        gridPane.add(hoursLabel, 2, 6);

        TextField resultField = new TextField("Your GPA will be here");
        resultField.setEditable(false);
        resultField.setStyle("-fx-text-fill: white; -fx-background-color: grey;-fx-font-weight: bold; -fx-font-size: 17px;");
        gridPane.add(resultField, 1, 7, 2, 1);

        Button calculateButton = new Button("Calculate");
        final float[] gpaValue = {0};
        calculateButton.setOnAction(e ->{

            gpaValue[0] = calculateGPA(resultField);
        });
        calculateButton.setStyle("-fx-text-fill: white;-fx-font-weight: bold; -fx-background-color: grey; -fx-font-size: 14px;");
        gridPane.add(calculateButton, 1, 6, 2, 1);


// Parse the text as a float

        System.out.println("the result = "+ gpaValue[0]);
        Button button2=new Button("Suggestion Course");
        button2.setPrefWidth(200); // Set preferred width
        button2.setPrefHeight(60);


        button2.setOnAction(e -> {
            String courseSuggest = fetchCourseSuggestion(gpaValue[0]);
            displayCourseSuggestion(courseSuggest);
        });
        button2.setStyle("-fx-background-color: grey; " + // Custom background color
                "-fx-text-fill: white; " + // Text color
                "-fx-border-radius: 10; " + // Border radius
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;");
        gridPane.add(button2,1,8);
        // Top-right logo
        Image topRightLogo = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\fci_en.png");
        ImageView topRightView = new ImageView(topRightLogo);
        StackPane.setAlignment(topRightView, Pos.TOP_RIGHT);

        Image bottomLeftLogo = new Image("C:\\Users\\Adhamm74\\Desktop\\ProjectImages\\uni_en.png");
        ImageView bottomLeftView = new ImageView(bottomLeftLogo);
        StackPane.setAlignment(bottomLeftView, Pos.BOTTOM_LEFT);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(gridPane, topRightView, bottomLeftView);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        Scene scene = new Scene(stackPane, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private Float calculateGPA(TextField resultField) {
        float totalPoints = 0;
        int totalHours = 0;

        for (int i = 0; i < 6; i++) {
            String grade = gradeComboBoxes[i].getValue();

            if (grade != null) {
                totalPoints += getGradePoints(grade) * 3; // Always assuming 3 hours for each subject
                totalHours += 3; // Adding 3 hours for each subject
            }
        }

        float gpa = 0;
        if (totalHours != 0) {
            gpa = totalPoints / totalHours;
            resultField.setText(String.format("%.2f", gpa));
            return gpa;
        } else
            resultField.clear();
        return gpa;
    }

    private double getGradePoints(String grade) {
        switch (grade) {
            case "A+":
                return 4.0;
            case "A":
                return 3.7;
            case "B+":
                return 3.3;
            case "B":
                return 3.0;
            case "C+":
                return 2.7;
            case "C":
                return 2.4;
            case "D+":
                return 2.0;
            case "D":
                return 1.7;
            case "F":
                return 0.0;
            default:
                return 0.0;
        }
    }
    private void goToDashboardtPage() {

        Dashboard dashboardPage = new Dashboard();
        dashboardPage.start(new Stage());
        primaryStage.close();
    }


    private void displayCourseSuggestion(String courseSuggestion) {
        // Display the course suggestion in a dialog or a text field
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Course Suggestion");
        alert.setHeaderText(null);
        alert.setContentText("Recommended Course: " + courseSuggestion);
        String cssPath = getClass().getResource("styles.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(cssPath);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("User confirmed the action."));
    }

    private String fetchCourseSuggestion(float number) {
        // Call your API here and handle the response
        // For example, using a simple API URL
        String course="";

        try {
            float totalgpa=0;
            for (int i = 1; i <= 1; i++) {
                URL url = new URL("https://gpa-api.onrender.com/getSubjects");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    System.out.println(response.toString());

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(response.toString());


//
                    // Write JSON to a file
                    String fileName = "temp.json";
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Adhamm74\\IdeaProjects\\demo\\src" + File.separator + fileName))) {
                        writer.write(jsonNode.toPrettyString());
                        System.out.println("JSON data saved to: " + File.separator + fileName);
                    }
                    String urll = "jdbc:mysql://localhost:3306/login";
                    String dbUsername = "root";
                    String dbPassword = "";
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(urll, dbUsername, dbPassword);

                    // Prepared statement to prevent SQL injection
                    PreparedStatement preparedStatement = con.prepareStatement("SELECT s.level,s.gpa,g.terms FROM studentdetails s JOIN studentgpa g USING (studentid) WHERE s.studentid= ?");
                    preparedStatement.setString(1, LoginApp.currentusername);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        int level = rs.getInt("level");
                        float gpa = rs.getFloat("gpa");
                        int terms = rs.getInt("terms");


                        totalgpa= ((gpa * terms) + number) / (terms + 1);


                        System.out.println("gpa = "+gpa);

                        System.out.println("Level = "+level);

                        System.out.println("Terms = "+terms);

                        File file = new File("C:\\Users\\Adhamm74\\IdeaProjects\\demo\\src\\main\\temp.json");
                        Temp temp = objectMapper.readValue(file, Temp.class);
                        Data[] subjects = temp.getData().get(level - 1);

                        if (totalgpa <= 2)
                            course = subjects[3].getSubject();
                        else if (totalgpa <= 2.4 && totalgpa > 2.0)
                            course = subjects[2].getSubject();
                        else if (totalgpa <= 3 && totalgpa > 2.4)
                            course = subjects[1].getSubject();
                        else
                            course = subjects[0].getSubject();
                    }


                    System.out.println("Total Gpa = "+totalgpa);

                    System.out.println("Suggestion Course = "+course);

                    rs.close();
                    preparedStatement.close();
                    con.close();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  course;
    }


    public static void main(String[] args) {
        launch(args);
        }
}
