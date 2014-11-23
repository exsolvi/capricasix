package se.exsolvi.capricasix.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

public class DoubleArrayMutation implements EvolutionaryOperator<double[]> {
  private Probability probability;

  public DoubleArrayMutation(Probability probability) {
    this.probability = probability;
  }

  public List<double[]> apply(List<double[]> candidates, Random rng) {
    List<double[]> nextGeneration = new ArrayList<double[]>(candidates.size());
    // Not very elegant, but the easiest way to create new candidates is to use the factory
    DelayedStepCandidateFactory factory = new DelayedStepCandidateFactory(candidates.get(0).length);
    for (double[] candidate : candidates) {
      if (rng.nextDouble() < probability.doubleValue()) {
        candidate = factory.generateRandomCandidate(rng);
      }
      nextGeneration.add(candidate);
    }
    return nextGeneration;
  }

}
