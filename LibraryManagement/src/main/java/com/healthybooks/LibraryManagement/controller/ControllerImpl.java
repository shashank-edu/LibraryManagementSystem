package com.healthybooks.LibraryManagement.controller;

import com.healthybooks.LibraryManagement.ExceptionalHandelling.BookNotFoundException;
import com.healthybooks.LibraryManagement.bookEntity.BookCategory;
import com.healthybooks.LibraryManagement.bookEntity.BorrowedBooks;
import com.healthybooks.LibraryManagement.webService.WebServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/healthybooks/libraryManagementService")
public class ControllerImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerImpl.class);
    @Autowired
    private WebServiceImpl webService;

    @GetMapping("/getAllBooks")
    public List<BookCategory> findAllBookCategory() {
        LOGGER.info("Getting all the books");
        List<BookCategory> listOfBooks = webService.getAllBooks();
        LOGGER.info("Getting All th books by Category", listOfBooks);
        return listOfBooks;
    }

    @GetMapping("/booksId")
    public Optional<BookCategory> findBookById(@RequestParam String  id) {
        Optional<BookCategory> book = webService.getBookById(id);
        LOGGER.info("Getting Book by ID", book);
        return book;
    }
    @GetMapping("/getBorrowedById")
    public Optional<BorrowedBooks> getBorrowedById(@RequestParam String  id) {
        Optional<BorrowedBooks> book = webService.getBorrowedById(id);
        LOGGER.info("Getting get borrowed Books by ID", book);
        return book;
    }

    @GetMapping("/Author/{author}")
    public List<BookCategory> findBookByAuthor(@PathVariable String author) {
        List<BookCategory> listOfBooks = webService.getBooksListByAuthor(author);
        LOGGER.info("Getting Books by Author", author);
        return listOfBooks;
    }

    @GetMapping("/department/{department}")
    public List<BookCategory> findBookByDepartment(@PathVariable String department) {
        List<BookCategory> listOfBooks = webService.getBooksListByDepartment(department);
        LOGGER.info("Getting Books by department", department);
        return listOfBooks;
    }

    @GetMapping("/category/{category}")
    public List<BookCategory> findBookByCategory(@PathVariable String category) {
        List<BookCategory> listOfBooks = webService.getBooksListCategory(category);
        LOGGER.info("Getting Books by category", listOfBooks);
        return listOfBooks;
    }

    @PostMapping("/save")
    public Optional<BookCategory> addBook(@RequestBody BookCategory books) {
        webService.saveBooks(books);
        return webService.getBookById(books.getBookID());
    }

    @PutMapping("/edit/{id}")
    public Optional<BookCategory> updateFormById(@RequestBody BookCategory book,@PathVariable("id") String id){

        if (!webService.checkAvailability(id)){
            throw new BookNotFoundException("Book id Not Found - "+id);
        }
        Optional<BookCategory> bookResponse= webService.updateBookById(id,book);
        return bookResponse;
    }
    @GetMapping("/borrowerBookByID/{bookISBN}")
    public Optional<BookCategory> borrowerBookByID(@PathVariable("bookISBN") String bookISBN){
        Optional<BookCategory> bookResponse = webService.borrowerBookByID(bookISBN);
        return bookResponse;
    }

    @GetMapping("/returnBookByID/{id}")
    public BookCategory returnBookByID(@PathVariable("id") String id){
        BookCategory bookResponse = webService.returnBookByID(id);
        return bookResponse;
    }
    @GetMapping("/getAllBorrowedBooks")
    public List<BorrowedBooks> getAllBorrowedBooks() {
        LOGGER.info("Getting all the books");
        List<BorrowedBooks> listOfBooks = webService.getAllBorrowedBooks();
        LOGGER.info("Getting All Borrowed books", listOfBooks);
        return listOfBooks;
    }

    @DeleteMapping("/removeBook")
    public Optional<BookCategory> removeBookByID(@RequestParam String  id){
        return webService.removeBookByID(id);
    }
}
