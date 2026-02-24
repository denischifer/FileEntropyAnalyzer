package dev.denischifer.core;

import dev.denischifer.math.ProbabilityModel;

public interface EntropyCalculator {
    double calculate(ProbabilityModel<?> model);
}