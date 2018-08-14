package uk.co.iseeshapes.weather.proxy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uk.co.iseeshapes.weather.converter.WeatherStationReadingConverter;
import uk.co.iseeshapes.weather.data.WUndergroundWeatherPersonalStationReading;
import uk.co.iseeshapes.weather.data.WeatherStationReading;
import uk.co.iseeshapes.weather.proxy.service.WUndergroundUploadService;

import java.net.*;

public class DefaultWUndergroundUploadService implements WUndergroundUploadService {
    private static final Logger log = LoggerFactory.getLogger(DefaultWUndergroundUploadService.class);

    private final String username;
    private final String password;

    private class Worker implements Runnable {
        private URI uri;

        private Worker(URI uri) {
            this.uri = uri;
        }

        @Override
        public void run() {
            try {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    //carry on...
                }
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
                if (log.isDebugEnabled()) {
                    log.debug("WUnderground response: {}", response.getBody());
                }
            } catch (HttpClientErrorException e) {
                log.error("Error uploading data to {}", uri.toString(), e);
            }
        }
    }

    private WeatherStationReadingConverter converter;

    public DefaultWUndergroundUploadService(WeatherStationReadingConverter converter, String username, String password) {
        this.converter = converter;
        this.username = username;
        this.password = password;
    }

    @Override
    public void uploadReading(WeatherStationReading weatherStationReading) {
        WUndergroundWeatherPersonalStationReading data = converter.convert(weatherStationReading);

        try {
            String url = "http://weatherstation.wunderground.com/weatherstation/updateweatherstation.php?" +
                    "action=updateraw&ID=" + username + "&PASSWORD=" + password +
                    "&dateutc=" + URLEncoder.encode(data.getDateUtc(), "UTF-8") +
                    "&winddir=" + data.getWindDirection() + "&windspeedmph=" + data.getWindSpeed() +
                    "&windgustmph=" + data.getWindGust() + "&baromin=" + data.getPressure() +
                    "&humidity=" + data.getHumidity() + "&dewptf=" + data.getDewPoint() +
                    "&tempf=" + data.getTemperature() + "&rainin=" + data.getRainRate() +
                    "&dailyrainin=" + data.getDailyRain() + "&solarradiation=" + data.getSolarRadiation() +
                    "&UV=" + data.getUv() + "&indoortempf=" + data.getIndoorTemperature() +
                    "&indoorhumidity=" + data.getIndoorHumidity();

            Thread thread = new Thread(new Worker(new URI(url)));
            thread.start();
        } catch (Exception e) {
            log.error("Upload to WUnderground failed dew to Bad URI", e);
        }
    }
}
