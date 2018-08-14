package uk.co.iseeshapes.weather.converter;

import uk.co.iseeshapes.weather.data.WUndergroundWeatherPersonalStationReading;
import uk.co.iseeshapes.weather.data.WeatherSleuthReading;
import uk.co.iseeshapes.weather.data.WeatherStationReading;
import uk.co.iseeshapes.weather.util.WeatherMath;

public class WeatherStationReadingConverter {
    public WeatherStationReading convert (WeatherSleuthReading weatherSleuthReading) {
        WeatherStationReading result = new WeatherStationReading(weatherSleuthReading.getObservationTimeUtc());
        result.setWindDirection((short)Math.round(Math.toDegrees(weatherSleuthReading.getWindDirection())));
        result.setWindSpeed(weatherSleuthReading.getWindSpeed());
        result.setWindGust(weatherSleuthReading.getWindGust());
        result.setPressure(weatherSleuthReading.getAbsoluteBarometer());
        result.setHumidity(weatherSleuthReading.getOutsideHumidity());
        result.setTemperature(weatherSleuthReading.getOutsideTemperature());
        result.setRainRate(weatherSleuthReading.getDailyRain());
        result.setRainToday(weatherSleuthReading.getDailyRain());
        result.setSolarRadiation(weatherSleuthReading.getLight());
        result.setUv(weatherSleuthReading.getUv());
        result.setIndoorTemperature(weatherSleuthReading.getIndoorTemperature());
        result.setIndoorHumidity(weatherSleuthReading.getIndoorHumidity());

        return result;
    }

    public WUndergroundWeatherPersonalStationReading convert (WeatherStationReading weatherStationReading) {
        WUndergroundWeatherPersonalStationReading result = new WUndergroundWeatherPersonalStationReading(weatherStationReading.getReadingTime());
        result.setWindDirection((short)Math.round(Math.toDegrees(weatherStationReading.getWindDirection())));
        result.setWindSpeed(weatherStationReading.getWindSpeed() / WeatherMath.milesPerHoursToMetersPerSecond);
        result.setWindGust(weatherStationReading.getWindGust() / WeatherMath.milesPerHoursToMetersPerSecond);
        result.setPressure(weatherStationReading.getPressure() / WeatherMath.inchesOfMercuryToMilliBar);
        result.setHumidity(weatherStationReading.getHumidity());
        double dewPoint = WeatherMath.calculateDewPoint(weatherStationReading.getTemperature(), weatherStationReading.getHumidity());
        result.setDewPoint(WeatherMath.celsiusToFahrenheit(dewPoint));
        result.setTemperature(WeatherMath.celsiusToFahrenheit(weatherStationReading.getTemperature()));
        result.setRainRate(weatherStationReading.getRainRate() / WeatherMath.inchesToMilliMeters);
        result.setDailyRain(weatherStationReading.getRainToday() / WeatherMath.inchesToMilliMeters);
        result.setSolarRadiation(weatherStationReading.getSolarRadiation());
        result.setUv(weatherStationReading.getUv());
        result.setIndoorTemperature(WeatherMath.celsiusToFahrenheit(weatherStationReading.getIndoorTemperature()));
        result.setIndoorHumidity(weatherStationReading.getIndoorHumidity());

        return result;
    }
}
