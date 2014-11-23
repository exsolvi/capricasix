package se.exsolvi.capricasix.genetic;

import java.util.Random;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

public class DelayedStepCandidateFactory extends AbstractCandidateFactory<double[]> {

  private double max = 1.0;
  private int numLegs;
  private double[] delays;

  public DelayedStepCandidateFactory(int numLegs) {
    this.numLegs = numLegs;
  }

  public double[] generateRandomCandidate(Random r) {
    delays = new double[numLegs];
    for(int i=0; i<numLegs; i++){
      delays[i] = r.nextDouble() * max;
    }
    return delays;
  }

}
