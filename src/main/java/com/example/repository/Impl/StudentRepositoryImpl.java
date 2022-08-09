package com.example.repository.Impl;

import com.example.model.Student;
import com.example.repository.StudentRepository;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class StudentRepositoryImpl implements StudentRepository {

    MongoClient client;

    public StudentRepositoryImpl(Vertx vertx) {
        JsonObject config = new JsonObject()
                .put("connection_string", "mongodb://localhost:27017")
                .put("db_name", "local");
        client = MongoClient.createShared(vertx, config);
    }

    @Override
    public Future<Student> findById(int id) {
        Future<Student> future = Future.future();
        JsonObject query = new JsonObject().put("_id", id);
        JsonObject field = new JsonObject();
        client.findOne("myCollection", query, null, result -> {
            if (result.succeeded()) {

                JsonObject result1 = result.result();
                try {
                    Student student = result1.mapTo(Student.class);
                    future.complete(student);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            } else {
                future.fail(result.cause());
            }
        });
        return future;
    }
}
