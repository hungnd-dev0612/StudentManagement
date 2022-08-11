package com.example.handle;

import com.example.dto.StudentDTO;
import com.example.dto.Test;
import com.example.entity.StudentEntity;
import com.example.service.impl.StudentServiceImpl;
import com.example.service.StudentService;
import com.mongodb.MongoWriteException;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.nio.Buffer;

public class StudentVerticle extends AbstractVerticle {

    StudentService studentService;

    @Override
    public void start(Future<Void> startFuture) {
        studentService = new StudentServiceImpl(vertx);
        Router route = Router.router(vertx);
//        route.get("/student").handler(this::handleGetAll);
//        route.get("/student/:id").handler(this::handleGetStudentById);
//        route.route("/student/insert*").handler(BodyHandler.create());
        route.post("/student/insert").handler(this::handleInsertStudent);
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
                rc.response().putHeader("content-type", "text/plain").end(Json.encodePrettily(handler.result()));

            } else {
                rc.response().end("Loi get all");
                handler.cause().printStackTrace();
            }
        });
    }
    //    public void handleInsertStudent(RoutingContext routingContext) {
//        routingContext.request().bodyHandler(event -> {
//            JsonObject json = event.toJsonObject();
//            StudentEntity student = json.mapTo(StudentEntity.class);
//            JsonObject parseEntity = JsonObject.mapFrom(student);
//            System.out.println("student json mapfrom " + parseEntity);
//            studentService.insert(student.getId(), student.getName(), student.getAge(), student.getAddress()).setHandler(handle -> {
//                if (handle.succeeded()) {
//                    routingContext.response().putHeader("content-type", "application/json; charset=utf-8").end(Json.encodePrettily(student));
//                } else {
//
//                    handle.cause().printStackTrace();
//                    routingContext.response().end(handle.cause().getMessage());
//                }
////
////                try {
////                    routingContext.response().putHeader("content-type", "application/json; charset=utf-8").end(Json.encodePrettily(student));
////                } catch (MongoWriteException ignored) {
////                    System.out.println("catch the error ");
////                    ignored.printStackTrace();
////                }
//
//            });
//        });
//
//    }


    public void handleInsertStudent(RoutingContext routingContext) {
//        try {
//            Test test = routingContext.getBodyAsJson().mapTo(Test.class);
//            routingContext.request().response().putHeader("content-type", "application/json ; charset = utf-8").end(Json.encodePrettily(test));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        try {
            routingContext.request().bodyHandler(bodyHandle -> {
//                sau khi co bodyHandler tu method.post thi no se la mot luong buffer
                JsonObject json = new JsonObject(bodyHandle);
//                luc nay se convert luong buffer nay thanh dto object
                StudentDTO dto = json.mapTo(StudentDTO.class);
//                sau khi co duoc dto object se truyen vao service va convert thanh entity va luu xuong repository
                System.out.println(dto);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
