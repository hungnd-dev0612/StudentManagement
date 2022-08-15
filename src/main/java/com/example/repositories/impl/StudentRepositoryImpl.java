package com.example.repositories.impl;

import com.example.entities.StudentEntity;
import com.example.repositories.StudentRepository;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class StudentRepositoryImpl implements StudentRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentRepositoryImpl.class);
    private final MongoClient client;
    private static final String STUDENT_COLLECTION = "students";

    public StudentRepositoryImpl(Vertx vertx) {
        JsonObject config = new JsonObject()
                .put("connection_string", "mongodb://localhost:27017")
                .put("db_name", "local");
        client = MongoClient.createShared(vertx, config);
    }

    @Override
    public Future<StudentEntity> findById(String id) {
        Future<StudentEntity> future = Future.future();
        JsonObject query = new JsonObject().put("_id", id);
        client.findOne(STUDENT_COLLECTION, query, null, result -> {
            if (result.succeeded()) {
                JsonObject json = result.result();
                try {
                    StudentEntity student = json.mapTo(StudentEntity.class);
                    LOGGER.info("future object {}", future);
                    future.complete(student);
                } catch (Exception exception) {
                    future.fail(exception);
                    exception.printStackTrace();

                }

            } else {
                future.fail(result.cause());
            }
        });
        return future;
    }

    @Override
    public Future<List<StudentEntity>> findAll() {
        Future<List<StudentEntity>> future = Future.future();
        JsonObject query = new JsonObject();
        client.find(STUDENT_COLLECTION, query, ar -> {
            List<StudentEntity> entity;
            if (ar.succeeded()) {
                List<JsonObject> data = ar.result();
                entity = data.stream().map(student -> student.mapTo(StudentEntity.class)).collect(Collectors.toList());
                LOGGER.info("data {}", data);
                future.complete(entity);

            } else {
                LOGGER.error("result error");
                future.fail(ar.cause());
            }


        });


        return future;
    }

    @Override
    public Future<String> insert(StudentEntity entity) {
        entity.set_id(ObjectId.get().toHexString());
        Future<String> future = Future.future();
        JsonObject query = JsonObject.mapFrom(entity);
//        if (entity.get_id() == null) {
//            query.remove("_id");
//        }
        LOGGER.info("get entity {}", query);
        client.insert(STUDENT_COLLECTION, query, result -> {
            if (result.succeeded()) {
                future.complete(entity.get_id());
            } else {
                future.fail(result.cause());
            }
        });

        return future;
    }

    @Override
    public Future<StudentEntity> update(String id, StudentEntity entity) {
        Future<StudentEntity> future = Future.future();
        JsonObject json = JsonObject.mapFrom(entity);
        JsonObject json2 = new JsonObject().put("$set", json);
        JsonObject query = new JsonObject().put("_id", id);
        if (id != null) {
            client.updateCollection(STUDENT_COLLECTION, query, json2, res -> {
                if (res.succeeded()) {
                    LOGGER.info("Update succeeded {}", res.result());
                    future.complete(entity);
                } else {
                    future.fail("Fail future");
                    LOGGER.error("Update fail ", res.cause());
                }
            });
        }

        return future;
    }


    @Override
    public void delete() {

    }

}
