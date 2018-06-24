package com.example.matiz.issdemoproject.repository.models.api;

import com.example.matiz.issdemoproject.repository.models.base.BaseFrame;
import com.example.matiz.issdemoproject.repository.models.content.ISSPosition;
import com.google.gson.annotations.SerializedName;

public class ISSNow extends BaseFrame {
    @SerializedName("iss_position")
    ISSPosition issPosition;

    public ISSNow() {
    }
    public ISSNow(String message, int timestamp, ISSPosition issPosition) {
        super(message, timestamp);
        this.issPosition = issPosition;
    }

    public ISSPosition getIssPosition() {
        return issPosition;
    }
}
