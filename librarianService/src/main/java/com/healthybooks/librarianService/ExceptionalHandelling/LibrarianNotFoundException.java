package com.healthybooks.librarianService.ExceptionalHandelling;

public class LibrarianNotFoundException extends RuntimeException {
    public LibrarianNotFoundException(String message) {
        super(message);
    }

    public LibrarianNotFoundException(Throwable cause) {
        super(cause);
    }

    public LibrarianNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
