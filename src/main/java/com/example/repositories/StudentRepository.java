package com.example.repositories;

import com.example.entities.StudentEntity;
import io.vertx.core.Future;

import java.util.List;

public interface StudentRepository {

    Future<StudentEntity> findById(String id);

    Future<List<StudentEntity>> findAll();

    Future<String> insert(StudentEntity entity);

    Future<StudentEntity> update(String id, StudentEntity entity);

    Future<Boolean> delete(String id);


}
