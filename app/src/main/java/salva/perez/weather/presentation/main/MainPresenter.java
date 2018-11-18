package salva.perez.weather.presentation.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import salva.perez.weather.data.manager.WeatherLocationManager;
import salva.perez.weather.domain.interactor.main.MainInteractor;
import salva.perez.weather.domain.interactor.main.MainInteractorImpl;
import salva.perez.weather.presentation.Presenter;

import static salva.perez.weather.app.Utils.REQUEST_PERMISSIONS.REQUEST_PERMISSION_ACCESS_COARSE_LOCATION;


public class MainPresenter extends Presenter<MainPresenter.View> implements MainInteractor.MainCallback{

    private MainInteractorImpl mInteractor;

    public interface View extends Presenter.View {
        void getUserLocation(Location location);
    }

    public MainPresenter(Context context, Activity activity) {
        super(context, activity);
        this.mInteractor = new MainInteractorImpl(context, this);
    }

    @Override
    public void create() {
        super.create();
        mInteractor.run();
        mView.initView();
        getUserLocationPermission();
    }

    @Override
    public void destroy() {
        super.destroy();
        mInteractor.destroy();
    }

    public void getWeatherLocation(Location location){

    }

    @Override
    public void onWeatherSuccess() {

    }

    @Override
    public void onWeatherError() {

    }

    //PERMISSIONS

    public void getUserLocationPermission(){
        if(userHasGrantedLocation()) {
            WeatherLocationManager locationLoycusManager = new WeatherLocationManager();
            mView.getUserLocation(locationLoycusManager.getLocationUpdates(mActivity));
        }else{
            requestGPSPermission();
        }
    }

    public boolean userHasGrantedLocation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        } else{
            return true;
        }
    }

    public void requestGPSPermission(){
        ActivityCompat.requestPermissions(mActivity, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.INTERNET
        }, REQUEST_PERMISSION_ACCESS_COARSE_LOCATION);
    }

    public void recentPermissionResult(int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getUserLocationPermission();
        }
    }

}
