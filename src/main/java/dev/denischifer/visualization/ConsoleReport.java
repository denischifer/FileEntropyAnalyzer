package dev.denischifer.visualization;

import dev.denischifer.core.EntropyResult;

public class ConsoleReport {
    public void print(EntropyResult result) {
        System.out.println("=== Entropy Analysis Report ===");
        System.out.println("File: " + result.getFileName());
        System.out.println("Mode: " + result.getType());
        System.out.println("Value: " + String.format("%.6f", result.getValue()) + " bits/symbol");
        System.out.println("Time: " + result.getExecutionTimeMs() + " ms");
    }
}