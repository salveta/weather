package salva.perez.weather.domain.interactor.main;

import android.content.Context;
import salva.perez.weather.domain.interactor.AbstractInteractor;


public class MainInteractorImpl extends AbstractInteractor implements MainInteractor {

    private static String TAG = "LoginInteractorImpl";

    private static MainInteractorImpl sInstance;
    private MainInteractor.MainCallback mCallback;


    public MainInteractorImpl(Context context, MainInteractor.MainCallback callback) {
        super(context);
        this.mCallback = callback;
    }

    @Override
    public void run() {
        getCurrentWeather();
    }

    private void getCurrentWeather(){}

    @Override
    public void removeCallbacks() {
        mCallback = null;
    }

    @Override
    public void destroy() {
        removeCallbacks();
        clearComposite();
    }

}
