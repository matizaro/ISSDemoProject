package com.example.matiz.issdemoproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matiz.issdemoproject.App;
import com.example.matiz.issdemoproject.R;
import com.example.matiz.issdemoproject.contracts.MainActivityContract;
import com.example.matiz.issdemoproject.dagger.modules.MainActivityModule;
import com.example.matiz.issdemoproject.mapbox.LatLngEvaluator;
import com.example.matiz.issdemoproject.repository.models.content.Astronaut;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

    @BindView(R.id.iss_map)
    MapView mapView;
    @BindView(R.id.astronauts_tv)
    TextView astronautsTv;
    @BindView(R.id.update_time)
    TextView timeTv;

    private MapboxMap mapBoxMap;
    private MarkerOptions marker;
    private Marker markerView;

    @Inject
    MainActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getApp()
                .getAppComponent()
                .plus(new MainActivityModule(this))
                .inject(this);
        ButterKnife.bind(this);
        initMapBox(savedInstanceState);
    }

    private void init() {
        mapBoxMap.setOnMarkerClickListener(l->{
            clickMarker();
            return true;
        });
        presenter.onCreate();
        presenter.runRefreshingISSStation();
    }

    private void initMapBox(Bundle savedInstanceState) {
        Mapbox.getInstance(this, getString(R.string.mapbox_key));
        marker = new MarkerOptions().setIcon(IconFactory.getInstance(this).fromResource(R.drawable.international_space_station_icon));
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(mapboxMap -> {
            MainActivity.this.mapBoxMap = mapboxMap;
            init();
        });
    }

    private void clickMarker(){
        if(astronautsTv.getVisibility()== View.VISIBLE){
            astronautsTv.setVisibility(View.GONE);
        }else{
            presenter.showAstronautsList();
        }
    }
    @Override
    public void setAstros(List<Astronaut> astronauts) {
        if(astronauts!=null){
            SpannableStringBuilder snippetList = new SpannableStringBuilder();
            snippetList.append(astronauts.size()==1 ? "There is 1 person on the ISS" : "There are "+astronauts.size()+" people on the ISS").append("\n");
            snippetList.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, snippetList.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            for(Astronaut astronaut : astronauts){
                snippetList
                        .append(astronaut.getName())
                        .append("\n");
            }
            astronautsTv.setText(snippetList);
            astronautsTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showAstrosLoadingError() {
        Toast.makeText(this, R.string.refresh_error,Toast.LENGTH_LONG).show();

    }

    @Override
    public void setISSPosition(LatLng issPosition) {
        markerView.setPosition(issPosition);
        mapBoxMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(issPosition).zoom(4.0f).build()), 300);
        mapBoxMap.getMarkerViewManager().update();
    }

    @Override
    public void initMarker(LatLng position) {
        mapBoxMap.clear();
        marker.setPosition(position);
        markerView = mapBoxMap.addMarker(marker);
        mapBoxMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(position).zoom(4.0f).build()), 300);
    }

    @Override
    public void showNoLastPosition() {
        Toast.makeText(this, R.string.refresh_error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRefreshPositionError() {
        Toast.makeText(this, R.string.no_last_position,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setDate(String lastSavedDateString) {
        timeTv.setText(getString(R.string.last_date)+lastSavedDateString);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mapView!=null){
            mapView.onResume();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mapView!=null) {
            mapView.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        astronautsTv.setVisibility(View.GONE);
        if(mapView!=null) {
            mapView.onStop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        astronautsTv.setVisibility(View.GONE);
        if(mapView!=null) {
            mapView.onPause();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mapView!=null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mapView!=null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.stopRefreshing();
        presenter.onDestroy();
        if(mapView!=null) {
            mapView.onDestroy();
        }
    }


}
