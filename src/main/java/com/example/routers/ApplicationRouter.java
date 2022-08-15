package com.example.routers;

import com.example.routers.handlers.StudentHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class ApplicationRouter {

    private final StudentHandler studentHandler;

    public ApplicationRouter(Vertx vertx) {
        studentHandler = new StudentHandler(vertx);
    }

    public Router getRouter(Vertx vertx) {
        Router route = Router.router(vertx);
        route.get("/students").handler(studentHandler::getAll);
        route.get("/students/findById/:id").handler(studentHandler::findById);
        route.post("/student/insert").handler(studentHandler::insert);
        route.put("/student/update/:id").handler(studentHandler::update);
        route.delete("/student/delete/:id").handler(studentHandler::delete);
        return route;
    }

}
