package com.healthybooks.librarianService.repository;

import com.healthybooks.librarianService.entity.Librarian;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.repository.MongoRepository;

@Configurable
public interface RepostioryImpl extends MongoRepository<Librarian,Integer> {


}
