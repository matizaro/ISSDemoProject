package com.example.matiz.issdemoproject.repository;

import com.example.matiz.issdemoproject.repository.models.api.Astros;
import com.example.matiz.issdemoproject.repository.models.api.ISSNow;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Backend{
    String ISS_API_ADDRESS="http://api.open-notify.org/";

    @GET("iss-now.json")
    Observable<ISSNow> getISSNow();

    @GET("astros.json")
    Observable<Astros> getAstros();

}
