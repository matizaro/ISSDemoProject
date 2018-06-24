package com.example.matiz.issdemoproject.contracts;

import com.example.matiz.issdemoproject.repository.models.api.Astros;
import com.example.matiz.issdemoproject.repository.models.api.ISSNow;
import com.example.matiz.issdemoproject.repository.models.content.Astronaut;
import com.example.matiz.issdemoproject.sharedPrefs.LastISSPositionManager;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.List;
import java.util.function.Consumer;

public class MainActivityContract {
    public interface Model{
        void requestISSNow(Consumer<ISSNow> onSuccess, Consumer<Throwable> onError);
        void requestAstronauts(Consumer<Astros> onSuccess, Consumer<Throwable> onError);
        LatLng getLastSavedPosition() throws LastISSPositionManager.NoLastPositionException;
        String getLastSavedDateString();
        void dispose();
    }
    public interface View{
        void setAstros(List<Astronaut> astronauts);
        void showAstrosLoadingError();
        void setISSPosition(LatLng issPosition);
        void initMarker(LatLng position);
        void showNoLastPosition();
        void showRefreshPositionError();
        void setDate(String lastSavedDateString);
    }
    public interface Presenter{
        void onCreate();
        void onDestroy();
        void showAstronautsList();
        void runRefreshingISSStation();
        void stopRefreshing();
    }
}
