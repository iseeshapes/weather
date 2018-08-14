package uk.co.iseeshapes.weather.data;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class WeatherStationReading {
    @Id
    private Long id;

    private Date readingTime;         // UTC

    private double windDirection;     //degrees of north

    private double windSpeed;         // m/s

    private double windGust;          // m/s

    private double pressure;          // milli Bar

    private int humidity;             // %

    private double temperature;       // Degrees Centigrade

    private double rainRate;          // mm/hour

    private double rainToday;         // total mm today

    private double solarRadiation;    // W/m^2

    private double uv;                // Index

    private double indoorTemperature; // Degrees Centigrade

    private int indoorHumidity;    // %

    public WeatherStationReading(Date readingTime) {
        this.readingTime = readingTime;
    }

    public Long getId() {
        return id;
    }

    public Date getReadingTime() {
        return readingTime;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
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

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getRainRate() {
        return rainRate;
    }

    public void setRainRate(double rainRate) {
        this.rainRate = rainRate;
    }

    public double getRainToday() {
        return rainToday;
    }

    public void setRainToday(double rainToday) {
        this.rainToday = rainToday;
    }

    public double getSolarRadiation() {
        return solarRadiation;
    }

    public void setSolarRadiation(double solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    public double getUv() {
        return uv;
    }

    public void setUv(double uv) {
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
}
