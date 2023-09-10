package com.healthybooks.LibraryManagement.bookRepository;

import com.healthybooks.LibraryManagement.bookEntity.BorrowedBooks;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.repository.MongoRepository;

@Configurable
public interface RepositoryBorrowedBooksImpl extends MongoRepository<BorrowedBooks,String> {

}
