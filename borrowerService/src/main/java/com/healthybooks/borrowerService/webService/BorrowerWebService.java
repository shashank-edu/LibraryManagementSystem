package com.healthybooks.borrowerService.webService;

import com.healthybooks.borrowerService.ExceptionalHandelling.BorrowerNotFoundException;
import com.healthybooks.borrowerService.entity.BookCategory;
import com.healthybooks.borrowerService.entity.Books;
import com.healthybooks.borrowerService.entity.BooksIssued;
import com.healthybooks.borrowerService.entity.Borrowers;
import com.healthybooks.borrowerService.repository.RepositoryImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class BorrowerWebService {
    @Autowired
    private RepositoryImpl borrowersRepository;
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowerWebService.class);
    synchronized public List<Borrowers> getAllBorrowersList() {
        for(Borrowers borrowersEntity: borrowersRepository.findAll()){
            for(BooksIssued tempBooksIssued : borrowersEntity.getBookIssuedJsonObjectList()){
                Period diff
                        = Period
                        .between(LocalDate.now(),tempBooksIssued.getBookReturnDate());
                if(diff.getDays() < 0){
                    tempBooksIssued.setFineToBePaid(diff.getDays()*(-10));
                }else{
                    tempBooksIssued.setFineToBePaid(0);
                }
            }
            borrowersRepository.save(borrowersEntity);
        }

        return borrowersRepository.findAll();
    }

    synchronized public Optional<Borrowers> getBorrowerById(int borrowerId) {
        if(checkAvailability(borrowerId)){
            Borrowers borrowersEntity = borrowersRepository.findById(borrowerId).get();
            for(BooksIssued tempBooksIssued : borrowersEntity.getBookIssuedJsonObjectList()){
                Period diff
                        = Period
                        .between(LocalDate.now(),tempBooksIssued.getBookReturnDate());
                if(diff.getDays() < 0){
                    tempBooksIssued.setFineToBePaid(diff.getDays()*(-10));
                }else{
                    tempBooksIssued.setFineToBePaid(0);
                }
            }
            borrowersRepository.save(borrowersEntity);
            return borrowersRepository.findById(borrowerId);
        }else{
            LOGGER.error("Borrower Not found");
            throw new BorrowerNotFoundException("Borrower Not Found");
        }
    }

    synchronized public Optional<Borrowers> saveBorrowersEntity(Borrowers borrowers) {
        borrowersRepository.save(borrowers);
        LOGGER.info("Save borrower Entity",borrowers);
        return borrowersRepository.findById(borrowers.getStudentId());
    }

    public boolean checkAvailability(int id) {
        return borrowersRepository.findById(id).isPresent();
    }

    synchronized public Optional<Borrowers> updateBorrowersById(int id, Borrowers book) {
        if (checkAvailability(id)) {
            Borrowers bookResponse = borrowersRepository.findById(id).get();
            LOGGER.debug("update borrowers Object by ID: "+id,bookResponse);
            bookResponse.setClassSection(book.getClassSection());
            bookResponse.setDepartment(book.getDepartment());
            bookResponse.setCurrentYear(book.getDepartment());
            bookResponse.setLastName(book.getLastName());
            bookResponse.setFirstName(book.getFirstName());
            borrowersRepository.save(bookResponse);
            return borrowersRepository.findById(id);
        }else {
            LOGGER.error("Student is with given ID is not available");
            throw new BorrowerNotFoundException("Student is with given ID is not available");
        }
    }
    public BooksIssued converterBorrowerObj(BookCategory bookCategoryBorrowed,String bookISBN){
        BooksIssued booksIssuedAppender = new BooksIssued();
        LOGGER.debug("Converting from bookCategory to Borrowers ",bookCategoryBorrowed);
        String issuedBookISBN=null;
        for(Books tempBook: bookCategoryBorrowed.getIndividualBooksObj()){
            if(bookISBN.equals(tempBook.getBookISBN())){
                issuedBookISBN=tempBook.getBookISBN();
                break;
            }
        }
        if(issuedBookISBN != null){
            booksIssuedAppender.setBookISBN(issuedBookISBN);
        }else {
            booksIssuedAppender.setBookISBN("");
        }
        booksIssuedAppender.setAuthor(bookCategoryBorrowed.getAuthor());
        booksIssuedAppender.setBookIssueDate(LocalDate.now());
        booksIssuedAppender.setBookReturnDate(LocalDate.now().plusDays(15));
        booksIssuedAppender.setPublishedYear(bookCategoryBorrowed.getPublishedYear());
        booksIssuedAppender.setRack(bookCategoryBorrowed.getRack());
        booksIssuedAppender.setFloor(bookCategoryBorrowed.getFloor());
        booksIssuedAppender.setCategory(bookCategoryBorrowed.getCategory());
        booksIssuedAppender.setTitle(bookCategoryBorrowed.getTitle());
        return booksIssuedAppender;
    }
    synchronized public Optional<Borrowers> borrowerBookByID(String bookISBN,int borrowerId) {
        if(checkAvailability(borrowerId)){
            Borrowers borrowers = borrowersRepository.findById(borrowerId).get();
            List<BooksIssued> booksIssued = borrowers.getBookIssuedJsonObjectList();
            BookCategory bookCategoryBorrowed = this.restTemplate.getForObject("http://LIBRARYMANAGEMENTSERVICE/healthybooks/libraryManagementService/borrowerBookByID/" + bookISBN, BookCategory.class);
            BooksIssued booksIssuedAppender = converterBorrowerObj(bookCategoryBorrowed,bookISBN);
            booksIssued.add(booksIssuedAppender);
            borrowers.setBookIssuedJsonObjectList(booksIssued);
            borrowersRepository.save(borrowers);
            return Optional.of(borrowersRepository.findById(borrowerId).get());
        }else{
            return Optional.empty();
        }
    }
    public List<BookCategory> getBooksListByAuthor(String author) {
        List<BookCategory> bookCategoryBorrowed = (List<BookCategory>) this.restTemplate.getForObject("http://LIBRARYMANAGEMENTSERVICE/healthybooks/libraryManagementService/Author/" + author, BookCategory.class);
        return bookCategoryBorrowed;
    }

    public BookCategory getBooksListByDepartment(String department) {
        BookCategory bookCategoryBorrowed = this.restTemplate.getForObject("http://LIBRARYMANAGEMENTSERVICE/healthybooks/libraryManagementService/department/" + department, BookCategory.class);
        return bookCategoryBorrowed;
    }

    public List<BookCategory> getBooksListCategory(String category) {
        List<BookCategory> bookCategoryBorrowed = (List<BookCategory>) this.restTemplate.getForObject("http://LIBRARYMANAGEMENTSERVICE/healthybooks/libraryManagementService/category/" + category, BookCategory.class);
        return bookCategoryBorrowed;
    }
    public Borrowers removeBookFromBorrowerAccount(BookCategory bookCategoryBorrowed,int borrowerId){
        Borrowers borrowers = borrowersRepository.findById(borrowerId).get();
        LOGGER.debug("Removing the book from Borrower Account having Borrower id: "+borrowerId);
        List<BooksIssued> booksIssued = borrowers.getBookIssuedJsonObjectList();
        List<BooksIssued> newBooksIssuedList = new ArrayList<BooksIssued>();
        String bookISBN = bookCategoryBorrowed.getIndividualBooksObj().get(0).getBookISBN();
        if(bookISBN!=null){
            for(BooksIssued tempIssuedBookObj : booksIssued){
                if(!bookISBN.equals(tempIssuedBookObj.getBookISBN())){
                    newBooksIssuedList.add(tempIssuedBookObj);
                }
            }
        }
        borrowers.setBookIssuedJsonObjectList(newBooksIssuedList);
        return borrowers;
    }
    synchronized public Borrowers returnBookByID(String bookISBN, int borrowerId) {
        if(borrowersRepository.findById(borrowerId).isPresent()){
            LOGGER.debug("Returning By id: "+bookISBN);
            BookCategory bookCategoryBorrowed = this.restTemplate.getForObject("http://LIBRARYMANAGEMENTSERVICE/healthybooks/libraryManagementService/returnBookByID/" + bookISBN, BookCategory.class);
            Borrowers borrowersObj= borrowersRepository.findById(borrowerId).get();
            for(Books tempBooks: bookCategoryBorrowed.getIndividualBooksObj()){
                Period diff = Period.between(LocalDate.now(),tempBooks.getBookReturnDate());
                if(diff.getDays() < 0){ // if return time is exceeded then only have to setFine
                    for(BooksIssued tempBookIssue: borrowersObj.getBookIssuedJsonObjectList()){
                        if(bookISBN.equals(tempBookIssue.getBookISBN())) { // here we are taking as return only one book at a time
                            borrowersObj.setTotalFineAlreadyPaid(borrowersObj.getTotalFineAlreadyPaid() + tempBookIssue.getFineToBePaid());
                            borrowersRepository.save(borrowersObj);
                            break;
                        }
                    }
                }
            }
            Borrowers filteredBorrowers =  removeBookFromBorrowerAccount(bookCategoryBorrowed ,borrowerId);
            borrowersRepository.save(filteredBorrowers);
            return filteredBorrowers;
        }
        LOGGER.error("Borrower Not found");
       throw  new BorrowerNotFoundException("Borrower not found");
    }
    public void writeToCSV()
    {
        String CSV_SEPARATOR = ",";
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("pendingReturnBookReport.csv"), "UTF-8"));
            String headerString = "Student Id,Borrower Name,Current Year,Course Name,Department,Class Section,EmailId,BookISBN,Book Issue Date,Book Return Date";
            LOGGER.debug("CSV file generated with header",headerString);
            bw.write(headerString);
            bw.newLine();
            for (Borrowers borrowersEntity : borrowersRepository.findAll()) {
                for (BooksIssued tempBooksIssued : borrowersEntity.getBookIssuedJsonObjectList()) {
                    Period diff
                            = Period
                            .between(LocalDate.now(), tempBooksIssued.getBookReturnDate());
                    if (diff.getDays() < 0) {
                        StringBuffer oneLine = new StringBuffer();
                        oneLine.append(borrowersEntity.getStudentId());
                        oneLine.append(CSV_SEPARATOR);
                        oneLine.append(borrowersEntity.getFirstName()+" "+borrowersEntity.getLastName());
                        oneLine.append(CSV_SEPARATOR);
                        oneLine.append(borrowersEntity.getCurrentYear());
                        oneLine.append(CSV_SEPARATOR);
                        oneLine.append(borrowersEntity.getCourseName());
                        oneLine.append(CSV_SEPARATOR);
                        oneLine.append(borrowersEntity.getDepartment());
                        oneLine.append(CSV_SEPARATOR);
                        oneLine.append(borrowersEntity.getClassSection());
                        oneLine.append(CSV_SEPARATOR);
                        oneLine.append(borrowersEntity.getEmailId());
                        oneLine.append(CSV_SEPARATOR);
                        oneLine.append(tempBooksIssued.getBookISBN());
                        oneLine.append(CSV_SEPARATOR);
                        oneLine.append(tempBooksIssued.getBookIssueDate());
                        oneLine.append(CSV_SEPARATOR);
                        oneLine.append(tempBooksIssued.getBookReturnDate());
                        oneLine.append(CSV_SEPARATOR);
                        bw.write(oneLine.toString());
                        bw.newLine();
                    }
                    bw.flush();
                    bw.close();
                }
            }
        }
        catch (UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException",e);
        }
        catch (FileNotFoundException e){
            LOGGER.error("File Not Found",e);
        }
        catch (IOException e){
            LOGGER.error("IO Exception",e);
        }
    }


}


