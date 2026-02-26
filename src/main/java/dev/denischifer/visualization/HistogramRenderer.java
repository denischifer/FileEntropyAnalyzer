package dev.denischifer.visualization;

import dev.denischifer.math.ByteSequence;

import java.util.Arrays;
import java.util.Map;

public class HistogramRenderer {
    public void render(Map<ByteSequence, Long> frequencies) {
        System.out.println("\n--- Byte Distribution ---");
        frequencies.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    byte[] data = entry.getKey().data();
                    String label = (data.length == 1)
                            ? String.format("0x%02X", data[0])
                            : Arrays.toString(data);

                    String bar = "■".repeat((int) Math.min(entry.getValue() / 100, 50));
                    System.out.printf("%-10s: %s (%d)%n", label, bar, entry.getValue());
                });
    }
}