package com.example.demo.data;

public enum Status {
    SUCCESS("Success"),
    ERROR("Error");

    String code;

    Status(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
