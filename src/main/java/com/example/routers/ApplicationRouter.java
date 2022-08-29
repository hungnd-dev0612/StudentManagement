package com.example.routers;

import com.example.routers.handlers.ClassHandler;
import com.example.routers.handlers.SpecialityHandler;
import com.example.routers.handlers.StudentHandler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;


public class ApplicationRouter {


    private final StudentHandler studentHandler;
    private final ClassHandler classHandler;
    private final SpecialityHandler specialityHandler;
    private final Vertx vertx;

    public ApplicationRouter(Vertx vertx, StudentHandler studentHandler, ClassHandler classHandler, SpecialityHandler specialityHandler) {
        this.studentHandler = studentHandler;
        this.classHandler = classHandler;
        this.specialityHandler = specialityHandler;
        this.vertx = vertx;
    }

    public Router getRouter() {
        Router router = Router.router(vertx);

//        router.route().handler(BodyHandler.create());
//        Student
        router.route()
                .handler(CorsHandler.create("*")
                        .allowedMethod(HttpMethod.GET)
                        .allowedMethod(HttpMethod.POST)
                        .allowedMethod(HttpMethod.PUT)
                        .allowedMethod(HttpMethod.DELETE)
                        .allowedMethod(HttpMethod.OPTIONS)
                        .allowedHeader("Access-Control-Request-Method")
                        .allowedHeader("Access-Control-Allow-Credentials")
                        .allowedHeader("Access-Control-Allow-Origin")
                        .allowedHeader("Access-Control-Allow-Headers")
                        .allowedHeader("Content-Type")
                        .allowedHeader("Application/json; charset = utf-8")
                );
        router.get("/students").handler(studentHandler::getAll);
        router.get("/students/:id").handler(studentHandler::findById);
        router.post("/students").handler(studentHandler::insert);
        router.put("/students/:id").handler(studentHandler::update);
        router.delete("/students/:id").handler(studentHandler::delete);

//        Speciality

        router.get("/specialities").handler(specialityHandler::getAll);
        router.post("/specialities").handler(specialityHandler::insert);
        router.put("/specialities/:id").handler(specialityHandler::update);
        router.get("/specialities/:id").handler(specialityHandler::findById);
        router.delete("/specialities/:id").handler(specialityHandler::delete);
//
//        Class
        router.get("/classes").handler(classHandler::getAll);
        router.post("/classes/:specialitiesId").handler(classHandler::insert);
        router.put("/classes/:id/:update").handler(classHandler::update);
        router.get("/classes/:id").handler(classHandler::findById);
        router.delete("/classes/:id").handler(classHandler::delete);


        return router;
    }

}
