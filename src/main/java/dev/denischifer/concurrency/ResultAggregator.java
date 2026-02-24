package dev.denischifer.concurrency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class ResultAggregator<T> {
    private final Map<T, LongAdder> frequencies = new ConcurrentHashMap<>();

    public void add(T symbol) {
        frequencies.computeIfAbsent(symbol, k -> new LongAdder()).increment();
    }

    public void addMany(T symbol, long count) {
        frequencies.computeIfAbsent(symbol, k -> new LongAdder()).add(count);
    }

    public Map<T, Long> build() {
        Map<T, Long> result = new ConcurrentHashMap<>();
        frequencies.forEach((k, v) -> result.put(k, v.sum()));
        return result;
    }
}