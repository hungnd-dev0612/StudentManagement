package com.example.repositories;

import com.example.entities.ClassEntity;
import io.vertx.core.Future;

import java.util.List;

public interface ClassRepository {

    Future<ClassEntity> findById(String id);

    Future<List<ClassEntity>> getAll();

    Future<ClassEntity> insert(ClassEntity entity);

    Future<ClassEntity> update(String id, ClassEntity entity);

    void delete(String id);

    Future<ClassEntity> findByName(String name);

}
