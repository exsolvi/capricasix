package se.exsolvi.capricasix.genetic;

import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

public class StrideEvaluator implements FitnessEvaluator<double[]> {

  public double getFitness(double[] candidate, List<? extends double[]> population) {
    return candidate[0] * 500; // For now, let the stride with the longest delay for leg 0 win
    // TODO: This method should let the spider move and return the score of the movement
  }

  public boolean isNatural() {
    return true;
  }

}
