package dev.denischifer.math;

import lombok.Getter;
import java.util.Collections;
import java.util.Map;

@Getter
public class ProbabilityModel<T> {
    private final Map<T, Long> frequencies;
    private final long totalCount;

    public ProbabilityModel(Map<T, Long> frequencies) {
        this.frequencies = Collections.unmodifiableMap(frequencies);
        this.totalCount = frequencies.values().stream().mapToLong(Long::longValue).sum();
    }

    public double getProbability(T symbol) {
        if (totalCount == 0) return 0.0;
        return (double) frequencies.getOrDefault(symbol, 0L) / totalCount;
    }
}