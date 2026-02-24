package dev.denischifer.algorithms;

import dev.denischifer.core.EntropyCalculator;
import dev.denischifer.math.ProbabilityModel;
import dev.denischifer.math.StatisticsUtils;

public class ShannonEntropy implements EntropyCalculator {
    @Override
    public double calculate(ProbabilityModel<?> model) {
        return StatisticsUtils.calculateShannon(model);
    }
}