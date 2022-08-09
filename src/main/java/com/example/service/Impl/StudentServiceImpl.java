package com.example.service.Impl;

import com.example.model.Student;
import com.example.repository.Impl.StudentRepositoryImpl;
import com.example.repository.StudentRepository;
import com.example.service.StudentService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class StudentServiceImpl implements StudentService {

    StudentRepository repository;

    public StudentServiceImpl(Vertx vertx) {
        this.repository = new StudentRepositoryImpl(vertx);

    }

    @Override
    public Future<String> get() {
        Future<String> future = Future.future();
        future.complete("danghung");
        return future;
    }

    @Override
    public Future<Student> findById(int id) {
        return repository.findById(id);
    }

}
