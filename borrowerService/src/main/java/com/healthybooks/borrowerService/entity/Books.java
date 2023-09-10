package com.healthybooks.borrowerService.entity;


import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

public class Books {
    @MongoId
    private String bookISBN;
    private int noOfBookAvailable;
    private int totalNoOfBooks;
    private LocalDate bookIssueDate;
    private LocalDate bookReturnDate;
    private LocalDate lastModified;
    private String issuerId;

    private boolean isIssued;       // this will be true if the book is currently issued to some borrower.

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public int getNoOfBookAvailable() {
        return noOfBookAvailable;
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

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public Books() {
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public Books(String bookISBN, int noOfBookAvailable, int totalNoOfBooks, LocalDate bookIssueDate, LocalDate bookReturnDate, LocalDate lastModified, String issuerId, boolean isIssued) {
        this.bookISBN = bookISBN;
        this.noOfBookAvailable = noOfBookAvailable;
        this.totalNoOfBooks = totalNoOfBooks;
        this.bookIssueDate = bookIssueDate;
        this.bookReturnDate = bookReturnDate;
        this.lastModified = lastModified;
        this.issuerId = issuerId;
        this.isIssued = isIssued;
    }

    @Override
    public String toString() {
        return "Books{" +
                "bookISBN='" + bookISBN + '\'' +
                ", noOfBookAvailable=" + noOfBookAvailable +
                ", totalNoOfBooks=" + totalNoOfBooks +
                ", bookIssueDate=" + bookIssueDate +
                ", bookReturnDate=" + bookReturnDate +
                ", lastModified=" + lastModified +
                ", issuerId='" + issuerId + '\'' +
                ", isIssued=" + isIssued +
                '}';
    }
}
