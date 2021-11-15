package com.example;

import lombok.Data;

import java.util.UUID;

@Data
public class Key {
    private String key = UUID.randomUUID().toString();
    public String getKey() {
        return key;
    }
}
