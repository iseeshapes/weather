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
import java.text.DecimalFormat;

public class DefaultWUndergroundUploadService implements WUndergroundUploadService {
    private static final Logger log = LoggerFactory.getLogger(DefaultWUndergroundUploadService.class);

    private static final DecimalFormat simpleNumberFormat = new DecimalFormat("0.0");
    private static final DecimalFormat rainNumberFormat = new DecimalFormat("0.0###");

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
                    "&winddir=" + simpleNumberFormat.format(data.getWindDirection()) +
                    "&windspeedmph=" + simpleNumberFormat.format(data.getWindSpeed()) +
                    "&windgustmph=" + simpleNumberFormat.format(data.getWindGust()) +
                    "&baromin=" + simpleNumberFormat.format(data.getPressure()) +
                    "&humidity=" + simpleNumberFormat.format(data.getHumidity()) +
                    "&dewptf=" + simpleNumberFormat.format(data.getDewPoint()) +
                    "&tempf=" + simpleNumberFormat.format(data.getTemperature()) +
                    "&rainin=" + rainNumberFormat.format(data.getRainRate()) +
                    "&dailyrainin=" + rainNumberFormat.format(data.getDailyRain()) +
                    "&solarradiation=" + simpleNumberFormat.format(data.getSolarRadiation()) +
                    "&UV=" + simpleNumberFormat.format(data.getUv()) +
                    "&indoortempf=" + simpleNumberFormat.format(data.getIndoorTemperature()) +
                    "&indoorhumidity=" + simpleNumberFormat.format(data.getIndoorHumidity());

            Thread thread = new Thread(new Worker(new URI(url)));
            thread.start();
        } catch (Exception e) {
            log.error("Upload to WUnderground failed dew to Bad URI", e);
        }
    }
}
