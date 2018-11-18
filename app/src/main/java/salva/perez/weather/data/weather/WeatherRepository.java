package salva.perez.weather.data.weather;

import android.content.Context;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import salva.perez.weather.domain.api.Api;
import salva.perez.weather.domain.model.weather.CurrentWeather;
import salva.perez.weather.domain.rest.RetrofitAdapter;

public class WeatherRepository {

    private static WeatherRepository sInstance = null;
    private Context mContext = null;
    private WeatherRepositoryService service;

    // Temp vars
    private CurrentWeather mWeather;

    public static WeatherRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new WeatherRepository();
            sInstance.mContext = context;
        }
        return sInstance;
    }


    private WeatherRepository() {
        Retrofit retrofit = RetrofitAdapter.getInstance(mContext).getAdapter();
        this.service = retrofit.create(WeatherRepositoryService.class);
    }


    public WeatherRepositoryService getService() {
        return this.service;
    }

    public void setWeather(CurrentWeather weather){
        this.mWeather = weather;
    }

    public CurrentWeather getWeather(){
        return this.mWeather;
    }

    public interface WeatherRepositoryService {
        @GET(Api.ENDPOINT.CURRENT_WEATHER)
        Single<Response<CurrentWeather>> getCurrentWeather(@Query("lat") double latitude, @Query("lon") double longitude, @Query("units") String units, @Query("appid") String appid);
    }
}
