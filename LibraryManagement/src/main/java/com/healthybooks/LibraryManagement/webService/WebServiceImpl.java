package com.healthybooks.LibraryManagement.webService;

import com.healthybooks.LibraryManagement.ExceptionalHandelling.BookNotFoundException;
import com.healthybooks.LibraryManagement.bookEntity.BookCategory;
import com.healthybooks.LibraryManagement.bookEntity.Books;
import com.healthybooks.LibraryManagement.bookEntity.BorrowedBooks;
import com.healthybooks.LibraryManagement.bookRepository.RepositoryBookCategoryImpl;
import com.healthybooks.LibraryManagement.bookRepository.RepositoryBorrowedBooksImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WebServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceImpl.class);

    @Autowired
    private RepositoryBookCategoryImpl bookRepository;
    @Autowired
    private RepositoryBorrowedBooksImpl repositoryBorrowedBooks;
    public List<BookCategory> getAllBooks() {
        return  bookRepository.findAll();
    }

    public Optional<BookCategory> getBookById(String id) {
        return  bookRepository.findById(id);
    }

    public List<BookCategory> getBooksListByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<BookCategory> getBooksListCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    synchronized public void saveBooks(BookCategory books) {
        books.setEntityCreationDate(LocalDate.now());
        ArrayList<Books> listOfBooks = new ArrayList<Books>();
        int countBookAvailable =0;
        for(Books tempIndividualBooks : books.getIndividualBooksObj()){
            countBookAvailable+=1;
            if(tempIndividualBooks.isIssued() != true){
                tempIndividualBooks.setBookIssueDate(null);
                tempIndividualBooks.setBookReturnDate(null);
                tempIndividualBooks.setLastModified(LocalDate.now());
                tempIndividualBooks.setIssuerId(null);
                tempIndividualBooks.setIssued(false);
            }
            listOfBooks.add(tempIndividualBooks);
        }
        books.setNoOfBookAvailable(countBookAvailable);
        bookRepository.save(books);
    }

    public boolean checkAvailability(String id) {
        return bookRepository.findById(id).isPresent();
    }

    synchronized public Optional<BookCategory> updateBookById(String bookID, BookCategory book) {
        BookCategory bookResponse = bookRepository.findById(bookID).get();
        bookResponse.setAuthor(book.getAuthor());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setCategory(book.getCategory());
        bookResponse.setBookID(book.getBookID());
        bookResponse.setRack(book.getRack());
        bookResponse.setFloor(book.getFloor());
        bookResponse.setDepartment(book.getDepartment());
        bookResponse.setNoOfBookAvailable(book.getNoOfBookAvailable());
        bookResponse.setTotalNoOfBooks(book.getTotalNoOfBooks());
        bookResponse.setIndividualBooksObj(bookResponse.getIndividualBooksObj());
        bookResponse.setEntityCreationDate(LocalDate.now());
        bookResponse.setPublishedYear(book.getPublishedYear());
        bookResponse.setBookID(bookID);
        bookRepository.save(bookResponse);
        return bookRepository.findById(bookID);
    }
    public List<BookCategory> getBooksListByDepartment(String department) {
        return bookRepository.findByType(department);
    }

    synchronized public Optional<BookCategory> removeBookByID(String id) {
        if(checkAvailability(id)){
            Optional<BookCategory> bookResponse = bookRepository.findById(id);
            bookRepository.deleteById(id);
            return bookResponse;
        }else{
            throw  new BookNotFoundException("Book Not Found");
        }
    }

    public  BookCategory  removeBookFromBookCategory(BookCategory bookCategory,String bookISBN){
        List<Books> listOfBookAvailable= bookCategory.getIndividualBooksObj();
        List<Books> filteredBooks=new ArrayList<Books>();
        for(Books tempBooksObj:listOfBookAvailable){
                if(!bookISBN.equals(tempBooksObj.getBookISBN())){
                    filteredBooks.add(tempBooksObj);
                }
            }
        bookCategory.setIndividualBooksObj(filteredBooks);
        return bookCategory;
        }

    synchronized public Optional<BookCategory> borrowerBookByID(String bookISBN) {
        List<BookCategory> listOfBookCategory = bookRepository.findAll();
        String bookId=null;
        for(BookCategory tempBorrowedBooks:listOfBookCategory){
            for(Books tempBooks : tempBorrowedBooks.getIndividualBooksObj()){
                if(tempBooks!=null && bookISBN.equals(tempBooks.getBookISBN())){
                    bookId=tempBorrowedBooks.getBookID();
                    break;
                }
            }
        }
        if(bookId !=null){
            BookCategory bookCategory = bookRepository.findById(bookId).get();
            BorrowedBooks filteredBorrowedBooks = new BorrowedBooks(bookCategory);
            List<Books> listBooksObj = new ArrayList<Books>();
            for (Books tempBooksObj: filteredBorrowedBooks.getIndividualBooksObj()){
                if(bookISBN.equals(tempBooksObj.getBookISBN())){
                    tempBooksObj.setBookReturnDate(LocalDate.now().plusDays(15));
                    tempBooksObj.setBookIssueDate(LocalDate.now());
                    listBooksObj.add(tempBooksObj);
                }
            }
            filteredBorrowedBooks.setIndividualBooksObj(listBooksObj);
            repositoryBorrowedBooks.save(filteredBorrowedBooks);
            List<Books> booksToReturn = bookCategory.getIndividualBooksObj();
            BookCategory filteredBookCategory = removeBookFromBookCategory(bookCategory,bookISBN);
            bookRepository.save(filteredBookCategory);
            bookCategory.setIndividualBooksObj(booksToReturn);
            return Optional.of(bookCategory);
        }else{
            throw new BookNotFoundException("book not found");
        }
    }
    public Optional<BorrowedBooks> getBorrowedById(String id) {
       return repositoryBorrowedBooks.findById(id);
    }
    public List<BorrowedBooks> getAllBorrowedBooks() {
        return repositoryBorrowedBooks.findAll();
    }

    public BookCategory copyingObjectValue(BookCategory bookCategory,BorrowedBooks borrowedBooks,Books bookObjForAddition){
        bookCategory.setFloor(borrowedBooks.getFloor());
        bookCategory.setBookID(borrowedBooks.getBookID());
        bookCategory.setCategory(borrowedBooks.getCategory());
        bookCategory.setRack(borrowedBooks.getRack());
        bookCategory.setTitle(borrowedBooks.getTitle());
        bookCategory.setAuthor(borrowedBooks.getAuthor());
        bookCategory.setNoOfBookAvailable(bookCategory.getNoOfBookAvailable()+1);
        List<Books> borrowedBooksRepoObj = borrowedBooks.getIndividualBooksObj();
        borrowedBooksRepoObj.add(bookObjForAddition);
        bookCategory.setIndividualBooksObj(borrowedBooksRepoObj);
        return bookCategory;
    }
    public BookCategory convertibleFromBorrowedBook(String bookId,Books bookObjForAddition){
        BorrowedBooks borrowedBooks = repositoryBorrowedBooks.findById(bookId).get();
        if(bookRepository.findById(borrowedBooks.getBookID()).isPresent()){
            BookCategory bookCategory = bookRepository.findById(bookId).get();
            return  copyingObjectValue(bookCategory,borrowedBooks,bookObjForAddition);
        }else{
            BookCategory bookCategory = new BookCategory();
            return copyingObjectValue(bookCategory,borrowedBooks,bookObjForAddition);
        }
    }
    public BorrowedBooks removeBookFromBorrowedBookDocument(String bookISBN,String bookIdToReturn){
        BorrowedBooks borrowedBooksObj =repositoryBorrowedBooks.findById(bookIdToReturn).get();
        List<Books> listOfFilteredBorrowedBooks= new ArrayList<Books>();
        for(Books tempBooksObj : borrowedBooksObj.getIndividualBooksObj()){
            if(!bookISBN.equals(tempBooksObj.getBookISBN())){
                listOfFilteredBorrowedBooks.add(tempBooksObj);
            }
        }
        borrowedBooksObj.setIndividualBooksObj(listOfFilteredBorrowedBooks);
        return borrowedBooksObj;
    }

    synchronized public BookCategory returnBookByID(String bookISBN) {
        BookCategory bookCategory = null;
        List<BorrowedBooks> repoBorrowedBookObject = repositoryBorrowedBooks.findAll();
        String bookIdToReturn = null;
        Books booksToAddOnBookCategory=null;
        for(BorrowedBooks tempBorrowedBook : repoBorrowedBookObject){
           for(Books tempBookObj: tempBorrowedBook.getIndividualBooksObj()){
               if(bookISBN.equals(tempBookObj.getBookISBN())){
                   bookIdToReturn = tempBorrowedBook.getBookID();
                   booksToAddOnBookCategory=tempBookObj;
                   break;
               }
           }
        }

        if(bookIdToReturn!=null){
            if(repositoryBorrowedBooks.findById(bookIdToReturn).isPresent()){
                bookCategory = convertibleFromBorrowedBook(bookIdToReturn,booksToAddOnBookCategory);
                bookRepository.save(bookCategory);
                BorrowedBooks filteredBorrowedBook=removeBookFromBorrowedBookDocument(bookISBN,bookIdToReturn);
                repositoryBorrowedBooks.save(filteredBorrowedBook);
                return bookCategory;
            }
        }
    return bookCategory;
    }


}
