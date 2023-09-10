package com.healthybooks.borrowerService.ExceptionalHandelling;

public class BorrowerNotFoundException extends RuntimeException {
    public BorrowerNotFoundException(String message) {
        super(message);
    }

    public BorrowerNotFoundException(Throwable cause) {
        super(cause);
    }

    public BorrowerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
