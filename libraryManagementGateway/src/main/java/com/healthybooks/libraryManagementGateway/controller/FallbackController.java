package com.healthybooks.libraryManagementGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/librarianServiceFallback")
    public String librarianServiceFallback(){
        return "Librarian Service is down this time !! ";
    }

    @GetMapping("/borrowerServiceFallback")
    public String borrowerServiceFallback(){
        return "Borrower Service is down this time !! ";
    }

    @GetMapping("/libraryManagementServiceFallback")
    public String libraryManagementServiceFallback(){
        return "library Management Service is down this time !! ";
    }
}
