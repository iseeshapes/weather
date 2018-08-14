package uk.co.iseeshapes.weather.proxy.repository.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import uk.co.iseeshapes.weather.data.WeatherSleuthReading;
import uk.co.iseeshapes.weather.proxy.repository.WeatherReadingIdRepository;
import uk.co.iseeshapes.weather.proxy.repository.WeatherSleuthRepository;

public class DefaultWeatherSleuthRepository implements WeatherSleuthRepository {
    //private static final String collection = "weather_sleuth_observations";

    private MongoTemplate mongoTemplate;
    private String collectionName;
    private WeatherReadingIdRepository weatherReadingIdRepository;

    public DefaultWeatherSleuthRepository (MongoTemplate mongoTemplate,
                                           WeatherReadingIdRepository weatherReadingIdRepository,
                                           String collectionName) {
        this.mongoTemplate = mongoTemplate;
        this.weatherReadingIdRepository = weatherReadingIdRepository;
        this.collectionName = collectionName;
    }

    @Override
    public void add(WeatherSleuthReading weatherSleuthReading) {
        long id = weatherReadingIdRepository.getNextId();
        weatherSleuthReading.setId (id);
        mongoTemplate.insert(weatherSleuthReading, collectionName);
    }
}
