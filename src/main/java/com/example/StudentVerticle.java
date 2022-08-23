package com.example;

import com.example.pattern.Facade;
import com.example.routers.ApplicationRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StudentVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {
//        ApplicationRouter studentRoute = new ApplicationRouter(vertx);
//        Router router = studentRoute.getRouter(vertx);
//        createHttp(router);
        Facade facade = new Facade(vertx);
        createHttp(facade.getApplicationRouter().getRouter());
    }

    public Future<HttpServer> createHttp(Router route) {
        Future<HttpServer> createServerHandler = Future.future();
        vertx.createHttpServer().requestHandler(route).listen(8080, createServerHandler);
        createServerHandler.setHandler(handle -> {
            if (handle.succeeded()) {
                LOGGER.info("start http succeeded {} ", handle.succeeded());
            } else {
                LOGGER.error("start http fail ", handle.cause());
            }
        });

        return createServerHandler;
    }
}
