package com.example.service.impl;

import com.example.entity.StudentEntity;
import com.example.repository.impl.StudentRepositoryImpl;
import com.example.repository.StudentRepository;
import com.example.service.StudentService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

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
    public Future<Integer> insert(int id, String name, int age, String address) {
        return repository.insert(id, name, age, address);
    }

}
