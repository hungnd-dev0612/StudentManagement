package com.example.services;

import com.example.dto.ClassDTO;
import io.vertx.core.Future;

import java.util.List;

public interface ClassService {
    Future<ClassDTO> findById(String id);

    Future<List<ClassDTO>> getAll();

    Future<ClassDTO> insert(ClassDTO dto);

    Future<ClassDTO> update(String id, ClassDTO dto);

    void delete(String id);

    Future<ClassDTO> findByName(String name);
}
