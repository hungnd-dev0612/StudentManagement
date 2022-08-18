package com.example.services.impl;

import com.example.dto.ClassDTO;
import com.example.entities.ClassEntity;
import com.example.repositories.ClassRepository;
import com.example.repositories.SpecialityRepository;
import com.example.repositories.impl.ClassRepositoryImpl;
import com.example.repositories.impl.SpecialityRepositoryImpl;
import com.example.services.ClassService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ClassServiceImpl implements ClassService {

    private final ClassRepository repository;

    private final SpecialityRepository specialRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassServiceImpl.class);

    public ClassServiceImpl(Vertx vertx) {
        repository = new ClassRepositoryImpl(vertx);
        specialRepo = new SpecialityRepositoryImpl(vertx);
    }


    @Override
    public Future<ClassDTO> findById(String id) {
        Future<ClassDTO> futureDto = Future.future();
        repository.findById(id).setHandler(handler -> {
            if (handler.succeeded()) {
                JsonObject parseEntity = JsonObject.mapFrom(handler.result());
                ClassDTO dto = parseEntity.mapTo(ClassDTO.class);
                futureDto.complete(dto);
            } else {
                futureDto.fail("fail");
            }
        });

        return futureDto;
    }

    @Override
    public Future<List<ClassDTO>> getAll() {
        Future<List<ClassDTO>> futureDto = Future.future();
        Future<List<ClassEntity>> futureEntity = repository.getAll();
        List<ClassDTO> dtos = new ArrayList<>();

        futureEntity.setHandler(handler -> {
            if (handler.succeeded()) {
                for (ClassEntity entity : handler.result()) {
                    JsonObject json = JsonObject.mapFrom(entity);
                    ClassDTO dto = json.mapTo(ClassDTO.class);
                    dtos.add(dto);
                }
                futureDto.complete(dtos);
            } else {
                futureDto.fail("fail at service");
            }
        });
        return futureDto;
    }

    @Override
    public Future<ClassDTO> insert(ClassDTO dto) {
        Future<ClassDTO> futureDto = Future.future();
        JsonObject json = JsonObject.mapFrom(dto);
        ClassEntity entity = json.mapTo(ClassEntity.class);
        specialRepo.findById(dto.getSpecialitiesId()).setHandler(handler -> {
            if (handler.succeeded()) {
                LOGGER.info("id is matching {}", handler.result());
                repository.insert(entity).setHandler(handle2 -> {
                    if (handler.succeeded()) {
                        JsonObject json2 = JsonObject.mapFrom(handler.result());
                        ClassDTO parseToJson = json2.mapTo(ClassDTO.class);
                        LOGGER.info("json2 {}", json2);
                        LOGGER.info("parseToJson {}", parseToJson);
                        futureDto.complete(parseToJson);
                    } else {
                        LOGGER.error("fail insert");
                        futureDto.fail("fail insert");
                    }
                });
            } else {
                futureDto.fail("Id speciality not found");
            }
        });

        return futureDto;
    }

    @Override
    public Future<ClassDTO> update(String id, ClassDTO dto) {

        Future<ClassDTO> futureDto = Future.future();
        dto.setId(id);
        JsonObject parseDto = JsonObject.mapFrom(dto);
        ClassEntity entity = parseDto.mapTo(ClassEntity.class);
        Future<ClassEntity> futureEntity = repository.update(id, entity);
        futureEntity.setHandler(handler -> {
            if (handler.succeeded()) {
                JsonObject parseEntity = JsonObject.mapFrom(handler.result());
                ClassDTO parseDto2 = parseEntity.mapTo(ClassDTO.class);
                futureDto.complete(parseDto2);
            } else {
                futureDto.fail("update fail");
            }
        });
        return futureDto;
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    @Override
    public Future<ClassDTO> findByName(String name) {
        Future<ClassDTO> futureDto = Future.future();
        repository.findByName(name).setHandler(handler -> {
            if (handler.succeeded()) {
                JsonObject parseToJson = JsonObject.mapFrom(handler.result());
                ClassDTO dto = parseToJson.mapTo(ClassDTO.class);
                futureDto.complete(dto);
            } else {
                futureDto.fail(handler.cause());
            }
        });
        return futureDto;
    }
}
