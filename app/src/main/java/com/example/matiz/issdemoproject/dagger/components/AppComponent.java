package com.example.matiz.issdemoproject.dagger.components;

import android.app.Application;

import com.example.matiz.issdemoproject.activities.MainActivity;
import com.example.matiz.issdemoproject.dagger.modules.BackendModule;
import com.example.matiz.issdemoproject.dagger.modules.MainActivityModule;
import com.example.matiz.issdemoproject.dagger.modules.SharedPreferencesModule;
import com.example.matiz.issdemoproject.dagger.scopes.ActivityScope;
import com.example.matiz.issdemoproject.dagger.subcomponents.MainActivitySubcomponent;
import com.example.matiz.issdemoproject.repository.Backend;
import com.example.matiz.issdemoproject.repository.models.content.ISSPosition;
import com.example.matiz.issdemoproject.repository.models.validators.Validate;
import com.example.matiz.issdemoproject.sharedPrefs.LoadSaveManager;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {BackendModule.class, SharedPreferencesModule.class})
public interface AppComponent {
    MainActivitySubcomponent plus(MainActivityModule module);
}
