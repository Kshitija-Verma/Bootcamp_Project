package com.mongodb.mongoApp.repositories;

import com.mongodb.mongoApp.entities.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student,String> {

    @Query("{'name' : ?0 }")
    Student findByName(String name);

    @Query("{standard:{$lt:?0,$gt:?1}}")
    List<Student> findByLessThanGreaterThan(int lt,int bt);

    @Query("{'age':?0}")
    List<Student> findByAge(Integer age);
}
