package com.example.matiz.issdemoproject.repository.models.base;

public class BaseFrame {
    String message;
    long timestamp;

    public BaseFrame() {
    }

    public BaseFrame(String message, int timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
