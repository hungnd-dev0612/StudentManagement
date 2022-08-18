package com.example.services.impl;

import com.example.dto.SpecialityDTO;
import com.example.entities.SpecialityEntity;
import com.example.repositories.SpecialityRepository;
import com.example.repositories.impl.SpecialityRepositoryImpl;
import com.example.services.SpecialityService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialityServiceImpl.class);

    public SpecialityServiceImpl(Vertx vertx) {
        repository = new SpecialityRepositoryImpl(vertx);
    }

    @Override
    public Future<List<SpecialityDTO>> getAll() {
        Future<List<SpecialityEntity>> entityFuture = repository.getAll();
        Future<List<SpecialityDTO>> dtoFuture = Future.future();
        List<SpecialityDTO> listDto = new ArrayList<>();
        entityFuture.setHandler(handler -> {
            if (handler.succeeded()) {
                for (SpecialityEntity entity : handler.result()) {
                    JsonObject jsonObject = JsonObject.mapFrom(entity);
                    SpecialityDTO dto = jsonObject.mapTo(SpecialityDTO.class);
                    listDto.add(dto);
                }
                dtoFuture.complete(listDto);
            } else {
                dtoFuture.fail("failure get all ()");
            }
        });
        return dtoFuture;
    }

    @Override
    public Future<SpecialityDTO> insert(SpecialityDTO dto) {
        Future<SpecialityDTO> dtoFuture = Future.future();
        JsonObject json = JsonObject.mapFrom(dto);
        SpecialityEntity entity = json.mapTo(SpecialityEntity.class);
        repository.insert(entity).setHandler(handle -> {
            if (handle.succeeded()) {
                JsonObject dto2 = JsonObject.mapFrom(handle.result());
                SpecialityDTO sped = dto2.mapTo(SpecialityDTO.class);
                dtoFuture.complete(sped);
            } else {
                dtoFuture.fail("fail at speciality service");
            }
        });
        return dtoFuture;
    }

    @Override
    public Future<SpecialityDTO> update(String id, SpecialityDTO dto) {
        Future<SpecialityDTO> futureDto = findById(id);
        Future<SpecialityDTO> result = Future.future();
        futureDto.setHandler(handler -> {
            if (handler.succeeded()) {
                dto.setId(id);
                JsonObject json = JsonObject.mapFrom(dto);
                SpecialityEntity entity = json.mapTo(SpecialityEntity.class);
                repository.update(id, entity).setHandler(handler2 -> {
                    if (handler2.succeeded()) {
                        JsonObject jsonObject = JsonObject.mapFrom(handler2.result());
                        SpecialityDTO dto2 = jsonObject.mapTo(SpecialityDTO.class);
                        result.complete(dto2);
                    } else {
                        result.fail(handler2.cause());
                    }
                });
            } else {
                result.fail(handler.cause());
            }
        });

        return result;
    }

    @Override
    public Future<SpecialityDTO> findById(String id) {
        Future<SpecialityDTO> futureDto = Future.future();
        Future<SpecialityEntity> entity = repository.findById(id);
        entity.setHandler(handler -> {
            if (handler.succeeded()) {
                JsonObject json = JsonObject.mapFrom(handler.result());
                LOGGER.info("json data {}", handler.result());
                SpecialityDTO parse = json.mapTo(SpecialityDTO.class);
                futureDto.complete(parse);
            } else {
                LOGGER.error("Get the error ");
                futureDto.fail("handler.cause()");
            }
        });

        return futureDto;
    }

    @Override
    public Future<SpecialityDTO> findByName(String name) {
        Future<SpecialityDTO> futureDTO = Future.future();
        repository.findByName(name).setHandler(handler -> {
            if (handler.succeeded()) {
                JsonObject json = JsonObject.mapFrom(handler.result());
                SpecialityDTO dto = json.mapTo(SpecialityDTO.class);
                futureDTO.complete(dto);
            } else {
                futureDTO.fail("service fail");
            }
        });
        return futureDTO;
    }

    @Override
    public Future<Boolean> delete(String id) {
        Future<Boolean> future = Future.future();
         repository.delete(id).setHandler(handler -> {
            if(handler.succeeded()){

            }
        });

        return future;
    }
}
