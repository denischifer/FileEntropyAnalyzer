package dev.denischifer.algorithms;

import dev.denischifer.core.EntropyCalculator;
import dev.denischifer.math.ProbabilityModel;
import dev.denischifer.math.StatisticsUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlockEntropy implements EntropyCalculator {
    private final int blockSize;

    @Override
    public double calculate(ProbabilityModel<?> model) {
        return StatisticsUtils.calculateShannon(model) / blockSize;
    }
}