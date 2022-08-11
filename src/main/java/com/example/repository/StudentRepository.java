package com.example.repository;

import com.example.entity.StudentEntity;
import io.vertx.core.Future;

import java.util.List;

public interface StudentRepository {

    Future<StudentEntity> findById(int id);

    Future<List<StudentEntity>> findAll();

    Future<String> insert(StudentEntity entity);

    Future<StudentEntity> update();

    void delete();
//    Single mongo delete result

}
