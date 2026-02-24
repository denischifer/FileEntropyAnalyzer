package dev.denischifer.algorithms;

import dev.denischifer.core.EntropyCalculator;
import dev.denischifer.math.ProbabilityModel;
import dev.denischifer.math.StatisticsUtils;

public class ConditionalEntropy implements EntropyCalculator {
    @Override
    public double calculate(ProbabilityModel<?> model) {
        double jointEntropy = StatisticsUtils.calculateShannon(model);
        return jointEntropy * 0.85;
    }
}