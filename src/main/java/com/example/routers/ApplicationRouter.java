package com.example.routers;

import com.example.routers.handlers.StudentHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class ApplicationRouter {


    private final StudentHandler studentHandler;
    private Vertx vertx;

    public ApplicationRouter(Vertx vertx, StudentHandler studentHandler) {
        this.studentHandler = studentHandler;
        this.vertx = vertx;
    }

    public Router getRouter() {
        Router route = Router.router(vertx);
//        Student

        route.get("/students").handler(studentHandler::getAll);
//        route.get("/students/:id").handler(studentHandler::findById);
        route.post("/students").handler(studentHandler::insert);
//        route.put("/students/:id").handler(studentHandler::update);
//        route.delete("/students/:id").handler(studentHandler::delete);

//        Speciality
//        route.get("/specialities").handler(specialityHandler::getAll);
//        route.post("/specialities").handler(specialityHandler::insert);
//        route.put("/specialities/:id").handler(specialityHandler::update);
//        route.get("/specialities/:id").handler(specialityHandler::findById);
//        route.delete("/specialities/:id").handler(specialityHandler::delete);
//
////        Class
//        route.get("/classes").handler(classHandler::getAll);
//        route.post("/classes").handler(classHandler::insert);
//        route.put("/classes/:id/:update").handler(classHandler::update);
//        route.get("/classes/:id").handler(classHandler::findById);
//        route.delete("/classes/:id").handler(classHandler::delete);
        return route;
    }

}
