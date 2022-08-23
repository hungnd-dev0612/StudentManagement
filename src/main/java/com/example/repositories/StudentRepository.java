package com.example.repositories;

import com.example.entities.StudentEntity;
import io.vertx.core.Future;

import java.util.List;


public interface StudentRepository {
    Future<List<StudentEntity>> findAll();

    Future<StudentEntity> findById(String id);

    Future<StudentEntity> update(String id, StudentEntity entity);

    Future<StudentEntity> insert(StudentEntity entity);

    void delete(String id);
}
