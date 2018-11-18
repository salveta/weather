package salva.perez.weather.app.navigator;


import android.app.Activity;
import android.content.Intent;

import salva.perez.weather.app.ui.forecast.ForecastActivity;
import salva.perez.weather.app.utils.Utils;

public class Navigator {

    public interface MAIN {
        static void openForecast(Activity mActivity, int cityId){
            Intent intent = new Intent(mActivity, ForecastActivity.class);
            intent.putExtra(Utils.ARGUMENTS.CITY_ID, cityId);
            mActivity.startActivity(intent);
        }

    }
}
