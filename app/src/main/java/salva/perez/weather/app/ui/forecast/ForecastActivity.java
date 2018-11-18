package salva.perez.weather.app.ui.forecast;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import salva.perez.weather.R;
import salva.perez.weather.app.ui.base.GenericActivity;
import salva.perez.weather.app.utils.Utils;
import salva.perez.weather.presentation.forecast.ForecastPresenter;

public class ForecastActivity extends GenericActivity implements ForecastPresenter.View{

    private Intent intent;
    private int cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_activity);
        mUnbinder = ButterKnife.bind(this);

        intent = getIntent();

        if(intent != null){
            cityId = intent.getIntExtra(Utils.ARGUMENTS.CITY_ID, 0);
        }

        mPresenter = new ForecastPresenter(this, this);
        initializePresenter(this);
        mPresenter.create();
    }

    @Override
    public void initView() {
        ((ForecastPresenter) mPresenter).getWeatherLocation(cityId);
    }
}
