package com.healthybooks.borrowerService.ExceptionalHandelling;

import java.sql.Timestamp;

public class BorrowerErrorResponse {
    
    private int status;
    private String message; 
    private Timestamp timestamp;                     

    public BorrowerErrorResponse() {
    }
    
    public BorrowerErrorResponse(int status, String message, Timestamp timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp2) {
        this.timestamp = timestamp2;
    }

    
}
