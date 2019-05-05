package uk.co.iseeshapes.weather.display.respository;

public class NoRecordException extends Exception {
    public NoRecordException (String message) {
        super(message);
    }

    public NoRecordException (String message, Throwable throwable) {
        super (message, throwable);
    }
}
