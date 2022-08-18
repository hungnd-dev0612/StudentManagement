package com.example.routers.handlers;

import com.example.dto.SpecialityDTO;
import com.example.services.SpecialityService;
import com.example.services.impl.SpecialityServiceImpl;
import com.example.utils.SomeContants;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpecialityHandler {
    private final SpecialityService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialityHandler.class);

    public SpecialityHandler(Vertx vertx) {
        service = new SpecialityServiceImpl(vertx);
    }

    public void insert(RoutingContext routingContext) {
        routingContext.request().bodyHandler(handler -> {
            JsonObject obj = handler.toJsonObject();
            SpecialityDTO dto = obj.mapTo(SpecialityDTO.class);
            service.insert(dto).setHandler(ar -> {
                if (ar.succeeded()) {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(ar.result()));
                    LOGGER.info("insert succeeded {}", ar.result());
                } else {

                    LOGGER.error("insert error ", ar.cause());
                }
            });
        });

    }

    public void getAll(RoutingContext routingContext) {
        service.getAll().setHandler(handler -> routingContext.response()
                .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                .setStatusCode(200)
                .end(Json.encodePrettily(handler.result())));
    }

    public void update(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        LOGGER.info(id);
        routingContext.request().bodyHandler(buffer -> {
            JsonObject parseBuffer = buffer.toJsonObject();
            SpecialityDTO dto = parseBuffer.mapTo(SpecialityDTO.class);
            service.update(id, dto).setHandler(handler -> {
                if (handler.succeeded()) {
                    LOGGER.info("hanler {},", handler.result());
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(handler.result()));
                } else {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(handler.cause()));
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
            } else {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(handler.cause()));
            }
        });
    }

    public void delete(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        Future future = Future.future();
        service.delete(id).setHandler(handler -> {
            if(handler.succeeded()){
                routingContext.response().setStatusCode(200).putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE).end();
            }else{
                routingContext.response().setStatusCode(400).putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE).end();
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
                        .setStatusCode(500)
                        .end(Json.encodePrettily(handler.cause()));
            }
        });
    }
}
