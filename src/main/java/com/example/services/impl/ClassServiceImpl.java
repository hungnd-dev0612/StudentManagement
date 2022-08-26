package com.example.services.impl;

import com.example.dto.ClassDTO;
import com.example.dto.ClassDTOv2;
import com.example.entities.ClassEntity;
import com.example.repositories.ClassRepository;
import com.example.repositories.SpecialityRepository;
import com.example.services.ClassService;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ClassServiceImpl implements ClassService {
    private final ClassRepository repository;
    private final SpecialityRepository specRepo;
    private final Logger LOGGER = LoggerFactory.getLogger(ClassServiceImpl.class);

    public ClassServiceImpl(ClassRepository repository, SpecialityRepository specRepo) {
        this.repository = repository;
        this.specRepo = specRepo;
    }

    @Override
    public Future<List<ClassDTO>> getAll() {
        Future<List<ClassDTO>> listFuture = Future.future();
        repository.findAll().setHandler(listAr -> {
            if (listAr.succeeded()) {
                List<ClassDTO> list = new ArrayList<>();
                for (ClassEntity entity : listAr.result()) {
                    ClassDTO dto = ClassDTO.convertToDTO(entity);
                    list.add(dto);
                }

                listFuture.complete(list);
            } else {
                listFuture.fail("failed");
            }
        });
        return listFuture;
    }

    @Override
    public Future<ClassDTO> findById(String id) {
        Future<ClassDTO> dtoFuture = Future.future();
        repository.findById(id).setHandler(ar -> {
            if (ar.succeeded()) {
                ClassDTO dto = ClassDTO.convertToDTO(ar.result());
                dtoFuture.complete(dto);
            } else {
                dtoFuture.fail("service fail");
            }
        });
        return dtoFuture;
    }

    @Override
    public Future<ClassDTO> insert(ClassDTO dto, String specialityId) {
        Future<ClassDTO> dtoFuture = Future.future();
        ClassEntity entity = ClassDTO.convertToEntity(dto);
        specRepo.findById(specialityId).setHandler(result -> {
            if (result.succeeded()) {
                repository.insert(entity).setHandler(ar -> {
                    if (ar.succeeded()) {
                        ClassDTO parseToDto = ClassDTO.convertToDTO(ar.result());
                        dtoFuture.complete(parseToDto);
                    } else {
                        dtoFuture.fail(ar.cause());
                    }
                });
            } else {
                dtoFuture.fail(result.cause());
            }
        });

        return dtoFuture;
    }

    @Override
    public Future<ClassDTO> update(String id, ClassDTO dto) {
        Future<ClassDTO> dtoFuture = Future.future();
        dto.setId(id);
        ClassEntity entity = ClassDTO.convertToEntity(dto);
        repository.update(id, entity).setHandler(ar -> {
            if (ar.succeeded()) {
                ClassDTO parseToDto = ClassDTO.convertToDTO(ar.result());
                dtoFuture.complete(parseToDto);
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

    @Override
    public Future<List<ClassDTOv2>> getAll2() {
        Future<List<ClassDTOv2>> listDtoFuture = Future.future();
        List<ClassDTOv2> listDto2 = new ArrayList<>();
        repository.findAll().setHandler(ar -> {
            if (ar.succeeded()) {
                for (ClassEntity entity1 : ar.result()) {
                    specRepo.findById(entity1.getSpecialitiesId()).setHandler(handler -> {
                        if (handler.succeeded()) {
                            ClassDTOv2 dtov2 = new ClassDTOv2();
                            String specialitiesName = handler.result().getName();
                            dtov2.setSpecialitiesName(specialitiesName);
                            dtov2.setClassId(entity1.get_id());
                            dtov2.setClassName(entity1.getName());
                            dtov2.setSpecialitiesId(entity1.getSpecialitiesId());
                            LOGGER.info(dtov2.getSpecialitiesName());
                            listDto2.add(dtov2);
                        }
                        if (handler.succeeded()) {
                            listDtoFuture.complete(listDto2);
                        } else {
                            listDtoFuture.fail("fail");
                        }
                    });

                }

            }

        });
        return listDtoFuture;
    }
}
