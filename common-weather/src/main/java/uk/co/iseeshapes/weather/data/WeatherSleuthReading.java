package uk.co.iseeshapes.weather.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Document
public class WeatherSleuthReading {
    private static final Logger log = LoggerFactory.getLogger(WeatherSleuthReading.class);
    private static final SimpleDateFormat utcFormat;

    static {
        utcFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Id
    private long id;
    private Date observationTimeUtc;    // time utc
    private double outsideTemperature;  // degrees c
    private int outsideHumidity;        // %
    private double dewPoint;            // degrees c
    private double windChill;           // degrees c
    private int windDirection;          // angle from north
    private double windSpeed;           // m/s
    private double windGust;            // m/s
    private double rainRate;            // mm / hour
    private double dailyRain;           // mm / day
    private double weeklyRain;          // mm / week
    private double monthlyRain;         // mm / month
    private double yearlyRain;          // mm / year
    private double light;               // W / m^2
    private int uv;                     // ?
    private double indoorTemperature;   // degrees c
    private int indoorHumidity;         // %
    private double absoluteBarometer;   // hPa
    private double relativeBarometer;   // hPa
    private String lowBattery;         // true / false

    public WeatherSleuthReading () {

    }

    public WeatherSleuthReading (String dateUtc) {
        try {
            observationTimeUtc = utcFormat.parse(dateUtc);
        } catch (ParseException e) {
            log.error("Unable to pass {} in {} format setting reading as now", dateUtc, utcFormat.toPattern());
            observationTimeUtc = new Date();
        }
    }

    public WeatherSleuthReading (Date observationTimeUtc) {
        this.observationTimeUtc = observationTimeUtc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getObservationTimeUtc() {
        return observationTimeUtc;
    }

    public void setObservationTimeUtc(Date observationTimeUtc) {
        this.observationTimeUtc = observationTimeUtc;
    }

    public double getOutsideTemperature() {
        return outsideTemperature;
    }

    public void setOutsideTemperature(double outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public int getOutsideHumidity() {
        return outsideHumidity;
    }

    public void setOutsideHumidity(int outsideHumidity) {
        this.outsideHumidity = outsideHumidity;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public double getWindChill() {
        return windChill;
    }

    public void setWindChill(double windchill) {
        this.windChill = windchill;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindGust() {
        return windGust;
    }

    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }

    public double getRainRate() {
        return rainRate;
    }

    public void setRainRate(double rainRate) {
        this.rainRate = rainRate;
    }

    public double getDailyRain() {
        return dailyRain;
    }

    public void setDailyRain(double dailyRain) {
        this.dailyRain = dailyRain;
    }

    public double getWeeklyRain() {
        return weeklyRain;
    }

    public void setWeeklyRain(double weeklyRain) {
        this.weeklyRain = weeklyRain;
    }

    public double getMonthlyRain() {
        return monthlyRain;
    }

    public void setMonthlyRain(double monthlyRain) {
        this.monthlyRain = monthlyRain;
    }

    public double getYearlyRain() {
        return yearlyRain;
    }

    public void setYearlyRain(double yearlyRain) {
        this.yearlyRain = yearlyRain;
    }

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public double getIndoorTemperature() {
        return indoorTemperature;
    }

    public void setIndoorTemperature(double indoorTemperature) {
        this.indoorTemperature = indoorTemperature;
    }

    public int getIndoorHumidity() {
        return indoorHumidity;
    }

    public void setIndoorHumidity(int indoorHumidity) {
        this.indoorHumidity = indoorHumidity;
    }

    public double getAbsoluteBarometer() {
        return absoluteBarometer;
    }

    public void setAbsoluteBarometer(double absoluteBarometer) {
        this.absoluteBarometer = absoluteBarometer;
    }

    public double getRelativeBarometer() {
        return relativeBarometer;
    }

    public void setRelativeBarometer(double relativeBarometer) {
        this.relativeBarometer = relativeBarometer;
    }

    public String isLowBattery() {
        return lowBattery;
    }

    public void setLowBattery(String lowBattery) {
        this.lowBattery = lowBattery;
    }
}
