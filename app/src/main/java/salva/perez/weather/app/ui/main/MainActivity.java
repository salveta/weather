package salva.perez.weather.app.ui.main;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import butterknife.ButterKnife;
import salva.perez.weather.R;
import salva.perez.weather.app.ui.base.GenericActivity;
import salva.perez.weather.domain.model.CurrentWeather;
import salva.perez.weather.domain.model.Main;
import salva.perez.weather.presentation.main.MainPresenter;

import static salva.perez.weather.app.utils.Utils.REQUEST_PERMISSIONS.REQUEST_PERMISSION_ACCESS_COARSE_LOCATION;

public class MainActivity extends GenericActivity implements MainPresenter.View {

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
}
