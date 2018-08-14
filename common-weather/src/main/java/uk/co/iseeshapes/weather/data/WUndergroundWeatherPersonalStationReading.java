package uk.co.iseeshapes.weather.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WUndergroundWeatherPersonalStationReading {
    private static final Logger log = LoggerFactory.getLogger(WUndergroundWeatherPersonalStationReading.class);

    private static final String datePattern = "yyyy-MM-dd hh:mm:ss";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @JsonIgnore
    private Date readingTime;

    @JsonProperty("winddir")
    private short windDirection;           //[0-360 instantaneous wind direction]

    @JsonProperty("windspeedmph")
    private double windSpeed;              //[mph instantaneous wind speed]

    @JsonProperty("windgustmph")
    private double windGust;               //[mph current wind gust, using software specific time period]

    @JsonProperty("baromin")
    private double pressure;               //[barometric pressure inches]

    @JsonProperty("humidity")
    private int humidity;                  //[% outdoor humidity 0-100%]

    @JsonProperty("dewptf")
    private double dewPoint;               //[F outdoor dewpoint F]

    @JsonProperty("tempf")
    private double temperature;            //[F outdoor temperature]

    @JsonProperty("rainin")
    private double rainRate;               //[rain inches over the past hour)] -- the accumulated rainfall in the past 60 min

    @JsonProperty("dailyrainin")
    private double dailyRain;              //[rain inches so far today in local time]

    @JsonProperty("solarradiation")
    private double solarRadiation;         //[W/m^2]

    @JsonProperty("UV")
    private double uv;                   //[index]

    @JsonProperty("indoortempf")
    private double indoorTemperature;      //[F indoor temperature F]

    @JsonProperty("indoorhumidity")
    private int indoorHumidity;            //[% indoor humidity 0-100]

    public WUndergroundWeatherPersonalStationReading(Date readingTime) {
        this.readingTime = readingTime;
    }

    @JsonCreator
    public WUndergroundWeatherPersonalStationReading(@JsonProperty("dateutc") String dateUtc) {
        try {
            readingTime = simpleDateFormat.parse(dateUtc);
        } catch (ParseException e) {
            log.error("Unable to parse date {} to width String {}", dateUtc, datePattern);
            readingTime = new Date();
        }
    }

    @JsonProperty("dateutc")
    public String getDateUtc () {
        return simpleDateFormat.format(readingTime);
    }

    @JsonIgnore
    public Date getReadingTime() {
        return readingTime;
    }

    @JsonIgnore
    public short getWindDirection() {
        return windDirection;
    }

    @JsonIgnore
    public void setWindDirection(short windDirection) {
        this.windDirection = windDirection;
    }

    @JsonIgnore
    public double getWindSpeed() {
        return windSpeed;
    }

    @JsonIgnore
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @JsonIgnore
    public double getWindGust() {
        return windGust;
    }

    @JsonIgnore
    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }

    @JsonIgnore
    public double getPressure() {
        return pressure;
    }

    @JsonIgnore
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    @JsonIgnore
    public int getHumidity() {
        return humidity;
    }

    @JsonIgnore
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @JsonIgnore
    public double getDewPoint() {
        return dewPoint;
    }

    @JsonIgnore
    public void setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
    }

    @JsonIgnore
    public double getTemperature() {
        return temperature;
    }

    @JsonIgnore
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @JsonIgnore
    public double getRainRate() {
        return rainRate;
    }

    @JsonIgnore
    public void setRainRate(double rainRate) {
        this.rainRate = rainRate;
    }

    @JsonIgnore
    public double getDailyRain() {
        return dailyRain;
    }

    @JsonIgnore
    public void setDailyRain(double dailyRain) {
        this.dailyRain = dailyRain;
    }

    @JsonIgnore
    public double getSolarRadiation() {
        return solarRadiation;
    }

    @JsonIgnore
    public void setSolarRadiation(double solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    @JsonIgnore
    public double getUv() {
        return uv;
    }

    @JsonIgnore
    public void setUv(double uvIndex) {
        this.uv = uv;
    }

    @JsonIgnore
    public double getIndoorTemperature() {
        return indoorTemperature;
    }

    @JsonIgnore
    public void setIndoorTemperature(double indoorTemperature) {
        this.indoorTemperature = indoorTemperature;
    }

    @JsonIgnore
    public int getIndoorHumidity() {
        return indoorHumidity;
    }

    @JsonIgnore
    public void setIndoorHumidity(int indoorHumidity) {
        this.indoorHumidity = indoorHumidity;
    }
}
