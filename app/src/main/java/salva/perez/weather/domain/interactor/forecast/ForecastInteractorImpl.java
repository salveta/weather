package salva.perez.weather.domain.interactor.forecast;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import salva.perez.weather.data.forecast.ForecastRepository;
import salva.perez.weather.domain.api.Api;
import salva.perez.weather.domain.interactor.AbstractInteractor;
import salva.perez.weather.domain.model.forecast.City;
import salva.perez.weather.domain.model.weather.CurrentWeather;

public class ForecastInteractorImpl extends AbstractInteractor implements ForecastInteractor {

    private ForecastInteractor.ForecastCallback mCallback;

    public ForecastInteractorImpl(Context context, ForecastInteractor.ForecastCallback callback) {
        super(context);
        this.mCallback = callback;
    }

    @Override
    public void run() {
    }

    public void getForecast(int cityId) {
        mCompositeDisposable.add(
                ForecastRepository.getInstance(mContext)
                        .getService()
                        .getForecast(cityId, Api.UNITS, Api.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            clearComposite();
                            processForecast(response);
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

    private void processForecast(Response<City> response) {
        if (response.isSuccessful()) {
            notifyWeatherDone(response.body());
        } else {
            notifyWeatherError();
        }
    }

    private void notifyWeatherDone(City weather) {
        if (mCallback != null) {
            mCallback.onForecastSuccess(weather);
        }
    }

    private void notifyWeatherError() {
        if (mCallback != null) {
            mCallback.onForecastError();
        }
    }
}
