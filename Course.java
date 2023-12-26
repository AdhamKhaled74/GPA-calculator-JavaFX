package com.example.demo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Course {

    private final SimpleStringProperty courseName;
    private final SimpleStringProperty date;
    private final SimpleIntegerProperty examid;

    public Course(int examid, String courseName, String date) {
        this.examid = new SimpleIntegerProperty(examid);

        this.courseName = new SimpleStringProperty(courseName);
        this.date = new SimpleStringProperty(date);

    }





    public String getCourseName() {
        return courseName.get();
    }
    public SimpleIntegerProperty examidProperty() {
        return examid;
    }
    public SimpleStringProperty courseNameProperty() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
        }
}