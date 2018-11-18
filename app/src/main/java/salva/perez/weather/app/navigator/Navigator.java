package salva.perez.weather.app.navigator;


import android.content.Context;
import android.content.Intent;

import salva.perez.weather.app.ui.forecast.ForecastActivity;
import salva.perez.weather.app.utils.Utils;

public class Navigator {

    public interface MAIN {
        static void openForecast(Context mContext, int cityId){
            Intent intent = new Intent(mContext, ForecastActivity.class);
            intent.putExtra(Utils.ARGUMENTS.CITY_ID, cityId);
            mContext.startActivity(intent);
        }

    }
}
