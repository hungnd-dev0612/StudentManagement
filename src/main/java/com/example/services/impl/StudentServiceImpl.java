package com.example.services.impl;

import com.example.dto.StudentDTO;
import com.example.entities.StudentEntity;
import com.example.repositories.StudentRepository;
import com.example.repositories.impl.StudentRepositoryImpl;
import com.example.services.StudentService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository repository;

    public StudentServiceImpl(Vertx vertx) {
        this.repository = new StudentRepositoryImpl(vertx);

    }

    @Override
    public Future<List<StudentDTO>> getAll() {
//        JsonObject json = JsonObject.mapFrom()
        Future<List<StudentDTO>> futureListDto = Future.future();
        List<StudentDTO> listDto = new ArrayList<>();
        repository.findAll().setHandler(ar -> {
                    if (ar.succeeded()) {
                        for (StudentEntity student : ar.result()) {
                            JsonObject json = JsonObject.mapFrom(student);
                            StudentDTO dto = json.mapTo(StudentDTO.class);
                            listDto.add(dto);
                        }
                    }
                    futureListDto.complete(listDto);
                }

        );
        return futureListDto;
    }

    @Override
    public Future<StudentDTO> findById(String id) {
        Future<StudentDTO> futureDto = Future.future();
        repository.findById(id).setHandler(handle -> {
            if (handle.succeeded()) {
//                dto luc nay dang tao ra mot doi tuong moi, reference toi StudentDTO()
                JsonObject json = JsonObject.mapFrom(handle.result());
//                con cai nay la minh tham chieu den constructor
//                String ss = new String();
//                StudentDTO dto = new StudentDTO();
                StudentDTO dto = json.mapTo(StudentDTO.class);
                futureDto.complete(dto);
                LOGGER.info("Handle succeeded in findById{}", dto);
            } else {
                LOGGER.error("Error in findById", handle.cause());
            }
        });
        return futureDto;
// code dep. cua Tan
//        return repository.findById(id).map(StudentEntity::convertToDTO);
    }


    @Override
    public Future<String> insert(StudentDTO dto) {
        JsonObject json = JsonObject.mapFrom(dto);
        StudentEntity entity = json.mapTo(StudentEntity.class);
        LOGGER.info("entity: {}", entity);
        return repository.insert(entity);
    }


    @Override
    public Future<StudentEntity> update(String id, StudentDTO studentDTO) {
        studentDTO.setId(id);
        LOGGER.info(studentDTO.getId(), studentDTO.getName());
        JsonObject json = JsonObject.mapFrom(studentDTO);
        StudentEntity entity = json.mapTo(StudentEntity.class);
        LOGGER.info("entity: {}", entity);
        return repository.update(id, entity);
    }

    @Override
    public Future<Boolean> delete(String id) {

        return repository.delete(id);

    }


}
