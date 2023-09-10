package com.healthybooks.librarianService.controller;

import com.healthybooks.librarianService.ExceptionalHandelling.LibrarianNotFoundException;
import com.healthybooks.librarianService.entity.BookCategory;
import com.healthybooks.librarianService.entity.Librarian;
import com.healthybooks.librarianService.webService.LibrarianWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/healthybooks/librarianService")
public class WebController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private LibrarianWebService librarianWebService;

    @GetMapping("/getAllLibrarians")
    public List<Librarian> findAllBookCategory() {
        LOGGER.info("Getting all the books");
        List<Librarian> listOfAllLibrarian = librarianWebService.getAllLibrarianList();
        LOGGER.info("Getting All the Borrowers list", listOfAllLibrarian);
        return listOfAllLibrarian;
    }

    @GetMapping("/librarianId")
    public Optional<Librarian> findBookById(@RequestParam int  id) {
        Optional<Librarian> book = librarianWebService.getLibrarianById(id);
        LOGGER.info("Getting borrowers by ID", book);
        return book;
    }

    @PostMapping("/save")
    public Optional<Librarian> addBook(@RequestBody Librarian librarian) {
        librarianWebService.saveLibrarianEntity(librarian);
        return librarianWebService.getLibrarianById(librarian.getEmpId());
    }

    @PutMapping("/edit/{id}")
    public Optional<Librarian> updateFormById(@RequestBody Librarian librarian, @PathVariable("id") int id){

        if (!librarianWebService.checkAvailability(id)){
            throw new LibrarianNotFoundException("Book id Not Found - "+id);
        }
        Optional<Librarian> borrowersResponse = librarianWebService.updateLibrarianById(id, librarian);
        return borrowersResponse;
    }

    @GetMapping("/getDetailsFromLibrary")
    public List getDetailsFromLibrary() {
        LOGGER.info("getDetailsFromLibrary()");
        return librarianWebService.getDetailsFromLibrary();
    }

    @PostMapping("/saveBookDetails")
    public BookCategory saveBook(@RequestBody BookCategory bookCategory){
        return librarianWebService.saveBooksData(bookCategory);
    }

    @PutMapping("/updateBookDetails/{bookId}")
    public BookCategory updateBook(@RequestBody BookCategory bookCategory,@PathVariable("bookId") String bookId){
        return librarianWebService.updateBooksDetails(bookId,bookCategory);
    }

    @DeleteMapping("/deleteBookData/{bookId}")
    public BookCategory deleteBookData(@PathVariable("bookId") String bookId){
        return librarianWebService.deleteBookData(bookId);
    }


}
