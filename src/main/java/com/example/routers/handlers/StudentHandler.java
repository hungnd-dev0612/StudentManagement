package com.example.routers.handlers;

import com.example.services.StudentService;
import com.example.utils.SomeContants;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentHandler.class);

    private final StudentService service;

    public StudentHandler(StudentService service) {
        this.service = service;
    }

    public void insert(RoutingContext routingContext) {

    }

    public void getAll(RoutingContext routingContext) {
        service.getAll().setHandler(ar -> {
            if (ar.succeeded()) {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(ar.result()));
            } else {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(ar.cause()));
            }
        });
    }
}
