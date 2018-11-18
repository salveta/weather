package salva.perez.weather.app.ui.main;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import salva.perez.weather.R;
import salva.perez.weather.app.navigator.Navigator;
import salva.perez.weather.app.ui.base.GenericActivity;
import salva.perez.weather.data.weather.WeatherRepository;
import salva.perez.weather.domain.api.Api;
import salva.perez.weather.domain.model.weather.CurrentWeather;
import salva.perez.weather.presentation.main.MainPresenter;

import static salva.perez.weather.app.utils.Utils.REQUEST_PERMISSIONS.REQUEST_PERMISSION_ACCESS_COARSE_LOCATION;

public class MainActivity extends GenericActivity implements MainPresenter.View {

    @BindView(R.id.tx_city_weather)         TextView tx_city_weather;
    @BindView(R.id.im_icon_detail)          ImageView im_icon_detail;
    @BindView(R.id.tx_max_temperature)      TextView tx_max_temperature;
    @BindView(R.id.btn_weather_next_days)   Button btn_weather_next_days;

    public static final int WEATHER_CONDITION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);

        mPresenter = new MainPresenter(this, this);
        initializePresenter(this);
        mPresenter.create();
    }

    @Override
    public void initView() {}

    @Override
    public void getUserLocation(Location location) {
        ((MainPresenter) mPresenter).getWeatherLocation(location);
    }

    @Override
    public void showWeather(CurrentWeather currentWeather) {
        tx_city_weather.setText(getString(R.string.current_day, currentWeather.getName()));
        tx_max_temperature.setText(String.valueOf(currentWeather.getMain().getTempMax()));

        Picasso.with(MainActivity.this)
                .load(Api.ICON_URL + currentWeather.getWeather().get(WEATHER_CONDITION_ID).getIcon() + Api.IMAGE_FORMAT)
                .into(im_icon_detail);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_ACCESS_COARSE_LOCATION: {
                ((MainPresenter) mPresenter).recentPermissionResult(grantResults);
            }
        }
    }

    @OnClick(R.id.btn_weather_next_days)
    public void onClickNextDays(){
        Navigator.MAIN.openForecast(MainActivity.this, WeatherRepository.getInstance(MainActivity.this).getWeather().getId());
    }
}
