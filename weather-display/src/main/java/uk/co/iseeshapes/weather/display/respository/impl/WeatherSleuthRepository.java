package uk.co.iseeshapes.weather.display.respository.impl;

import com.mongodb.MongoClientURI;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import uk.co.iseeshapes.weather.data.WeatherSleuthReading;
import uk.co.iseeshapes.weather.display.data.History;
import uk.co.iseeshapes.weather.display.data.SimpleWeatherData;
import uk.co.iseeshapes.weather.display.respository.NoRecordException;
import uk.co.iseeshapes.weather.display.respository.WeatherRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeatherSleuthRepository implements WeatherRepository {
    private static final Logger log = LoggerFactory.getLogger(WeatherRepository.class);

    private MongoTemplate mongoTemplate;

    public WeatherSleuthRepository() {
        String databaseURI = "mongodb://weatherApplication:weatherPassword@nas:27017/weather";
        log.info("Mongo DB connection string: {}", databaseURI);

        MongoClientURI mongoClientURI = new MongoClientURI(databaseURI);
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClientURI);
        mongoTemplate =  new MongoTemplate(mongoDbFactory);
    }

    @Override
    public SimpleWeatherData getCurrentWeather () throws NoRecordException {
        Criteria criteria = new Criteria();
        criteria.all();
        Query query = new Query();
        query.limit(1)
                .with(Sort.by(Sort.Order.desc("observationTimeUtc")));
        List<WeatherSleuthReading> weatherSleuthReadings = mongoTemplate.find(query, WeatherSleuthReading.class,
                "weather_sleuth_observations");

        if (weatherSleuthReadings.size() == 0) {
            throw new NoRecordException("Nothing found");
        }
        WeatherSleuthReading weatherSleuthReading = weatherSleuthReadings.get(0);
        return new SimpleWeatherData(weatherSleuthReading.getOutsideTemperature(),
                weatherSleuthReading.getAbsoluteBarometer(), weatherSleuthReading.getObservationTimeUtc());
    }

    private class Result {
        @Id
        int hour;
        double temperature;
   }

    @Override
    public History<Double> getDailyTemperatureHighs() {
        History<Double> history = new History<>();

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0).withSecond(0).withMinute(0);
        LocalDateTime endDateTime = LocalDateTime.of(date, time);
        LocalDateTime startDateTime = LocalDateTime.of(date.minusDays(1), time.plusHours(1));

        Fields fields = Fields.from(
                //Fields.field("hour", "{$hours: \"$observationTimeUtc\"}"),
                Fields.field("temperature", "$outsideTemperature")
        );

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("observationTimeUtc").gte(startDateTime).lte(endDateTime)),
                Aggregation.project(fields).and(DateOperators.Hour.hourOf("$observationTimeUtc")).as("hour"),
                Aggregation.group("hour").max("temperature").as("temperature"),
                Aggregation.sort(Sort.Direction.ASC, "_id")
        );
        AggregationResults<Result> result = mongoTemplate.aggregate(aggregation,
                "weather_sleuth_observations", Result.class);

        for (Result value : result.getMappedResults()) {
            System.out.println(String.format("hour : %2d, max temp : %4.1f", value.hour, value.temperature));
        }
        return null;
    }

    public static void main (String[] args) {
        WeatherSleuthRepository weatherSleuthRepository = new WeatherSleuthRepository ();
        weatherSleuthRepository.getDailyTemperatureHighs();
    }
}
