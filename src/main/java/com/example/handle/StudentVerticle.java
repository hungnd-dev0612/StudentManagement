package com.example.handle;

import com.example.repository.Impl.StudentRepositoryImpl;
import com.example.service.Impl.StudentServiceImpl;
import com.example.service.StudentService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class StudentVerticle extends AbstractVerticle {

    StudentService studentService;

    @Override
    public void start(Future<Void> startFuture) {
        studentService = new StudentServiceImpl(vertx);
        Router route = Router.router(vertx);
        route.get("/hello").handler(this::handleGetStudent);
        route.get("/hello/:id").handler(
                this::handleGetStudentById
        );
        vertx.createHttpServer().requestHandler(route).listen(8080);
    }

    public void handleGetStudent(RoutingContext rc) {
        studentService.get().setHandler(handler -> {
            rc.response().end(handler.result());
        });
    }

    public void handleGetStudentById(RoutingContext rc) {
        int id = Integer.parseInt(rc.request().getParam("id"));
        studentService.findById(id).setHandler(handler -> {
            if (handler.succeeded()) {
                System.out.println(handler.result());
                rc.response().end(Json.encodePrettily(handler.result()));
            } else {
                rc.response().end("Loi roi");
                handler.cause().printStackTrace();
            }

        });
    }
}
