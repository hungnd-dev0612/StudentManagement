package com.example.service;

import com.example.model.Student;
import io.vertx.core.Future;

public interface StudentService {

    Future<String> get();
    Future<Student> findById(int id);

}
