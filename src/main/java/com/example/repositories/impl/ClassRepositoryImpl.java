package com.example.repositories.impl;

import com.example.connectdb.MongoDBClient;
import com.example.dto.ClassDTO;
import com.example.entities.ClassEntity;
import com.example.repositories.ClassRepository;
import com.example.utils.SomeContants;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ClassRepositoryImpl implements ClassRepository {

    private final MongoClient client;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRepositoryImpl.class);

    public ClassRepositoryImpl(Vertx vertx) {

        client = new MongoDBClient().client(vertx);
    }


    @Override
    public Future<ClassEntity> findById(String id) {
//        client.removeDocument()
        JsonObject query = new JsonObject().put("_id", id);
        Future<ClassEntity> futureEntity = Future.future();
        client.findOne(SomeContants.CLASS_COLLECTION, query, null, ar -> {
            if (ar.succeeded()) {
                JsonObject json = JsonObject.mapFrom(ar.result());
                ClassEntity classEntity = json.mapTo(ClassEntity.class);
                futureEntity.complete(classEntity);
            } else {
                futureEntity.fail("fail ");
            }
        });
        return futureEntity;
    }

    @Override
    public Future<List<ClassEntity>> getAll() {
        Future<List<ClassEntity>> futureClassEntity = Future.future();
        List<ClassEntity> entities = new ArrayList<>();
        client.find(SomeContants.CLASS_COLLECTION, new JsonObject(), ar -> {
            if (ar.succeeded()) {
                for (JsonObject jsonObject : ar.result()) {
                    ClassEntity entity = jsonObject.mapTo(ClassEntity.class);
                    entities.add(entity);
                }
                futureClassEntity.complete(entities);
            } else {
                futureClassEntity.fail("fail");
            }
        });
        return futureClassEntity;
    }

    @Override
    public Future<ClassEntity> insert(ClassEntity entity) {
        Future<ClassEntity> futureEntity = Future.future();
        entity.set_id(ObjectId.get().toHexString());
        JsonObject query = JsonObject.mapFrom(entity);
        client.insert(SomeContants.CLASS_COLLECTION, query, ar -> {
            if (ar.succeeded()) {
                LOGGER.info("insert succeeded {}", entity);
                futureEntity.complete(entity);
            } else {
                LOGGER.error("query db fail", ar.cause());
                futureEntity.fail(ar.cause());
            }
        });
        return futureEntity;
    }

    @Override
    public Future<ClassEntity> update(String id, ClassEntity entity) {
        Future<ClassEntity> futureEntity = Future.future();
        JsonObject query = new JsonObject().put("_id", id);
        JsonObject parse = JsonObject.mapFrom(entity);
        JsonObject update = new JsonObject().put("$set", parse);
        client.updateCollection(SomeContants.CLASS_COLLECTION, query, update, ar -> {
            if (ar.succeeded()) {
                futureEntity.complete(entity);
            } else {
                futureEntity.fail("fail update");
            }

        });
        return futureEntity;
    }

    @Override
    public void delete(String id) {
        JsonObject query = new JsonObject().put("_id", id);
        client.removeDocument(SomeContants.CLASS_COLLECTION, query, ar -> {
            if (ar.succeeded()) {
                ar.result();
            } else {
                ar.cause();
            }
        });
    }

    @Override
    public Future<ClassEntity> findByName(String name) {
        Future<ClassEntity> futureEntity = Future.future();
        JsonObject query = new JsonObject().put("name", name);
        JsonObject field = new JsonObject();
        client.findOne(SomeContants.CLASS_COLLECTION, query, field, ar -> {
            if (ar.succeeded()) {
                ClassEntity entity = ar.result().mapTo(ClassEntity.class);
                futureEntity.complete(entity);
            } else {
                futureEntity.fail(ar.cause());
            }
        });

        return futureEntity;

    }
}
