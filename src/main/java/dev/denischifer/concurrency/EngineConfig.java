package dev.denischifer.concurrency;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EngineConfig {
    int threadCount;
    int chunkSize;
    int queueCapacity;
}