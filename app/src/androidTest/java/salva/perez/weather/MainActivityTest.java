package salva.perez.weather;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import salva.perez.weather.app.ui.main.MainActivity;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class MainActivityTest {

    @Rule
    public final ActivityTestRule mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void display_user_data(){
        onView(withId(R.id.tx_city_weather)).check(matches(isDisplayed()));
        onView(withId(R.id.im_icon_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.tx_max_temperature)).check(matches(isDisplayed()));
    }

    @Test
    public void clickToSeeForecast(){
        onView(withId(R.id.btn_weather_next_days)).perform(ViewActions.click());
    }
}
