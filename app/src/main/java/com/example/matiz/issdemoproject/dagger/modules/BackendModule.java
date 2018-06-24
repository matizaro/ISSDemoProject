package com.example.matiz.issdemoproject.dagger.modules;

import com.example.matiz.issdemoproject.repository.Backend;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class BackendModule {

    @Provides
    RxJava2CallAdapterFactory providesRxCallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }
    @Provides
    GsonConverterFactory providesGsonFactory(){
        return GsonConverterFactory.create();
    }
    @Provides
    @Singleton
    public Backend providesBackend(RxJava2CallAdapterFactory rxFactory, GsonConverterFactory gsonFactory){
        return new Retrofit
                .Builder()
                .baseUrl(Backend.ISS_API_ADDRESS)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(rxFactory)
                .build()
                .create(Backend.class);
    }
}
