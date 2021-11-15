package com.example;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Event {
    private String id;

    public Event(String id, String time, int humidity, int temperature) {
        this.id = id;
        this.time = time;
        this.humidity = humidity;
        this.temperature = temperature;
    }

    private String time;
    private int humidity;
    private int temperature;
}
