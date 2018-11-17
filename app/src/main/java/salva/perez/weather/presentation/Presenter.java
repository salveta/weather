package salva.perez.weather.presentation;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import salva.perez.weather.R;


public abstract class Presenter<T extends Presenter.View> extends BasePresenter {

    protected Context mContext;
    protected Activity mActivity;
    protected T mView;

    public Presenter(Context context, Activity activity) {
        mContext = context;
        mActivity = activity;
    }

    public T getView() {
        return mView;
    }

    public void setView(T view) {
        this.mView = view;
    }

    public void create() {

    }

    public void resume() {

    }

    public void pause() {

    }

    public void destroy() {

    }

    @Override
    protected void showErrorMessage() {
        super.showErrorMessage();
        Toast.makeText(mContext, mContext.getString(R.string.error_message_generic), Toast.LENGTH_SHORT).show();
    }
}
