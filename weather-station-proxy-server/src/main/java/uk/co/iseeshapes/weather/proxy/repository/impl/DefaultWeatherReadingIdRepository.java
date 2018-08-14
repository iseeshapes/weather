package uk.co.iseeshapes.weather.proxy.repository.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import uk.co.iseeshapes.weather.proxy.data.WeatherReadingId;
import uk.co.iseeshapes.weather.proxy.repository.WeatherReadingIdRepository;

import java.util.List;

public class DefaultWeatherReadingIdRepository implements WeatherReadingIdRepository {
    private String collectionName;
    private MongoTemplate mongoTemplate;

    public DefaultWeatherReadingIdRepository (MongoTemplate mongoTemplate, String collectionName) {
        this.mongoTemplate = mongoTemplate;
        this.collectionName = collectionName;
    }

    @Override
    public long getNextId() {
        List<WeatherReadingId> weatherReadingIds = mongoTemplate.findAll(WeatherReadingId.class, collectionName);
        WeatherReadingId weatherReadingId;
        if (weatherReadingIds.size() == 0) {
            weatherReadingId = new WeatherReadingId();
        } else {
            weatherReadingId = weatherReadingIds.get(0);
        }
        long id = weatherReadingId.updateId();
        mongoTemplate.save(weatherReadingId, collectionName);
        return id;
    }
}
