package uk.co.iseeshapes.weather.display.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDisplayController {
    private static final SimpleDateFormat format = new SimpleDateFormat("kk:mm:ss");
    @FXML
    private Label temperatureLabel;

    @FXML
    private Label pressureLabel;

    @FXML
    private Label timeLabel;

    public void updateDisplay (double temperature, double pressure, Date time) {
        temperatureLabel.setText(String.format("%2.1f", temperature));
        pressureLabel.setText(String.format("%4.1f", pressure));
        timeLabel.setText(format.format(time));
    }
}
