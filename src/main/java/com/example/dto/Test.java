package com.example.dto;

import lombok.Data;

@Data
public class Test {
    private String name;
    private String address;

    public Test() {

    }

    public Test(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
