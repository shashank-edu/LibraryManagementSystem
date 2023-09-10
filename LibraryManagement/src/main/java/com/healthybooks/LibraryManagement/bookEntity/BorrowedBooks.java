package com.healthybooks.LibraryManagement.bookEntity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "BorrowedBooks")
public class BorrowedBooks {
    @MongoId
    private String bookID;
    // ID given by a library to a book to make it distinguishable from other books
    // Example: 978-3-16-148410-0t
    private List<Books> individualBooksObj;
    private String title;         // Title of a book
    private String category;
    private int publishedYear;
    private String author;        // Author of book!
    private LocalDate entityCreationDate;
    private String floor;
    private String rack;

    public BorrowedBooks() {
    }
    public BorrowedBooks(BookCategory bookCategory) {
        this.bookID=bookCategory.getBookID();
        this.individualBooksObj= bookCategory.getIndividualBooksObj();
        this.author=bookCategory.getAuthor();
        this.entityCreationDate=LocalDate.now();
        this.floor=bookCategory.getFloor();
        this.publishedYear=bookCategory.getPublishedYear();
        this.title=bookCategory.getTitle();
        this.rack=bookCategory.getRack();
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

    public List<Books> getIndividualBooksObj() {
        return individualBooksObj;
    }

    public void setIndividualBooksObj(List<Books> individualBooksObj) {
        this.individualBooksObj = individualBooksObj;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
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
