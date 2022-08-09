package com.example.repository;

import com.example.model.Student;
import io.vertx.core.Future;

public interface StudentRepository {

    Future<Student> findById(int id);

}
