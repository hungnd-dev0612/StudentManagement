package com.example.services;

import com.example.dto.StudentDTO;
import io.vertx.core.Future;

import java.util.List;


public interface StudentService {

    Future<List<StudentDTO>> getAll();

    Future<StudentDTO> findById(String id);

    Future<StudentDTO> insert(StudentDTO dto);

    Future<StudentDTO> update(String id, StudentDTO dto);

    void delete(String id);
}
