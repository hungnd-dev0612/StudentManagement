package com.example.routers.handlers;

import com.example.dto.StudentDTO;
import com.example.entities.StudentEntity;
import com.example.services.StudentService;
import com.example.services.impl.StudentServiceImpl;
import com.example.utils.SomeContants;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentHandler.class);
    private final StudentService service;

    public StudentHandler(Vertx vertx) {
        service = new StudentServiceImpl(vertx);
    }

    public void getAll(RoutingContext routingContext) {
        service.getAll().setHandler(ar -> routingContext
                .response()
                .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                .end(Json.encodePrettily(
                        ar.result()
                )));

    }

    public void insert(RoutingContext routingContext) {
        String idClass = routingContext.request().getParam("idClass");
        String idSpeciality = routingContext.request().getParam("idSpeciality");

        routingContext.request().bodyHandler(handler -> {
            JsonObject json = handler.toJsonObject();
            StudentDTO dto = json.mapTo(StudentDTO.class);
            LOGGER.info("dto {}", dto);
            service.insert(dto).setHandler(ar -> {
                if (ar.succeeded()) {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(ar.result()));
                    LOGGER.info("insert succeeded {}", ar.result());
                } else {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(ar.result()));
                    LOGGER.error("insert error ", ar.cause());
                }
            });
        });
    }

    public void findById(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        service.findById(id).setHandler(handler -> {
            if (handler.succeeded()) {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(handler.result()));
            }
        });
    }

    public void findByName(RoutingContext routingContext) {
        String name = routingContext.request().getParam("name");

        service.findByName(name).setHandler(handler -> {
            if (handler.succeeded()) {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(handler.result()));
            } else {
                routingContext.response().setStatusCode(400)
                        .end(Json.encodePrettily(new JsonObject()
                                .put("id", "dung co gion")));
            }
        });
    }

    public void update(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400)
                    .end(Json.encodePrettily(new JsonObject()
                            .put("id", "id is not valid")));
        } else {
            routingContext.request().bodyHandler(bf -> {
                JsonObject json = bf.toJsonObject();
                StudentDTO dto = json.mapTo(StudentDTO.class);
                service.update(id, dto).setHandler(handle -> {
                    if (handle.succeeded()) {
                        routingContext.response()
                                .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                                .end(Json.encodePrettily(dto));
                    } else {
                        LOGGER.error("error at update");
                        handle.cause();
                    }
                });
            });
        }


    }

    public void delete(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        service.delete(id).setHandler(handler -> {
            if (handler.succeeded()) {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(handler.result()));
            } else {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(handler.result()));
            }
        });
    }

}
