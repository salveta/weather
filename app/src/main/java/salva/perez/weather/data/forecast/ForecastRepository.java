package salva.perez.weather.data.forecast;

import android.content.Context;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import salva.perez.weather.domain.api.Api;
import salva.perez.weather.domain.model.weather.CurrentWeather;
import salva.perez.weather.domain.rest.RetrofitAdapter;

public class ForecastRepository {
    private static ForecastRepository sInstance = null;
    private Context mContext = null;
    private ForecastRepository.ForecastRepositoryService service;

    // Temp vars
    private CurrentWeather mWeather;

    public static ForecastRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ForecastRepository();
            sInstance.mContext = context;
        }
        return sInstance;
    }

    private ForecastRepository() {
        Retrofit retrofit = RetrofitAdapter.getInstance(mContext).getAdapter();
        this.service = retrofit.create(ForecastRepository.ForecastRepositoryService.class);
    }


    public ForecastRepository.ForecastRepositoryService getService() {
        return this.service;
    }

    public void setWeather(CurrentWeather weather){
        this.mWeather = weather;
    }

    public CurrentWeather getWeather(){
        return this.mWeather;
    }

    public interface ForecastRepositoryService {
        @GET(Api.ENDPOINT.FORECAST)
        Single<Response<CurrentWeather>> getForecast(@Query("id") int cityId, @Query("units") String units, @Query("appid") String appid);
    }
}
