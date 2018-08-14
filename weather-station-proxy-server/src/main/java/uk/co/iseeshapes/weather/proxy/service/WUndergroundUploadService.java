package uk.co.iseeshapes.weather.proxy.service;

import uk.co.iseeshapes.weather.data.WeatherStationReading;

public interface WUndergroundUploadService {
    void uploadReading (WeatherStationReading weatherStationReading);
}
