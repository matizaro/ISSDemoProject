package com.example.matiz.issdemoproject.dagger.subcomponents;

import com.example.matiz.issdemoproject.activities.MainActivity;
import com.example.matiz.issdemoproject.contracts.MainActivityContract;
import com.example.matiz.issdemoproject.dagger.modules.MainActivityModule;
import com.example.matiz.issdemoproject.dagger.scopes.ActivityScope;
import com.example.matiz.issdemoproject.repository.Backend;
import com.example.matiz.issdemoproject.repository.models.content.ISSPosition;
import com.example.matiz.issdemoproject.sharedPrefs.LoadSaveManager;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {MainActivityModule.class})
public interface MainActivitySubcomponent {
    void inject(MainActivity mainActivity);
}
