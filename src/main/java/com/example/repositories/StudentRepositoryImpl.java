package com.example.repositories;

import com.example.connectdb.MongoDBClient;
import com.example.entities.StudentEntity;
import com.example.utils.SomeContants;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.Future;

public class StudentRepositoryImpl implements StudentRepository {

    private MongoClient client;

    public StudentRepositoryImpl(MongoClient client) {
        this.client = client;
    }

    @Override
    public Future<List<StudentEntity>> findAll() {
        Future<List<StudentEntity>> listFuture = Future.future();
        List<StudentEntity> listEntity = new ArrayList<>();
        JsonObject query = new JsonObject();
        client.find(SomeContants.STUDENT_COLLECTION, query, resultHandler -> {
            if (resultHandler.succeeded()) {
                for (JsonObject jsonObject : resultHandler.result()) {
                    StudentEntity studentEntity = jsonObject.mapTo(StudentEntity.class);
                    listEntity.add(studentEntity);
                }
                listFuture.complete(listEntity);
            } else {
                listFuture.fail("get all student failed");
            }
        });
        return listFuture;
    }

    @Override
    public Future<StudentEntity> findById(String id) {
        return null;
    }

    @Override
    public Future<StudentEntity> update(String id, StudentEntity entity) {
        return null;
    }

    @Override
    public Future<StudentEntity> insert(StudentEntity entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
