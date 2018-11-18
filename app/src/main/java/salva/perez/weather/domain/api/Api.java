package salva.perez.weather.domain.api;



public class Api {

    public static final String API_KEY = "1c4880021cf074d35d535f2fb2104d5b";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String UNITS = "metric";
    public static final String ICON_URL = "http://openweathermap.org/img/w/";
    public static final String IMAGE_FORMAT = ".png";

    public interface ENDPOINT {
        String CURRENT_WEATHER = "weather";
        String FORECAST = "forecast";
    }
}
