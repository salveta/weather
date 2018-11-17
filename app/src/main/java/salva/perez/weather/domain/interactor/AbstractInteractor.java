package salva.perez.weather.domain.interactor;


import android.content.Context;
import io.reactivex.disposables.CompositeDisposable;



public abstract class AbstractInteractor implements Interactor {

    protected static Context mContext;
    protected static CompositeDisposable mCompositeDisposable;

    public AbstractInteractor(Context context) {
        mContext = context;
        mCompositeDisposable = new CompositeDisposable();
    }

    protected static void clearComposite() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
