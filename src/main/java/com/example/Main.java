package com.example;

import io.vertx.core.Vertx;

import java.sql.Array;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new StudentVerticle());


//
//        int arr[] = {1, 2, 3, 4,};
//        if (arr[i] % 2 == 0) {
////            write some code...
//        }
//
//
//    }
//
//    public Boolean filter(int Input) {
//        return Input % 2 == 0;
//    }
    }
}
