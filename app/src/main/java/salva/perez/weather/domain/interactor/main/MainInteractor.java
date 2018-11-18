package salva.perez.weather.domain.interactor.main;

public interface MainInteractor {

    interface MainCallback {
        void onWeatherSuccess();
        void onWeatherError();
    }
}
