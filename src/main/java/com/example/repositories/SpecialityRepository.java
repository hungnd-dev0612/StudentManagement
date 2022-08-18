package com.example.repositories;

import com.example.entities.SpecialityEntity;
import io.vertx.core.Future;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

public interface SpecialityRepository {
    Future<SpecialityEntity> insert(SpecialityEntity entity);

    Future<List<SpecialityEntity>> getAll();

    Future<SpecialityEntity> findById(String id);

    Future<SpecialityEntity> update(String id, SpecialityEntity entity);

    Future<Boolean> delete(String id);

    Future<SpecialityEntity> findByName(String name);


}
