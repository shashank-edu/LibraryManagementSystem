package com.healthybooks.borrowerService.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Borrowers")
public class Borrowers {
    @MongoId
    private int studentId;
    private String firstName;
    private  String lastName;
    private String currentYear;
    private String courseName;
    private String department;
    private String classSection;
    private String emailId;
    private List<BooksIssued> bookIssuedJsonObjectList;
    private int totalFineAlreadyPaid;


    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public List<BooksIssued> getBookIssuedJsonObjectList() {
        return bookIssuedJsonObjectList;
    }

    public void setBookIssuedJsonObjectList(List<BooksIssued> bookIssuedJsonObjectList) {
        this.bookIssuedJsonObjectList = bookIssuedJsonObjectList;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClassSection() {
        return classSection;
    }

    public void setClassSection(String classSection) {
        this.classSection = classSection;
    }

    public int getTotalFineAlreadyPaid() {
        return totalFineAlreadyPaid;
    }

    public void setTotalFineAlreadyPaid(int totalFineAlreadyPaid) {
        this.totalFineAlreadyPaid = totalFineAlreadyPaid;
    }

    public Borrowers() {
       this.bookIssuedJsonObjectList = new ArrayList<BooksIssued>();
    }

}
