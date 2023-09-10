package com.healthybooks.LibraryManagement.bookRepository;

import com.healthybooks.LibraryManagement.bookEntity.BookCategory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@Configurable
public interface RepositoryBookCategoryImpl extends MongoRepository<BookCategory,String> {
    @Query(value = "{'department':?0}")
    public List<BookCategory> findByType(String department);

    @Query("{name:'?0'}")
    BookCategory findByName(String name);

    @Query(value = "{'category':?0}")
    List<BookCategory> findByCategory(String category);

    @Query(value = "{'author':?0}")
    List<BookCategory> findByAuthor(String author);
}
