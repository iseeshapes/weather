package uk.co.iseeshapes.weather.display.data;

import java.time.LocalDateTime;

public class HistoryData<V> {
    private LocalDateTime time;
    private V value;

    public HistoryData(LocalDateTime time, V value) {
        this.time = time;
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public V getValue() {
        return value;
    }
}
