package com.healthybooks.borrowerService.controller;


import com.healthybooks.borrowerService.entity.BookCategory;
import com.healthybooks.borrowerService.entity.Borrowers;
import com.healthybooks.borrowerService.webService.BorrowerWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/healthybooks/borrowerService")
public class WebController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private BorrowerWebService borrowerWebService;

    @GetMapping("/getAllBorrowers")
    public List<Borrowers> findAllBookCategory() {
        LOGGER.info("Getting all the books");
        List<Borrowers> listOfAllBorrowers = borrowerWebService.getAllBorrowersList();
        LOGGER.info("Getting All the Borrowers list", listOfAllBorrowers);
        return listOfAllBorrowers;
    }

    @GetMapping("/borrowersId")
    public Optional<Borrowers> findBookById(@RequestParam int  id) {
        Optional<Borrowers> book = borrowerWebService.getBorrowerById(id);
        LOGGER.info("Getting borrowers by ID", book);
        return book;
    }

    @PostMapping("/save")
    public Optional<Borrowers> addBook(@RequestBody Borrowers borrowers) {
        borrowerWebService.saveBorrowersEntity(borrowers);
        LOGGER.info("Saving the borrower into DB",borrowers);
        return borrowerWebService.getBorrowerById(borrowers.getStudentId());
    }

    @PutMapping("/edit/{id}")
    public Optional<Borrowers> updateBorrowersById(@RequestBody Borrowers borrowers,@PathVariable("id") int id){
        Optional<Borrowers> borrowersResponse = borrowerWebService.updateBorrowersById(id,borrowers);
        LOGGER.info("updating the borrowers entity",borrowersResponse);
        return borrowersResponse;
    }

    @GetMapping("/borrowerBookByID/{borrowerId}")
    public Borrowers borrowerBookByID(@RequestParam String  bookId,@PathVariable("borrowerId") int borrowerId){
        Borrowers borrowersResponse = borrowerWebService.borrowerBookByID(bookId,borrowerId).get();
        LOGGER.info("Borrowing the book",borrowersResponse);
        return borrowersResponse;
    }
    @GetMapping("/returnBookByID/{borrowerId}")
    public Borrowers returnBookByID(@RequestParam String  bookISBN,@PathVariable("borrowerId") int borrowerId){
        Borrowers borrowersResponse = borrowerWebService.returnBookByID(bookISBN,borrowerId);
        LOGGER.info("Returning the book",borrowersResponse);
        return borrowersResponse;
    }
    @GetMapping("/generateReport")
    public void generateCSVReport(){
        LOGGER.info("Generating CSV report");
        borrowerWebService.writeToCSV();
    }


    @GetMapping("/getBookByAuthor/{author}")
    public List<BookCategory> findBookByAuthor(@PathVariable String author) {
        List<BookCategory> listOfBooks = borrowerWebService.getBooksListByAuthor(author);
        LOGGER.info("Getting Books by Author", listOfBooks);
        return listOfBooks;
    }

    @GetMapping("/getBookByDepartment/{department}")
    public BookCategory findBookByDepartment(@PathVariable String department) {
        BookCategory listOfBooks = borrowerWebService.getBooksListByDepartment(department);
        LOGGER.info("Getting Books by department", listOfBooks);
        return listOfBooks;
    }

    @GetMapping("/getBookByCategory/{category}")
    public List<BookCategory> findBookByCategory(@PathVariable String category) {
        List<BookCategory> listOfBooks = borrowerWebService.getBooksListCategory(category);
        LOGGER.info("Getting Books by category", listOfBooks);
        return listOfBooks;
    }

}
