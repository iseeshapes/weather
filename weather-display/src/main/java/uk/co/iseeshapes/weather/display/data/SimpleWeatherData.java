package uk.co.iseeshapes.weather.display.data;

import java.util.Date;

public class SimpleWeatherData {
    private double temperature;
    private double pressure;
    private Date time;

    public SimpleWeatherData() {
    }

    public SimpleWeatherData(double temperature, double pressure, Date time) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
