package salva.perez.weather.domain.interactor.forecast;

import java.util.List;

import salva.perez.weather.domain.model.weather.CurrentWeather;

public interface ForecastInteractor {

    interface ForecastCallback {
        void onForecastSuccess(CurrentWeather weather);
        void onForecastError();
    }
}
