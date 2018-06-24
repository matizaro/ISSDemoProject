package com.example.matiz.issdemoproject.dagger.modules;

import android.widget.Toast;

import com.example.matiz.issdemoproject.R;
import com.example.matiz.issdemoproject.contracts.MainActivityContract;
import com.example.matiz.issdemoproject.dagger.scopes.ActivityScope;
import com.example.matiz.issdemoproject.repository.Backend;
import com.example.matiz.issdemoproject.repository.models.api.Astros;
import com.example.matiz.issdemoproject.repository.models.api.ISSNow;
import com.example.matiz.issdemoproject.repository.models.content.Astronaut;
import com.example.matiz.issdemoproject.repository.models.content.ISSPosition;
import com.example.matiz.issdemoproject.repository.models.validators.Validate;
import com.example.matiz.issdemoproject.sharedPrefs.LastISSPositionManager;
import com.example.matiz.issdemoproject.sharedPrefs.LoadSaveManager;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@Module
public class MainActivityModule {

    private MainActivityContract.View view;
    private long REFRESH_INTERVAL = 5;

    public MainActivityModule(MainActivityContract.View view) {
        this.view=view;
    }

    @Provides
    @ActivityScope
    MainActivityContract.Model providesMainActivityModel(Backend backend, LoadSaveManager<ISSPosition> issPositionManager, Validate<ISSPosition> validator){
        return new MainActivityContract.Model() {
            CompositeDisposable disposable = new CompositeDisposable();
            @Override
            public void requestISSNow(Consumer<ISSNow> onSuccess, Consumer<Throwable> onError) {
                disposable.add(backend.getISSNow()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(issNow->{
                            issPositionManager.save(issNow.getIssPosition());
                            onSuccess.accept(issNow);
                        }, onError::accept));
            }

            @Override
            public void requestAstronauts(Consumer<Astros> onSuccess, Consumer<Throwable> onError) {
                disposable.add(backend.getAstros()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onSuccess::accept, onError::accept));
            }

            @Override
            public LatLng getLastSavedPosition() throws LastISSPositionManager.NoLastPositionException {
                ISSPosition position = issPositionManager.load();
                if(!validator.isValid(position)){
                    throw new LastISSPositionManager.NoLastPositionException();
                }
                try{
                    return position.getLatLngPosition();
                }catch(NumberFormatException exception){
                    throw new LastISSPositionManager.NoLastPositionException();
                }
            }

            @Override
            public String getLastSavedDateString() {
                return issPositionManager.getSavedDate();
            }

            @Override
            public void dispose() {
                disposable.dispose();
            }
        };
    }

    @Provides
    @ActivityScope
    MainActivityContract.Presenter providesMainActivityPresenter(MainActivityContract.Model model){
        return new MainActivityContract.Presenter() {
            Disposable issSubscription;
            @Override
            public void onCreate() {
                try {
                    view.initMarker(model.getLastSavedPosition());
                    view.setDate(model.getLastSavedDateString());
                } catch (LastISSPositionManager.NoLastPositionException e) {
                    view.initMarker(new LatLng(0.0f, 0.0f));
                }
            }

            @Override
            public void onDestroy() {
                model.dispose();
            }

            @Override
            public void showAstronautsList() {
                model.requestAstronauts(astros -> {
                    view.setAstros(astros.getAstronauts());
                }, throwable -> view.showAstrosLoadingError());
            }

            @Override
            public void runRefreshingISSStation() {
                issSubscription = Observable.interval(REFRESH_INTERVAL, TimeUnit.SECONDS)
                        .subscribe(ignored->{
                            model.requestISSNow(issNow -> {
                                view.setISSPosition(issNow.getIssPosition().getLatLngPosition());
                                view.setDate(model.getLastSavedDateString());
                            }, throwable->{
                                view.showRefreshPositionError();
                            });
                        });
            }

            @Override
            public void stopRefreshing() {
                if(issSubscription!=null && ! issSubscription.isDisposed()){
                    issSubscription.dispose();
                }
            }

        };
    }
}
