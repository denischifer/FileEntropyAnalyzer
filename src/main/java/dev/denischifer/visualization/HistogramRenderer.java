package dev.denischifer.visualization;

import java.util.Map;

public class HistogramRenderer {
    public void render(Map<Integer, Long> freqs) {
        long max = freqs.values().stream().max(Long::compare).orElse(1L);
        freqs.entrySet().stream().sorted(Map.Entry.comparingByKey()).limit(16).forEach(e -> {
            int bars = (int) ((double) e.getValue() / max * 40);
            System.out.printf("0x%02X: %s%n", e.getKey(), "|".repeat(bars));
        });
    }
}