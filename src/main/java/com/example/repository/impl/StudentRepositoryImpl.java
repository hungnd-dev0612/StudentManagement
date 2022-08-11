package com.example.repository.impl;

import com.example.entity.StudentEntity;
import com.example.repository.StudentRepository;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.client.WebClient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentRepositoryImpl implements StudentRepository {

    MongoClient client;
    WebClient webClient;

    public StudentRepositoryImpl(Vertx vertx) {
        JsonObject config = new JsonObject()
                .put("connection_string", "mongodb://localhost:27017")
                .put("db_name", "local");
        client = MongoClient.createShared(vertx, config);
        webClient = WebClient.create(vertx);
    }

    @Override
    public Future<StudentEntity> findById(int id) {
        Future<StudentEntity> future = Future.future();
        JsonObject query = new JsonObject().put("_id", id);
        client.findOne("myCollection", query, null, result -> {
            if (result.succeeded()) {
                JsonObject result1 = result.result();

                try {
                    StudentEntity student = result1.mapTo(StudentEntity.class);
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

        client.find("myCollection", query, result -> {
            List<StudentEntity> entity = new ArrayList<>();
            if (result.succeeded()) {
                List<JsonObject> data = result.result();
                entity = data.stream().map(student -> student.mapTo(StudentEntity.class)).collect(Collectors.toList());
                future.complete(entity);

            } else {
                future.fail(result.cause());
            }


        });


        return future;
    }

    @Override
    public Future<String> insert(StudentEntity entity) {
        Future<String> future = Future.future();
        JsonObject query = new JsonObject()
                .put("name", entity.getName())
                .put("birthday", entity.getBirthday())
                .put("studentClass", entity.getStudentClass());
        client.insert("students", query, result -> {
            if (result.succeeded()) {
                future.complete(entity.get_id());
            } else {
                future.fail(result.cause());
            }
        });
        return future;
    }

//    @Override
//    public Future<String> insert(int id, String name, int age, String address) {
//        Future<String> future = Future.future();
//
//        JsonObject query = new JsonObject()
//                .put("_id", id)
//                .put("name", name)
//                .put("age", age)
//                .put("address", address);
//        System.out.println("Student Repo impl: " + query);
//        client.insert("myCollection", query, result -> {
//            if (result.succeeded()) {
//                System.out.println("student id = " + id);
//                int studentId = Integer.parseInt(result.result());
//                future.complete(studentId);
//            } else {
//
//                future.fail(result.cause());
//            }
//        });
//        return future;
//    }

    @Override
    public Future<StudentEntity> update() {
        return null;
    }

    @Override
    public void delete() {

    }
}
