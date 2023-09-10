package com.healthybooks.borrowerService.repository;

import com.healthybooks.borrowerService.entity.Borrowers;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.repository.MongoRepository;

@Configurable
public interface RepositoryImpl extends MongoRepository<Borrowers,Integer> {


}
