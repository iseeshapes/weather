package uk.co.iseeshapes.weather.display.respository;

import uk.co.iseeshapes.weather.display.data.History;
import uk.co.iseeshapes.weather.display.data.SimpleWeatherData;

import java.util.Date;

public interface WeatherRepository {
    SimpleWeatherData getCurrentWeather () throws NoRecordException;

    History<Double> getDailyTemperatureHighs ();
}
