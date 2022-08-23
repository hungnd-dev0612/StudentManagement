package com.example.pattern;

import com.example.connectdb.MongoDBClient;
import com.example.repositories.StudentRepository;
import com.example.repositories.StudentRepositoryImpl;
import com.example.routers.ApplicationRouter;
import com.example.routers.handlers.StudentHandler;
import com.example.services.StudentService;
import com.example.services.impl.StudentServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;

public class Facade {

    //    private Student
    private final MongoClient client;
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final StudentHandler studentHandler;

    private final ApplicationRouter applicationRouter;

    public Facade(Vertx vertx) {
        client = MongoDBClient.client(vertx);
        studentRepository = new StudentRepositoryImpl(client);
        studentService = new StudentServiceImpl(studentRepository);
        studentHandler = new StudentHandler(studentService);
        applicationRouter = new ApplicationRouter(vertx, studentHandler);
    }

    public MongoClient getClient() {
        return client;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public StudentHandler getStudentHandler() {
        return studentHandler;
    }

    public ApplicationRouter getApplicationRouter() {
        return applicationRouter;
    }
}
