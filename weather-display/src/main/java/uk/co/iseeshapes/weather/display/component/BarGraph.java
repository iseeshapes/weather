package uk.co.iseeshapes.weather.display.component;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BarGraph extends Canvas {
    private double minimumValue;
    private double maximumValue;
    private double[] values;

    public double getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(double minimumValue) {
        this.minimumValue = minimumValue;
    }

    public double getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(double maximumValue) {
        this.maximumValue = maximumValue;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public void draw () {
        GraphicsContext graphicsContext = getGraphicsContext2D();
        graphicsContext.setFill(new Color(0.0, 0.0, 1.0, 1.0));
        graphicsContext.fillRect(0.0, 0.0, getWidth(), getHeight());

        double width = getWidth() / values.length;
        double height;

        double x, y = getHeight();

        graphicsContext.setFill(new Color(1.0, 0.0, 0.0, 1.0));

        int index = 0;

        while (index < values.length) {
            x = width * index;
            height = getHeight() - (getHeight() * ((values[index] - minimumValue) / (maximumValue - minimumValue)));
            if (height < 0.0) {
                height = 0.0;
            }
            graphicsContext.fillRect(x, height, width, y);
            index++;
        }
    }
}
