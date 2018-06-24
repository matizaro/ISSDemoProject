package com.example.matiz.issdemoproject.repository.models.api;

import com.example.matiz.issdemoproject.repository.models.base.BaseFrame;
import com.example.matiz.issdemoproject.repository.models.content.Astronaut;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Astros extends BaseFrame{
    @SerializedName("people")
    List<Astronaut> astronauts;

    public Astros() {
    }

    public Astros(String message, int timestamp, List<Astronaut> astronauts) {
        super(message, timestamp);
        this.astronauts = astronauts;
    }

    public List<Astronaut> getAstronauts() {
        return astronauts;
    }
}
