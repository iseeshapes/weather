package uk.co.iseeshapes.weather.configuration;

import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import uk.co.iseeshapes.weather.converter.WeatherStationReadingConverter;

@Configuration
public class WeatherCommonConfiguration {
    private static final Logger log = LoggerFactory.getLogger(WeatherCommonConfiguration.class);

    private static final String databaseURI = "mongodb://weatherApplication:weatherPassword@nas:27017/weather";
    @Bean
    MongoClientURI mongoClientURI () {
        log.info("Mongo DB connection string: {}", databaseURI);
        return new MongoClientURI(databaseURI);
    }

    @Bean
    MongoDbFactory mongoDbFactory (MongoClientURI mongoClientURI) {
        return new SimpleMongoDbFactory(mongoClientURI);
    }

    @Bean
    MongoTemplate mongoTemplate (MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean
    WeatherStationReadingConverter weatherStationReadingConverter () {
        return new WeatherStationReadingConverter();
    }
}
