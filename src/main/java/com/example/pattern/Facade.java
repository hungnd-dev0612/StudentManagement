package com.example.pattern;

import com.example.connectdb.MongoDBClient;
import com.example.repositories.ClassRepository;
import com.example.repositories.Impl.ClassRepositoryImpl;
import com.example.repositories.Impl.SpecialityRepositoryImpl;
import com.example.repositories.SpecialityRepository;
import com.example.repositories.StudentRepository;
import com.example.repositories.Impl.StudentRepositoryImpl;
import com.example.routers.ApplicationRouter;
import com.example.routers.handlers.ClassHandler;
import com.example.routers.handlers.SpecialityHandler;
import com.example.routers.handlers.StudentHandler;
import com.example.services.ClassService;
import com.example.services.SpecialityService;
import com.example.services.StudentService;
import com.example.services.impl.ClassServiceImpl;
import com.example.services.impl.SpecialityServiceImpl;
import com.example.services.impl.StudentServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.ext.mongo.MongoClient;

public class Facade {

    //    private Student
    private final MongoClient client;
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final StudentHandler studentHandler;
    private final ClassHandler classHandler;
    private final ClassRepository classRepository;
    private final ClassService classService;

    private final SpecialityHandler specialityHandler;
    private final SpecialityRepository specialityRepository;
    private final SpecialityService specialityService;
    private final ApplicationRouter applicationRouter;

    public Facade(Vertx vertx) {
        client = MongoDBClient.client(vertx);
//        create new speciality here
        specialityRepository = new SpecialityRepositoryImpl(client);
        specialityService = new SpecialityServiceImpl(specialityRepository);
        specialityHandler = new SpecialityHandler(specialityService);
//        create new class here
        classRepository = new ClassRepositoryImpl(client);
        classService = new ClassServiceImpl(classRepository, specialityRepository);
        classHandler = new ClassHandler(classService);
//        create new student here
        studentRepository = new StudentRepositoryImpl(client);
        studentService = new StudentServiceImpl(studentRepository);
        studentHandler = new StudentHandler(studentService);


        applicationRouter = new ApplicationRouter(vertx, studentHandler, classHandler, specialityHandler);
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
