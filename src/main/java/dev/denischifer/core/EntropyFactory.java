package dev.denischifer.core;

import dev.denischifer.algorithms.*;
import java.util.Map;

public class EntropyFactory {
    public static EntropyCalculator get(EntropyType type, Map<String, Object> params) {
        return switch (type) {
            case SHANNON -> new ShannonEntropy();
            case MIN_ENTROPY -> new MinEntropy();
            case BLOCK -> new BlockEntropy((int) params.getOrDefault("blockSize", 1));
            case CONDITIONAL -> new ConditionalEntropy();
            default -> throw new UnsupportedOperationException("Mode not integrated in factory");
        };
    }
}