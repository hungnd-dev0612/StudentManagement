package com.example;

import io.vertx.core.Vertx;

import java.sql.Array;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new StudentVerticle());

    }
}
