package com.healthybooks.librarianService.entity;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;


@Document(collection = "BookCategory")
public class BookCategory {
    @MongoId
    private String bookID;
    // ID given by a library to a book to make it distinguishable from other books
    // Example: 978-3-16-148410-0
    private List<Books> individualBooksObj;
    private String department;
    private String title;         // Title of a book
    private String category;
    private int publishedYear;
    private String author;        // Author of book!
    private LocalDate entityCreationDate;
    private int noOfBookAvailable;
    private int totalNoOfBooks;
    private String floor;
    private String rack;

    public int getNoOfBookAvailable() {
        return noOfBookAvailable;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setNoOfBookAvailable(int noOfBookAvailable) {
        this.noOfBookAvailable = noOfBookAvailable;
    }

    public int getTotalNoOfBooks() {
        return totalNoOfBooks;
    }

    public void setTotalNoOfBooks(int totalNoOfBooks) {
        this.totalNoOfBooks = totalNoOfBooks;
    }


    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public BookCategory() {
    }

    public List<Books> getIndividualBooksObj() {
        return individualBooksObj;
    }

    public void setIndividualBooksObj(List<Books> individualBooksObj) {
        this.individualBooksObj = individualBooksObj;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getEntityCreationDate() {
        return entityCreationDate;
    }

    public void setEntityCreationDate(LocalDate entityCreationDate) {
        this.entityCreationDate = entityCreationDate;
    }

    public BookCategory(String bookID, List<Books> individualBooksObj, String title, String category, int publishedYear, String author, LocalDate entityCreationDate, int noOfBookAvailable, int totalNoOfBooks, String floor, String rack) {
        this.bookID = bookID;
        this.individualBooksObj = individualBooksObj;
        this.title = title;
        this.category = category;
        this.publishedYear = publishedYear;
        this.author = author;
        this.entityCreationDate = entityCreationDate;
        this.noOfBookAvailable = noOfBookAvailable;
        this.totalNoOfBooks = totalNoOfBooks;
        this.floor = floor;
        this.rack = rack;
    }

    @Override
    public String toString() {
        return "BookCategory{" +
                "bookID='" + bookID + '\'' +
                ", individualBooksObj=" + individualBooksObj +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", author='" + author + '\'' +
                ", entityCreationDate=" + entityCreationDate +
                '}';
    }
}

