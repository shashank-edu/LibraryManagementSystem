package com.healthybooks.librarianService.webService;

import com.healthybooks.librarianService.ExceptionalHandelling.LibrarianNotFoundException;
import com.healthybooks.librarianService.entity.BookCategory;
import com.healthybooks.librarianService.entity.Librarian;
import com.healthybooks.librarianService.repository.RepostioryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LibrarianWebService {

    @Autowired
    private RepostioryImpl borrowersRespository;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianWebService.class);
    public List<Librarian> getAllLibrarianList() {
        return borrowersRespository.findAll();
    }

    public Optional<Librarian> getLibrarianById(int id) {
        if(checkAvailability(id)){
            return borrowersRespository.findById(id);
        }else{
            throw new LibrarianNotFoundException("Borrower Not Found");
        }
    }

    synchronized public Optional<Librarian> saveLibrarianEntity(Librarian librarian) {
        borrowersRespository.save(librarian);
        return borrowersRespository.findById(librarian.getEmpId());
    }

    public boolean checkAvailability(int id) {
        return borrowersRespository.findById(id).isPresent();
    }

    synchronized public Optional<Librarian> updateLibrarianById(int id, Librarian book) {
        if (checkAvailability(id)) {
            Librarian bookResponse = borrowersRespository.findById(id).get();
            bookResponse.setLastName(book.getLastName());
            bookResponse.setFirstName(book.getFirstName());
            borrowersRespository.save(bookResponse);
            return borrowersRespository.findById(id);
        }else {
            throw new LibrarianNotFoundException("Student is with given ID is not available");
        }
    }

    public List getDetailsFromLibrary(){
        List allBooks = this.restTemplate.getForObject("http://LIBRARYMANAGEMENTSERVICE/healthybooks/libraryManagementService/getAllBooks",List.class);
        return allBooks;
    }

    public BookCategory saveBooksData(BookCategory bookCategory ){
        String serviceUrl = "http://LIBRARYMANAGEMENTSERVICE/healthybooks/libraryManagementService";
        BookCategory repoBookCategory = restTemplate.postForObject(serviceUrl + "/save",
                bookCategory, BookCategory.class);
        return repoBookCategory;
    }
    public BookCategory updateBooksDetails(String bookId, BookCategory bookCategory) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<BookCategory> entity = new HttpEntity<BookCategory>(bookCategory,headers);
        String serviceUrl = "http://LIBRARYMANAGEMENTSERVICE/healthybooks/libraryManagementService";
        BookCategory repoBookCategory = restTemplate.exchange(serviceUrl + "/edit/"+bookId, HttpMethod.PUT, entity, BookCategory.class).getBody();
        return repoBookCategory;
    }

    public BookCategory deleteBookData(String bookId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<BookCategory> entity = new HttpEntity<BookCategory>(headers);
        String serviceUrl = "http://LIBRARYMANAGEMENTSERVICE/healthybooks/libraryManagementService";
        return restTemplate.exchange(
                serviceUrl+"/removeBook?id="+bookId, HttpMethod.DELETE, entity, BookCategory.class).getBody();
    }
}


