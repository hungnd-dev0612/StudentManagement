package com.example.services.impl;

import com.example.dto.ClassDTO;
import com.example.dto.StudentDTO;
import com.example.entities.ClassEntity;
import com.example.entities.StudentEntity;
import com.example.repositories.StudentRepository;
import com.example.services.StudentService;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    private final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Future<List<StudentDTO>> getAll() {
        Future<List<StudentDTO>> listFuture = Future.future();
        repository.findAll().setHandler(ar -> {
            if (ar.succeeded()) {
                List<StudentDTO> listStudent = new ArrayList<>();
                for (StudentEntity entity : ar.result()) {
                    LOGGER.info("result{}", ar.result());
                    JsonObject json = JsonObject.mapFrom(entity);
                    LOGGER.info("json{}", json);
                    StudentDTO dto = json.mapTo(StudentDTO.class);
                    LOGGER.warn("dto{}", dto);
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
        Future<StudentDTO> studentDTOFuture = Future.future();
        repository.findById(id).setHandler(ar -> {
            if (ar.succeeded()) {
                StudentDTO dto = JsonObject.mapFrom(ar.result()).mapTo(StudentDTO.class);
                studentDTOFuture.complete(dto);
            } else {
                studentDTOFuture.fail(ar.cause());
            }
        });
        return studentDTOFuture;
    }

    @Override
    public Future<StudentDTO> insert(StudentDTO dto) {
        Future<StudentDTO> dtoFuture = Future.future();
        StudentEntity entity = JsonObject.mapFrom(dto).mapTo(StudentEntity.class);
        repository.insert(entity).setHandler(ar -> {
            if (ar.succeeded()) {

                StudentDTO parseToDto = JsonObject.mapFrom(ar.result()).mapTo(StudentDTO.class);
                dtoFuture.complete(parseToDto);
            } else {
                dtoFuture.fail(ar.cause());
            }
        });
        return dtoFuture;
    }

    @Override
    public Future<StudentDTO> update(String id, StudentDTO dto) {
        Future<StudentDTO> dtoFuture = Future.future();
        dto.setId(id);
        StudentEntity entity = JsonObject.mapFrom(dto).mapTo(StudentEntity.class);
        repository.update(id, entity).setHandler(ar -> {
            if (ar.succeeded()) {
                StudentDTO dto1 = JsonObject.mapFrom(ar.result()).mapTo(StudentDTO.class);
                dtoFuture.complete(dto1);
            } else {
                dtoFuture.fail(ar.cause());
            }
        });
        return dtoFuture;
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }
}
