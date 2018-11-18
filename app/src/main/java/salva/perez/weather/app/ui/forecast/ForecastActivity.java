package salva.perez.weather.app.ui.forecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import salva.perez.weather.R;
import salva.perez.weather.app.ui.base.GenericActivity;
import salva.perez.weather.app.ui.forecast.adapter.ForecastAdapter;
import salva.perez.weather.app.utils.Utils;
import salva.perez.weather.domain.model.forecast.ForecastList;
import salva.perez.weather.presentation.forecast.ForecastPresenter;

public class ForecastActivity extends GenericActivity implements ForecastPresenter.View{

    @BindView(R.id.rv_forecast) RecyclerView rv_forecast;

    private ForecastAdapter mAdapter;
    private List<ForecastList> mForecastList;
    private RecyclerView.LayoutManager mLayoutManager;

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

        initializeRecyclerView();
    }

    public void initializeRecyclerView(){
        mLayoutManager = new LinearLayoutManager(this);
        rv_forecast.setLayoutManager(mLayoutManager);
        mAdapter = new ForecastAdapter(mForecastList, this);
        rv_forecast.setAdapter(mAdapter);
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        rv_forecast.addItemDecoration(headersDecor);
    }

    @Override
    public void initView() {
        ((ForecastPresenter) mPresenter).getWeatherLocation(cityId);
    }

    @Override
    public void showForecast(List<ForecastList> forecastList) {
        mAdapter.updateList(forecastList);
    }

    @Override
    public void onForecastError() {
        Toast.makeText(ForecastActivity.this, getString(R.string.error_message_generic), Toast.LENGTH_SHORT).show();
    }
}
