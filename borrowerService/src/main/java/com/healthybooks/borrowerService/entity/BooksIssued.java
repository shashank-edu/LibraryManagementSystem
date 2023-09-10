package com.healthybooks.borrowerService.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Document(collection = "BooksIssued")
public class BooksIssued {
    @MongoId
    private String bookISBN;
    private String title;
    private String category;
    private String author;
    private LocalDate bookIssueDate;
    private LocalDate bookReturnDate;
    private int fineToBePaid;
    private String publishedYear;
    private String floor;
    private String rack;

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
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

    public LocalDate getBookIssueDate() {
        return bookIssueDate;
    }

    public void setBookIssueDate(LocalDate bookIssueDate) {
        this.bookIssueDate = bookIssueDate;
    }

    public LocalDate getBookReturnDate() {
        return bookReturnDate;
    }

    public void setBookReturnDate(LocalDate bookReturnDate) {
        this.bookReturnDate = bookReturnDate;
    }

    public int getFineToBePaid() {
        return fineToBePaid;
    }

    public void setFineToBePaid(int fineToBePaid) {
        this.fineToBePaid = fineToBePaid;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(String publishedYear) {
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

    public BooksIssued() {
    }

    public BooksIssued(String bookISBN, String title, String category, String author, LocalDate bookIssueDate, LocalDate bookReturnDate, int fineToBePaid, String publishedYear, String floor, String rack) {
        this.bookISBN = bookISBN;
        this.title = title;
        this.category = category;
        this.author = author;
        this.bookIssueDate = bookIssueDate;
        this.bookReturnDate = bookReturnDate;
        this.fineToBePaid = fineToBePaid;
        this.publishedYear = publishedYear;
        this.floor = floor;
        this.rack = rack;
    }

    @Override
    public String toString() {
        return "BooksIssued{" +
                "bookISBN='" + bookISBN + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", author='" + author + '\'' +
                ", bookIssueDate=" + bookIssueDate +
                ", bookReturnDate=" + bookReturnDate +
                ", fineToBePaid=" + fineToBePaid +
                ", publishedYear='" + publishedYear + '\'' +
                ", floor='" + floor + '\'' +
                ", rack='" + rack + '\'' +
                '}';
    }
}
