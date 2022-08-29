package com.example.repositories;

import com.example.entities.ClassEntity;
import com.example.entities.SpecialityEntity;
import io.vertx.core.Future;

import java.util.List;

public interface SpecialityRepository {
    Future<List<SpecialityEntity>> findAll();

    Future<SpecialityEntity> findById(String id);

    Future<SpecialityEntity> update(String id, SpecialityEntity entity);

    Future<SpecialityEntity> insert(SpecialityEntity entity);

    void delete(String id);

    Future<Boolean> checkIfNameIsExist(String name);
}
