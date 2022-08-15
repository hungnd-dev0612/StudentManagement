package com.example.routers.handlers;

import com.example.dto.StudentDTO;
import com.example.services.StudentService;
import com.example.services.impl.StudentServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentHandler.class);
    private StudentService service;
    private static final String CONTENT_TYPE = "content-type";
    private static final String CONTENT_VALUE = "application/json ; charset=utf-8";


    public StudentHandler(Vertx vertx) {
        service = new StudentServiceImpl(vertx);
    }

    public void getAll(RoutingContext routingContext) {
        service.getAll().setHandler(ar -> routingContext
                .response()
                .putHeader(CONTENT_TYPE, CONTENT_VALUE)
                .end(Json.encodePrettily(
                        ar.result()
                )));

    }

    public void insert(RoutingContext routingContext) {
        routingContext.request().bodyHandler(handler -> {
            JsonObject json = handler.toJsonObject();
            StudentDTO dto = json.mapTo(StudentDTO.class);
            service.insert(dto).setHandler(ar -> {
                if (ar.succeeded()) {
                    routingContext.response()
                            .putHeader(CONTENT_TYPE, CONTENT_VALUE)
                            .end(Json.encodePrettily(dto));
                    LOGGER.info("insert succeeded {}", ar.result());
                } else {
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
                        .putHeader(CONTENT_TYPE, CONTENT_VALUE)
                        .end(Json.encodePrettily(handler.result()));
            }
        });
    }

    public void update(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        routingContext.request().bodyHandler(bf -> {
            JsonObject json = bf.toJsonObject();
            StudentDTO dto = json.mapTo(StudentDTO.class);
            service.update(id, dto).setHandler(handle -> {
                if (handle.succeeded()) {
                    routingContext.response()
                            .putHeader(CONTENT_TYPE, CONTENT_VALUE)
                            .end(Json.encodePrettily(dto));
                } else {
                    LOGGER.error("error at update");
                    handle.cause();
                }
            });
        });

    }

    public void delete(RoutingContext routingContext) {

    }

}
