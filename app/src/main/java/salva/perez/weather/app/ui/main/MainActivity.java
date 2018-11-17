package salva.perez.weather.app.ui.main;

import android.os.Bundle;

import butterknife.ButterKnife;
import salva.perez.weather.R;
import salva.perez.weather.app.ui.base.GenericActivity;
import salva.perez.weather.presentation.main.MainPresenter;

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
    public void initView() {
    }
}
