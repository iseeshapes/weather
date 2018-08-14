package uk.co.iseeshapes.weather.proxy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import uk.co.iseeshapes.weather.configuration.WeatherCommonConfiguration;
import uk.co.iseeshapes.weather.converter.WeatherStationReadingConverter;
import uk.co.iseeshapes.weather.proxy.repository.WeatherReadingIdRepository;
import uk.co.iseeshapes.weather.proxy.repository.WeatherSleuthRepository;
import uk.co.iseeshapes.weather.proxy.repository.impl.DefaultWeatherReadingIdRepository;
import uk.co.iseeshapes.weather.proxy.repository.impl.DefaultWeatherSleuthRepository;
import uk.co.iseeshapes.weather.proxy.service.WUndergroundUploadService;
import uk.co.iseeshapes.weather.proxy.service.impl.DefaultWUndergroundUploadService;

@Configuration
@Import({WeatherCommonConfiguration.class})
public class ProxyApplicationConfiguration {
    private static final String wUndergroundUsername = "ICALNE10";
    private static final String wUndergroundPassword = "mkogwyrl";
    private static final String weatherSleuthCollection = "weather_sleuth_observations";
    private static final String weatherObservationIdCollection = "weather_observation_ids";

    @Bean
    WeatherSleuthRepository weatherSleuthRepository (MongoTemplate mongoTemplate,
                                                     WeatherReadingIdRepository weatherReadingIdRepository) {
        return new DefaultWeatherSleuthRepository (mongoTemplate, weatherReadingIdRepository, weatherSleuthCollection);
    }

    @Bean
    WUndergroundUploadService wUndergroundUploadService (WeatherStationReadingConverter weatherStationReadingConverter) {
        return new DefaultWUndergroundUploadService (weatherStationReadingConverter, wUndergroundUsername,
                wUndergroundPassword);
    }

    @Bean
    WeatherReadingIdRepository weatherReadingIdRepository (MongoTemplate mongoTemplate) {
        return new DefaultWeatherReadingIdRepository(mongoTemplate, weatherObservationIdCollection);
    }
}
