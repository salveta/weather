package salva.perez.weather.presentation;


public abstract class BasePresenter {

    public interface View {
        void initView();

        void showHideLoadingView(boolean show);
    }

    protected void showErrorMessage() {

    }
}
