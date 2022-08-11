package com.example.service.impl;

import com.example.dto.StudentDTO;
import com.example.entity.StudentEntity;
import com.example.repository.impl.StudentRepositoryImpl;
import com.example.repository.StudentRepository;
import com.example.service.StudentService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    StudentRepository repository;

    public StudentServiceImpl(Vertx vertx) {
        this.repository = new StudentRepositoryImpl(vertx);

    }

    @Override
    public Future<StudentEntity> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Future<List<StudentEntity>> getAll() {
        return repository.findAll();
    }

    @Override
    public Future<String> insert(StudentDTO dto) {
        StudentEntity entity = new StudentEntity();

        return repository.insert(entity);
    }

//    @Override
//    public Future<String> insert(StudentEntity entity) {
//        return repository.insert(entity);
//    }

}
