package com.example.springboot.bean;

import lombok.Data;


public class Pig {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pig(String name) {
        this.name = name;
    }

    public Pig() {
    }
}
