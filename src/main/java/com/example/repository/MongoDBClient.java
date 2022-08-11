package com.example.repository;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoDBClient {

    MongoClient mongoClient;

    public MongoClient client(Vertx vertx) {
        JsonObject config = new JsonObject()
                .put("connection_string", "mongodb://localhost:27017")
                .put("db_name", "students");
        return MongoClient.createShared(vertx, config);
    }

}
