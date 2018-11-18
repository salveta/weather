package salva.perez.weather.domain.interactor.main;

import android.content.Context;
import android.location.Location;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import salva.perez.weather.data.WeatherRepository;
import salva.perez.weather.domain.api.Api;
import salva.perez.weather.domain.interactor.AbstractInteractor;
import salva.perez.weather.domain.model.CurrentWeather;

public class MainInteractorImpl extends AbstractInteractor implements MainInteractor {

    private MainInteractor.MainCallback mCallback;

    public MainInteractorImpl(Context context, MainInteractor.MainCallback callback) {
        super(context);
        this.mCallback = callback;
    }

    @Override
    public void run() {
    }

    public void getCurrentWeather(Location location){
        mCompositeDisposable.add(
                WeatherRepository.getInstance(mContext)
                        .getService()
                        .getCurrentWeather(location.getLatitude(), location.getLongitude(), Api.UNITS, Api.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            clearComposite();
                            processCurrentWeahter(response);
                        }));
    }

    @Override
    public void removeCallbacks() {
        mCallback = null;
    }

    @Override
    public void destroy() {
        removeCallbacks();
        clearComposite();
    }

    private void processCurrentWeahter(Response<CurrentWeather> response){
        if (response.isSuccessful()) {
            notifyWeatherDone(response.body());
        } else {
            notifyWeatherError();
        }
    }

    private void notifyWeatherDone(CurrentWeather weather){
        if(mCallback != null){
            mCallback.onWeatherSuccess(weather);
        }
    }

    private void notifyWeatherError(){
        if(mCallback != null){
            mCallback.onWeatherError();
        }
    }
}
