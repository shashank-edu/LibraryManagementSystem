package com.healthybooks.librarianService.entity;


import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
public class Books {
    @MongoId
    private String bookISBN;
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

    public Books(String bookISBN, LocalDate bookIssueDate, LocalDate bookReturnDate, LocalDate lastModified, String issuerId, boolean isIssued) {
        this.bookISBN = bookISBN;
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
                ", bookIssueDate=" + bookIssueDate +
                ", bookReturnDate=" + bookReturnDate +
                ", lastModified=" + lastModified +
                ", issuerId='" + issuerId + '\'' +
                ", isIssued=" + isIssued +
                '}';
    }
}

//bookISBN{
//    @Id
//    String BookID
//    int NoOfBookAvailable;
//    int TotalNoOfBooks;
//    Date BookIssueDate;
//    Date BookReturnDate;
//    Date LastModified;
//private boolean isIssued;
//}