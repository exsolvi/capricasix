package se.exsolvi.capricasix.genetic;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.SteadyStateEvolutionEngine;
import org.uncommons.watchmaker.framework.operators.DoubleArrayCrossover;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

public class GeneticWalker {

  public static void main(String[] args) {
    int populationSize = 100;
    int numLegs = 4;
    double targetFitness = 499.0; // Stop execution when we reach 499 pixels forward movement

    DelayedStepCandidateFactory candidateFactory = new DelayedStepCandidateFactory(numLegs);

    // Create a pipeline that applies cross-over then mutation.
    List<EvolutionaryOperator<double[]>> operators = new LinkedList<EvolutionaryOperator<double[]>>();
    operators.add(new DoubleArrayMutation(new Probability(0.02)));
    operators.add(new DoubleArrayCrossover());
    EvolutionaryOperator<double[]> pipeline = new EvolutionPipeline<double[]>(operators);

    FitnessEvaluator<double[]> fitnessEvaluator = new StrideEvaluator();
    SelectionStrategy<Object> selection = new RouletteWheelSelection();
    Random rng = new MersenneTwisterRNG();

    EvolutionEngine<double[]> engine = new SteadyStateEvolutionEngine<double[]>(candidateFactory, pipeline, fitnessEvaluator, selection, 1, false, rng);

    engine.addEvolutionObserver(new EvolutionObserver<double[]>() {
      public void populationUpdate(PopulationData<? extends double[]> data) {
        System.out.printf("Generation %d: ", data.getGenerationNumber());
        double[] candidate = data.getBestCandidate();
        for (double d : candidate) {
          System.out.print(" " + d);
        }
        System.out.println("");
      }

    });

    double[] finalStride = engine.evolve(populationSize, 0, new TargetFitness(targetFitness, true));
    String result = "";
    for (int i = 0; i < finalStride.length; i++) {
      result += finalStride[i];
      if (i < finalStride.length - 1) {
        result += ":";
      }
    }
    System.out.println(result);
  }

}
