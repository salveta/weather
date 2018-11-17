package salva.perez.weather.presentation;

/**
 * Created by salva perez on 16/11/18.
 */

public abstract class BasePresenter {

    public interface View {
        void initView();

        void showHideLoadingView(boolean show);
    }

    protected void showErrorMessage() {

    }
}
