package com.example.services.impl;

import com.example.dto.StudentDTO;
import com.example.entities.StudentEntity;
import com.example.repositories.StudentRepository;
import com.example.services.StudentService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class StudentServiceImpl implements StudentService {

    private StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Future<List<StudentDTO>> getAll() {
        Future<List<StudentDTO>> listFuture = Future.future();
        List<StudentDTO> listStudent = new ArrayList<>();
        repository.findAll().setHandler(handler -> {
            if (handler.succeeded()) {
                for (StudentEntity entity : handler.result()) {
                    JsonObject json = JsonObject.mapFrom(entity);
                    StudentDTO dto = json.mapTo(StudentDTO.class);
                    listStudent.add(dto);

                }
                listFuture.complete(listStudent);
            } else {
                listFuture.fail("get all service failed");
            }
        });
        return listFuture;
    }

    @Override
    public Future<StudentDTO> findById(String id) {
        return null;
    }

    @Override
    public Future<StudentDTO> insert() {
        return null;
    }

    @Override
    public Future<StudentDTO> update(String id, StudentDTO dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
