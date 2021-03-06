package salva.perez.weather.domain.interactor.main;

import salva.perez.weather.domain.model.weather.CurrentWeather;

public interface MainInteractor {

    interface MainCallback {
        void onWeatherSuccess(CurrentWeather weather);
        void onWeatherError();
    }
}
