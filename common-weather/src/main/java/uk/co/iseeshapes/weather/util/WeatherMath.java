package uk.co.iseeshapes.weather.util;

public class WeatherMath {
    public static final double milesPerHoursToMetersPerSecond = 0.44704;
    public static final double inchesOfMercuryToMilliBar = 0.0338639 * 1000;
    public static final double inchesToMilliMeters = 24.5;

    public static double fahrenheitToCelsius (double fahrenheit) {
        return 5 * (fahrenheit - 32) / 9;
    }

    public static double celsiusToFahrenheit (double celsius) {
        return (9 * celsius / 5) + 32;
    }

    public static double calculateDewPoint (double temperature, double humidity) {
        return temperature - (100 - humidity) / 5;
    }
}
