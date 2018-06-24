package com.example.matiz.issdemoproject.repository.models.content;

public class Astronaut {
    String craft;
    String name;

    public Astronaut() {
    }

    public Astronaut(String craft, String name) {
        this.craft = craft;
        this.name = name;
    }

    public String getCraft() {
        return craft;
    }

    public String getName() {
        return name;
    }
}
