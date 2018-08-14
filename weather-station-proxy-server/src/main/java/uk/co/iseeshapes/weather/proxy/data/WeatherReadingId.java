package uk.co.iseeshapes.weather.proxy.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class WeatherReadingId {
    @Id
    private long id;

    private long lastWeatherReadingId;

    public long getId() {
        return id;
    }

    public long getLastWeatherReadingId() {
        return lastWeatherReadingId;
    }

    public void setLastWeatherReadingId(long lastWeatherReadingId) {
        this.lastWeatherReadingId = lastWeatherReadingId;
    }

    public long updateId () {
        return ++lastWeatherReadingId;
    }
}
