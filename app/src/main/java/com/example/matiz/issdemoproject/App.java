package com.example.matiz.issdemoproject;

import android.app.Application;

import com.example.matiz.issdemoproject.dagger.components.AppComponent;
import com.example.matiz.issdemoproject.dagger.components.DaggerAppComponent;
import com.example.matiz.issdemoproject.dagger.modules.BackendModule;
import com.example.matiz.issdemoproject.dagger.modules.SharedPreferencesModule;

import javax.inject.Inject;

public class App extends Application {

    private AppComponent appComponent;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        this.app=this;
        appComponent = DaggerAppComponent
                .builder()
                .backendModule(new BackendModule())
                .sharedPreferencesModule(new SharedPreferencesModule(this))
                .build();
    }

    public static App getApp(){
        return app;
    }
    public AppComponent getAppComponent() {
        return appComponent;
    }
}


