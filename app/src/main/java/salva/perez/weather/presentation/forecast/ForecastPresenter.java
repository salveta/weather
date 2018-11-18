package salva.perez.weather.presentation.forecast;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import salva.perez.weather.domain.interactor.forecast.ForecastInteractor;
import salva.perez.weather.domain.interactor.forecast.ForecastInteractorImpl;
import salva.perez.weather.domain.model.forecast.City;
import salva.perez.weather.domain.model.forecast.ForecastList;
import salva.perez.weather.domain.model.weather.CurrentWeather;
import salva.perez.weather.presentation.Presenter;


public class ForecastPresenter extends Presenter<ForecastPresenter.View> implements ForecastInteractor.ForecastCallback{

    private ForecastInteractorImpl mInteractor;

    public interface View extends Presenter.View {
        void showForecast(List<ForecastList> forecastList);
    }

    public ForecastPresenter(Context context, Activity activity) {
        super(context, activity);
        this.mInteractor = new ForecastInteractorImpl(context, this);
    }

    @Override
    public void create() {
        super.create();
        mInteractor.run();
        mView.initView();
        mView.showHideLoadingView(true);
    }

    @Override
    public void destroy() {
        super.destroy();
        mInteractor.destroy();
    }

    public void getWeatherLocation(int cityId){
        if(cityId > 0) {
            mInteractor.getForecast(cityId);
        }
    }

    @Override
    public void onForecastSuccess(City weather) {
        mView.showHideLoadingView(false);
        mView.showForecast(weather.getList());
    }

    @Override
    public void onForecastError() {
        mView.showHideLoadingView(false);
    }
}

