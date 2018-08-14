package uk.co.iseeshapes.weather.proxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.co.iseeshapes.weather.converter.WeatherStationReadingConverter;
import uk.co.iseeshapes.weather.data.WeatherSleuthReading;
import uk.co.iseeshapes.weather.data.WeatherStationReading;
import uk.co.iseeshapes.weather.proxy.repository.WeatherSleuthRepository;
import uk.co.iseeshapes.weather.proxy.service.WUndergroundUploadService;

@Controller
public class WeatherStationReceiverController {
    private WeatherSleuthRepository weatherSleuthRepository;
    private WeatherStationReadingConverter converter;
    private WUndergroundUploadService wUndergroundUploadService;

    public WeatherStationReceiverController (WeatherSleuthRepository weatherSleuthRepository,
                                             WeatherStationReadingConverter converter,
                                             WUndergroundUploadService wUndergroundUploadService) {
        this.weatherSleuthRepository = weatherSleuthRepository;
        this.converter = converter;
        this.wUndergroundUploadService = wUndergroundUploadService;
    }

    @RequestMapping ("/weatherstation/updateweatherstation.jsp")
    public @ResponseBody String upload (
            //ID=XXXXX
            //PASSWORD=XXXXX
            @RequestParam(value = "outtemp", defaultValue = "0") double outsideTemperature,
            @RequestParam(value = "outhumi", defaultValue = "0") int outsideHumidity,
            @RequestParam(value = "dewpoint", defaultValue = "0") double dewPoint,
            @RequestParam(value = "windchill", defaultValue = "0") double windchill,
            @RequestParam(value = "winddir", defaultValue = "0") int windDirection,
            @RequestParam(value = "windspeed", defaultValue = "0") double windSpeed,
            @RequestParam(value = "windgust", defaultValue = "0") double windGust,
            @RequestParam(value = "rainrate", defaultValue = "0") double rainRate,
            @RequestParam(value = "dailyrain", defaultValue = "0") double dailyRain,
            @RequestParam(value = "weeklyrain", defaultValue = "0") double weeklyRain,
            @RequestParam(value = "monthlyrain", defaultValue = "0") double monthlyRain,
            @RequestParam(value = "yearlyrain", defaultValue = "0") double yearlyRain,
            @RequestParam(value = "light", defaultValue = "0") double light,
            @RequestParam(value = "UV", defaultValue = "0") int uv,
            @RequestParam(value = "intemp", defaultValue = "0") double indoorTemperature,
            @RequestParam(value = "inhumi", defaultValue = "0") int indoorHumidity,
            @RequestParam(value = "absbaro", defaultValue = "0") double absoluteBarometer,
            @RequestParam(value = "relbaro", defaultValue = "0") double relativeBarometer,
            @RequestParam(value = "lowbatt", defaultValue = "0") boolean lowBattery,
            @RequestParam(value = "dateutc", defaultValue = "true") String dateUtc
            //softwaretype=WH2600GEN_V2.2.7
            //action=updateraw
            //realtime=1
            //rtfreq=5
    ) {
        WeatherSleuthReading weatherSleuthReading = new WeatherSleuthReading(dateUtc);
        weatherSleuthReading.setOutsideTemperature (outsideTemperature);
        weatherSleuthReading.setOutsideHumidity (outsideHumidity);
        weatherSleuthReading.setDewPoint (dewPoint);
        weatherSleuthReading.setWindChill (windchill);
        weatherSleuthReading.setWindDirection(windDirection);
        weatherSleuthReading.setWindSpeed (windSpeed);
        weatherSleuthReading.setWindGust (windGust);
        weatherSleuthReading.setRainRate (rainRate);
        weatherSleuthReading.setDailyRain (dailyRain);
        weatherSleuthReading.setWeeklyRain (weeklyRain);
        weatherSleuthReading.setMonthlyRain (monthlyRain);
        weatherSleuthReading.setYearlyRain (yearlyRain);
        weatherSleuthReading.setLight(light);
        weatherSleuthReading.setUv (uv);
        weatherSleuthReading.setIndoorTemperature (indoorTemperature);
        weatherSleuthReading.setIndoorHumidity (indoorHumidity);
        weatherSleuthReading.setAbsoluteBarometer (absoluteBarometer);
        weatherSleuthReading.setRelativeBarometer (relativeBarometer);
        weatherSleuthReading.setLowBattery (lowBattery);

        weatherSleuthRepository.add (weatherSleuthReading);

        WeatherStationReading weatherStationReading = converter.convert(weatherSleuthReading);

        wUndergroundUploadService.uploadReading(weatherStationReading);

        return "success";
    }
}
