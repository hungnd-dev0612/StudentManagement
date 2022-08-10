package com.example.service;

import com.example.entity.StudentEntity;
import io.vertx.core.Future;

import java.util.List;

public interface StudentService {
    Future<StudentEntity> findById(int id);

    Future<List<StudentEntity>> getAll();

    Future<Integer> insert(int id, String name, int age, String address);

}
