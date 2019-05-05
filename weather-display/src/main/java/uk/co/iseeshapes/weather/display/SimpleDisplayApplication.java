package uk.co.iseeshapes.weather.display;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.iseeshapes.weather.display.controller.SimpleDisplayController;
import uk.co.iseeshapes.weather.display.data.SimpleWeatherData;
import uk.co.iseeshapes.weather.display.respository.NoRecordException;
import uk.co.iseeshapes.weather.display.respository.WeatherRepository;
import uk.co.iseeshapes.weather.display.respository.impl.WeatherSleuthRepository;

import java.util.Locale;
import java.util.ResourceBundle;

public class SimpleDisplayApplication extends Application {
    private static final Logger log = LoggerFactory.getLogger(SimpleDisplayApplication.class);

    private class Worker implements Runnable {
        private SimpleDisplayController simpleDisplayController;

        private Worker(SimpleDisplayController simpleDisplayController) {
            this.simpleDisplayController = simpleDisplayController;
        }

        @Override
        public void run() {
            while (true) {
                log.info("Being run...");
                Platform.runLater(() -> {
                    try {
                        SimpleWeatherData data = weatherRepository.getCurrentWeather();
                        simpleDisplayController.updateDisplay(data.getTemperature(), data.getPressure(), data.getTime());
                        log.info("temperature: {}, pressure: {}", data.getTemperature(), data.getPressure());
                    } catch (NoRecordException e) {
                        log.error("No weather current record", e);
                    }
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    private WeatherRepository weatherRepository = new WeatherSleuthRepository();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale locale = new Locale("en");

        primaryStage.setTitle("Temperature");

        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("bundles.display", locale));
        loader.setLocation(SimpleDisplayApplication.class.getResource("/fxml/simpleDisplay.fxml"));

        GridPane root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(SimpleDisplayApplication.class.getResource("/css/simpleDisplay.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        SimpleDisplayController simpleDisplayController = loader.getController();
        Worker worker = new Worker(simpleDisplayController);
        Thread workerThread = new Thread(worker);
        workerThread.start();

        primaryStage.setOnCloseRequest(e -> {
            workerThread.interrupt();
        });
    }

    public static void main (String args) {
        launch(args);
    }
}
