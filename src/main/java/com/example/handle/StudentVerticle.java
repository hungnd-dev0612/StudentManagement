package com.example.handle;

import com.example.entity.StudentEntity;
import com.example.service.impl.StudentServiceImpl;
import com.example.service.StudentService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class StudentVerticle extends AbstractVerticle {

    StudentService studentService;

    @Override
    public void start(Future<Void> startFuture) {
        studentService = new StudentServiceImpl(vertx);
        Router route = Router.router(vertx);
        route.get("/student").handler(this::handleGetAll);
        route.get("/student/:id").handler(
                this::handleGetStudentById
        );
        route.get("/student/insert").handler(this::handleInsertStudent);
        vertx.createHttpServer().requestHandler(route).listen(8080);
    }


    public void handleGetStudentById(RoutingContext rc) {
//        HttpServerRequest
        int id = Integer.parseInt(rc.request().getParam("id"));
        studentService.findById(id).setHandler(handler -> {
            if (handler.succeeded()) {
                rc.response().end(Json.encodePrettily(handler.result()));
            } else {
                rc.response().end("Loi roi");
                handler.cause().printStackTrace();
            }

        });
    }

    public void handleGetAll(RoutingContext rc) {
        studentService.getAll().setHandler(handler -> {
            if (handler.succeeded()) {
                rc.response().end(Json.encodePrettily(handler.result()));

            } else {
                rc.response().end("Loi get all");
                handler.cause().printStackTrace();
            }
        });
    }

    public void handleInsertStudent(RoutingContext routingContext) {
        StudentEntity student = new StudentEntity();
        studentService
                .insert(student.getId(), student.getName(), student.getAge(), student.getAddress())
                .setHandler(handle -> {
                    if (handle.succeeded()) {
                        routingContext.response()
                                .putHeader("content-type", "application/json; charset=utf-8")
                                .end(Json.encodePrettily(student));
                    }
                });
    }
}
