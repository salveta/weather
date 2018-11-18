package salva.perez.weather;

import android.support.test.rule.ActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import salva.perez.weather.app.ui.main.MainActivity;
import salva.perez.weather.domain.model.weather.CurrentWeather;
import salva.perez.weather.presentation.main.MainPresenter;

import static org.mockito.Mockito.verify;

public class MainPresenterTest {

    @Mock
    private MainPresenter presenter;

    @Mock
    private MainActivity view;

    @Mock
    private CurrentWeather currentWeather;

    @Rule
    public final ActivityTestRule mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter(view, view);
        presenter.setView(view);
    }

    @Test
    public void sucessDataIsShow(){
        presenter.onWeatherSuccess(currentWeather);
        verify(view).showWeather(currentWeather);
    }
}
