package com.example.matiz.issdemoproject.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.matiz.issdemoproject.App;
import com.example.matiz.issdemoproject.dagger.scopes.ActivityScope;
import com.example.matiz.issdemoproject.repository.models.content.ISSPosition;
import com.example.matiz.issdemoproject.repository.models.validators.ISSPositionValidator;
import com.example.matiz.issdemoproject.repository.models.validators.Validate;
import com.example.matiz.issdemoproject.sharedPrefs.LastISSPositionManager;
import com.example.matiz.issdemoproject.sharedPrefs.LoadSaveManager;
import com.google.gson.Gson;
import com.mapbox.mapboxsdk.geometry.LatLng;

import javax.inject.Singleton;

import dagger.BindsOptionalOf;
import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {
    private static final String SHARED_PREF_NAME="ISS_PREFS";
    private static final String LAST_POSITION="LAST_POSITION";
    private Application application;

    public SharedPreferencesModule(Application  application){
        this.application=application;
    }
    @Provides
    @Singleton
    SharedPreferences providesSharedPrefs(){
        return application.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
    }
    @Provides
    @Singleton
    Gson providesGson(){
        return new Gson();
    }
    @Provides
    @Singleton
    LoadSaveManager<ISSPosition> providesOptionalLastPosition(SharedPreferences prefs, Gson gson){
        return new LastISSPositionManager(prefs,gson);
    }
    @Provides
    @Singleton
    Validate<ISSPosition> providesISSPositionValidator(){
        return new ISSPositionValidator();
    }
}
