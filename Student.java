package com.example.demo;


import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {

    private final SimpleIntegerProperty studentId;
    private final SimpleStringProperty studentName;

    private final SimpleIntegerProperty studentLevel;

    private final SimpleFloatProperty studentGpa;

    private final SimpleStringProperty stduentNational;
    private final SimpleStringProperty studentNationalId;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty studentBirthdate;


    public Student(int studentId, String studentName, int studentLevel, float studentGpa, String stduentNational, String studentNationalId, String gender, String studentBirthdate) {

        this.studentId= new SimpleIntegerProperty(studentId);
        this.studentName = new SimpleStringProperty(studentName);
        this.studentLevel = new SimpleIntegerProperty(studentLevel);
        this.studentBirthdate = new SimpleStringProperty(studentBirthdate);
        this.gender = new SimpleStringProperty(gender);
        this.studentNationalId =new SimpleStringProperty(studentNationalId);
        this.stduentNational = new SimpleStringProperty(stduentNational);
        this.studentGpa = new SimpleFloatProperty(studentGpa);
    }

    public SimpleIntegerProperty studentIdProperty() {
        return studentId;
    }
    public SimpleStringProperty studentNameProperty() {
        return studentName;
    }

    public SimpleIntegerProperty studentLevelProperty() {
        return studentLevel;
    }

    public SimpleFloatProperty studentGpaProperty() {
        return studentGpa;
    }

    public SimpleStringProperty studentBirthdateProperty() {
        return studentBirthdate;
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public SimpleStringProperty studentNationalIdProperty() {
        return studentNationalId;
    }

    public SimpleStringProperty stduentNationalProperty() {
        return stduentNational;

        }

}