package com.example.matiz.issdemoproject.repository.models.content;

import com.example.matiz.issdemoproject.repository.models.base.BaseFrame;
import com.example.matiz.issdemoproject.sharedPrefs.LastISSPositionManager;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class ISSPosition{
    String latitude;
    String longitude;

    public ISSPosition() {
    }

    public ISSPosition(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public LatLng getLatLngPosition() throws NumberFormatException{
        double latitude = Double.valueOf(getLatitude());
        double longitude = Double.valueOf(getLongitude());
        return new LatLng(latitude,longitude);
    }
}
