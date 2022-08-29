package com.example.services;


import com.example.dto.ClassDTO;
import com.example.dto.ClassDTOv2;
import io.vertx.core.Future;

import java.util.List;

public interface ClassService {
    Future<List<ClassDTO>> getAll();

    Future<ClassDTO> findById(String id);

    Future<ClassDTO> insert(ClassDTO dto, String specialityId);

    Future<ClassDTO> update(String id, ClassDTO dto);

    void delete(String id);


    Future<List<ClassDTOv2>> getAll2();
}
