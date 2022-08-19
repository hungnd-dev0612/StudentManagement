package com.example.routers.handlers;

import com.example.dto.ClassDTO;
import com.example.services.ClassService;
import com.example.services.impl.ClassServiceImpl;
import com.example.utils.SomeContants;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassHandler {
    private final ClassService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassHandler.class);

    public ClassHandler(Vertx vertx) {
        service = new ClassServiceImpl(vertx);
    }

    public void insert(RoutingContext routingContext) {
        routingContext.request().bodyHandler(buffer -> {
            JsonObject json = buffer.toJsonObject();
            ClassDTO dto = json.mapTo(ClassDTO.class);
            service.insert(dto).setHandler(handler -> {
                JsonObject errorResponse = new JsonObject().put("error", "khong tim thay speciality nay");
                JsonObject succeededResponse = new JsonObject().put("succeeded", "them du lieu thanh cong");
                if (handler.succeeded()) {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(succeededResponse));
                } else {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .setStatusCode(400)
                            .end(Json.encodePrettily(errorResponse));
                }
            });
        });
    }

    public void getAll(RoutingContext routingContext) {
        service.getAll().setHandler(handler -> {
            if (handler.succeeded()) {

                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(handler.result()));
            } else {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(handler.cause()));
            }
        });

    }

    public void update(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        routingContext.request().bodyHandler(buffer -> {
            JsonObject json = buffer.toJsonObject();
            ClassDTO dto = json.mapTo(ClassDTO.class);
            service.update(id, dto).setHandler(handler -> {
                if (handler.succeeded()) {
                    routingContext.response().setStatusCode(200)
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(handler.result()));
                } else {
                    routingContext.response().setStatusCode(200)
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(handler.cause()));
                }
            });
        });


    }

    public void delete(RoutingContext routingContext) {

    }

    public void findById(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        JsonObject json = new JsonObject();

        service.findById(id).setHandler(handler -> {
            if (handler.succeeded()) {


                JsonObject json2 = JsonObject.mapFrom(handler.result());
                System.out.println(json2);
                //                LOGGER.info("json {}", json.toString());
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(json2.encodePrettily());
            } else {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(json.toString());
            }
        });
    }

    public void findByName(RoutingContext routingContext) {
        String name = routingContext.request().getParam("name");
        service.findByName(name).setHandler(handler -> {
            if (handler.succeeded()) {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .setStatusCode(200)
                        .end(Json.encodePrettily(handler.result()));
            } else {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .setStatusCode(400)
                        .end(Json.encodePrettily(handler.cause()));
            }
        });
    }
}
