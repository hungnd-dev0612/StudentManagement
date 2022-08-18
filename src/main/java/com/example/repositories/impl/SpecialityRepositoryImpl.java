package com.example.repositories.impl;

import com.example.connectdb.MongoDBClient;
import com.example.entities.SpecialityEntity;
import com.example.repositories.SpecialityRepository;
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

public class SpecialityRepositoryImpl implements SpecialityRepository {

    private final String SPECIALITY_COLLECTION = "specialities";

    private final MongoClient client;
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialityRepositoryImpl.class);

    public SpecialityRepositoryImpl(Vertx vertx) {

        client = new MongoDBClient().client(vertx);
    }

    @Override
    public Future<SpecialityEntity> insert(SpecialityEntity entity) {

        entity.set_id(ObjectId.get().toHexString());
        LOGGER.info("entity {}", entity);
        Future<SpecialityEntity> getResult = Future.future();
        JsonObject json = JsonObject.mapFrom(entity);
        client.insert(SPECIALITY_COLLECTION, json, ar -> {
            if (ar.succeeded()) {
                LOGGER.info("return  {}", entity);
                getResult.complete(entity);
            } else {
                getResult.fail(ar.cause());
            }
        });
        return getResult;
    }

    @Override
    public Future<List<SpecialityEntity>> getAll() {
        Future<List<SpecialityEntity>> future = Future.future();
        JsonObject query = new JsonObject();
        client.find(SPECIALITY_COLLECTION, query, ar -> {
            if (ar.succeeded()) {
                LOGGER.info("data query {}", ar.result());
                List<JsonObject> listResult = ar.result();
                List<SpecialityEntity> listEntity = new ArrayList<>();
                for (JsonObject json : listResult) {
                    SpecialityEntity entity = json.mapTo(SpecialityEntity.class);
                    listEntity.add(entity);
                }
                LOGGER.info("future complete {}", listEntity);
                future.complete(listEntity);
            } else {
                future.fail("Query error");
            }
        });
        return future;
    }

    @Override
    public Future<SpecialityEntity> findById(String id) {
        Future<SpecialityEntity> specialEntity = Future.future();
        JsonObject query = new JsonObject().put("_id", id);
        client.findOne(SPECIALITY_COLLECTION, query, null, ar -> {
            if (ar.succeeded()) {
                LOGGER.info("Id: {}", ar.result());
                if (ar.result() != null) {
                    SpecialityEntity entity = ar.result().mapTo(SpecialityEntity.class);
                    specialEntity.complete(entity);
                } else {
                    specialEntity.fail(ar.cause());
                }
            } else {
                specialEntity.fail("khong tim thay id nay");
                LOGGER.error("future get wrong", ar.cause());
            }
        });
        return specialEntity;
    }

    @Override
    public Future<SpecialityEntity> update(String id, SpecialityEntity entity) {
        Future<SpecialityEntity> future = Future.future();
        JsonObject setId = new JsonObject().put("_id", id);
        JsonObject parseEntity = JsonObject.mapFrom(entity);
        JsonObject query = new JsonObject().put("$set", parseEntity);
        client.updateCollection(SPECIALITY_COLLECTION, setId, query, ar -> {
                    if (ar.succeeded()) {
                        LOGGER.info("doc modified{}", ar.result().getDocModified());
                        LOGGER.info("entity update {}", entity);
                        future.complete(entity);
                    } else {
                        future.fail("error in db");
                    }

                }
        );
        return future;
    }

    @Override
    public Future<Boolean> delete(String id) {
        Future<Boolean> future = Future.future();
        JsonObject json = new JsonObject().put("_id", id);
        JsonObject obj = new JsonObject().put("delete", "succeeded");
        client.removeDocument("specialities", json, ar -> {
            if (ar.succeeded()) {
                LOGGER.info("speciality {}", ar.result());
            } else {
                LOGGER.error("error {}", ar.cause());
            }
        });
        return future;
    }

    @Override
    public Future<SpecialityEntity> findByName(String name) {
        Future<SpecialityEntity> futureEntity = Future.future();
        JsonObject query = new JsonObject().put("name", name);
        JsonObject field = new JsonObject();
        client.findOne(SomeContants.SPECIALITY_COLLECTION, query, field, ar -> {
            if (ar.succeeded()) {
                SpecialityEntity entity = ar.result().mapTo(SpecialityEntity.class);
                futureEntity.complete(entity);
            } else {
                futureEntity.fail("fail result");
            }
        });
        return futureEntity;
    }
}
