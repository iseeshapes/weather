package uk.co.iseeshapes.weather.display.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class History<V> {
    private List<HistoryData<V>> history = new ArrayList<>();

    public void add (LocalDateTime time, V value) {
        history.add(new HistoryData<>(time, value));
    }

    public void clear () {
        history.clear();
    }

    public List<V> getValues () {
        List<V> values = new ArrayList<>();
        for (HistoryData<V> historyData :history) {
            values.add(historyData.getValue());
        }
        return values;
    }
}
