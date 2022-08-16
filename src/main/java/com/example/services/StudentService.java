package com.example.services;

import com.example.dto.StudentDTO;
import com.example.entities.StudentEntity;
import io.vertx.core.Future;

import java.util.List;

public interface StudentService {
    Future<StudentDTO> findById(String id);

    Future<List<StudentDTO>> getAll();

    Future<String> insert(StudentDTO dto);

    //    Future<>
    Future<StudentEntity> update(String id, StudentDTO dto);

    Future<Boolean> delete(String id);
}
