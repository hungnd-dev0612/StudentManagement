package com.example.repositories;

import com.example.entities.ClassEntity;
import io.vertx.core.Future;

import java.util.List;

public interface ClassRepository {
    Future<List<ClassEntity>> findAll();

    Future<ClassEntity> findById(String id);

    Future<ClassEntity> update(String id, ClassEntity entity);

    Future<ClassEntity> insert(ClassEntity entity);

    void delete(String id);
}
