package com.example.matiz.issdemoproject.sharedPrefs;

import android.content.SharedPreferences;

public interface LoadSaveManager<T> {
    T load();
    void save(T object);
    String getSavedDate();
}
