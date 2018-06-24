package com.example.matiz.issdemoproject.sharedPrefs;

import android.content.SharedPreferences;

import com.example.matiz.issdemoproject.repository.models.content.ISSPosition;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.annotations.Nullable;

public class LastISSPositionManager implements LoadSaveManager<ISSPosition>{

    private static final String LAST_POSITION="LAST_POSITION";
    private static final String SAVED_TIME="SAVED_TIME";
    private final SharedPreferences prefs;
    private final Gson gson;

    public LastISSPositionManager(SharedPreferences prefs, Gson gson){
        this.prefs=prefs;
        this.gson=gson;
    }
    @Override
    @Nullable
    public ISSPosition load() {
        return gson.fromJson(prefs.getString(LAST_POSITION,null), ISSPosition.class);
    }

    @Override
    public void save(ISSPosition object) {
        prefs.edit().putString(LAST_POSITION, gson.toJson(object)).commit();
        prefs.edit().putString(SAVED_TIME, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).commit();
    }

    @Override
    public String getSavedDate() {
        return prefs.getString(SAVED_TIME,"");
    }

    public static class NoLastPositionException extends Exception{

    }
}
