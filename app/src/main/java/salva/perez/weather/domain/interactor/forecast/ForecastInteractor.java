package salva.perez.weather.domain.interactor.forecast;


import salva.perez.weather.domain.model.forecast.City;

public interface ForecastInteractor {

    interface ForecastCallback {
        void onForecastSuccess(City weather);
        void onForecastError();
    }
}
