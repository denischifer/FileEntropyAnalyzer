package dev.denischifer.core;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EntropyResult {
    EntropyType type;
    double value;
    long executionTimeMs;
    String fileName;
    long fileSize;
}