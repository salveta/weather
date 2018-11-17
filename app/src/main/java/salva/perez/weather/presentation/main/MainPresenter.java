package salva.perez.weather.presentation.main;

import android.app.Activity;
import android.content.Context;
import salva.perez.weather.presentation.Presenter;


public class MainPresenter extends Presenter<MainPresenter.View> {


    public interface View extends Presenter.View {
    }

    public MainPresenter(Context context, Activity activity) {
        super(context, activity);
    }

    @Override
    public void create() {
        super.create();
        mView.initView();
    }

}
