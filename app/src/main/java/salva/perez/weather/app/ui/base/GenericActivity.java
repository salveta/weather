package salva.perez.weather.app.ui.base;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import salva.perez.weather.R;
import salva.perez.weather.presentation.Presenter;


public abstract class GenericActivity extends AppCompatActivity implements View.OnClickListener, Presenter.View {

    // Binder
    protected Unbinder mUnbinder;

    // Default Presenter
    protected Presenter mPresenter;

    @Nullable
    @BindView(R.id.generic_loading_view)
    protected View mGenericLoadingView;

    @Nullable
    @BindView(android.R.id.content)
    View mRootLayout;

    @Nullable
    @BindView(R.id.main_layout)
    View mSnackBarView;

    private Snackbar mSnackbar;
    private CompositeDisposable compositeDisposable;
    protected boolean isNetworkAvailable = true;


    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (mRootLayout != null) {
            mRootLayout.setOnClickListener(this);
        }

        checkNetworkConnectivity();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mRootLayout != null) {
            mRootLayout.setOnClickListener(null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }

        if (mPresenter != null) {
            mPresenter.destroy();
        }

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == android.R.id.content) {
        }
    }

    protected void initializePresenter(Presenter.View view) {
        mPresenter.setView(view);
    }

    @Override
    public void showHideLoadingView(boolean show) {
        if (mGenericLoadingView != null) {
            mGenericLoadingView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void checkNetworkConnectivity() {
        compositeDisposable.add(ReactiveNetwork.observeNetworkConnectivity(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity ->
                        checkStatusNetwork(connectivity.getState())));

    }

    public void checkStatusNetwork(NetworkInfo.State connectivity) {
        if (NetworkInfo.State.CONNECTED.equals(connectivity)) {
            removeSnackBar();
        } else if (NetworkInfo.State.DISCONNECTED.equals(connectivity)) {
            snackBarMessage(getResources().getString(R.string.no_network));
        } else if (NetworkInfo.State.UNKNOWN.equals(connectivity)) {
            snackBarMessage(getResources().getString(R.string.network_unknow));
        }
    }

    public void snackBarMessage(String message) {
        if (mSnackBarView != null) {
            mSnackbar = Snackbar.make(mSnackBarView, message, Snackbar.LENGTH_INDEFINITE);
            mSnackbar.show();
            isNetworkAvailable = false;
        }
    }

    public void removeSnackBar() {
        if (mSnackBarView != null && mSnackbar != null) {
            mSnackbar.dismiss();
            isNetworkAvailable = true;
        }
    }
}
