//=============================================================================
// Copyright 2006-2010 Daniel W. Dyer
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//=============================================================================
package org.uncommons.watchmaker.examples.smartant3;

import org.uncommons.watchmaker.examples.smartant3.mealy.MealyMachine;
import org.uncommons.watchmaker.examples.smartant3.mealy.MealyMachineMutation;
import org.uncommons.watchmaker.framework.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Alexander Buslaev
 */
public class AntESEngine extends EvolutionStrategyEngine<MealyMachine> {
    private MealyMachineMutation evolutionScheme;
    private FitnessEvaluator<? super MealyMachine> fitnessEvaluator;
    private final boolean plusSelection;
    private final int evolutionStepTheory;
    private final int evolutionStepPractice;
    private static int offspringMultiplier;
    private final double fitterCandidates;
    private final int numberOfMutationPoints;


    /**
     * Creates a new engine for an evolution strategy.
     *
     * @param candidateFactory       Factory used to create the initial population that is
     *                               iteratively evolved.
     * @param evolutionScheme        The combination of evolutionary operators used to evolve
     *                               the population at each generation.
     * @param fitnessEvaluator       A function for assigning fitness scores to candidate
     *                               solutions.
     * @param plusSelection          If true this object implements a (μ+λ) evolution strategy rather
     *                               than (μ,λ).  With plus-selection the parents are eligible for survival.  With
     *                               comma-selection only the offspring survive.
     * @param offspringMultiplierVAR How many offspring to create for each member of the parent
     *                               population.  This parameter effectively defines a multiplier for μ that gives λ.
     *                               We define λ in this indirect way because we don't know the value of μ until
     *                               it is passed as an argument to one of the evolve methods.
     *                               For a 1+1 ES this parameter would be set to one.  For other evolution strategies
     *                               a higher value might be better. Eiben & Smith suggest 7 as a good value.
     * @param rng                    The source of randomness used by all stochastic processes (including
     *                               evolutionary operators and selection strategies).
     */
    public AntESEngine(CandidateFactory<MealyMachine> candidateFactory, MealyMachineMutation evolutionScheme,
                       FitnessEvaluator<? super MealyMachine> fitnessEvaluator, boolean plusSelection,
                       int offspringMultiplierVAR, int evolutionStepTheory, int evolutionStepPractice,
                       double fitterCandidates, int numberOfMutationPoints, Random rng) {
        super(candidateFactory, null, fitnessEvaluator, plusSelection, offspringMultiplier, rng);
        this.evolutionScheme = evolutionScheme;
        this.fitnessEvaluator = fitnessEvaluator;
        this.plusSelection = plusSelection;
        offspringMultiplier = offspringMultiplierVAR;
        this.evolutionStepTheory = evolutionStepTheory;
        this.evolutionStepPractice = evolutionStepPractice;
        this.fitterCandidates = fitterCandidates;
        this.numberOfMutationPoints = numberOfMutationPoints;
    }


    /**
     * This method performs a single step/iteration of the evolutionary process.
     *
     * @param evaluatedPopulation The population at the beginning of the process.
     * @param eliteCount          Ignored by evolution strategies.  Elitism is implicit in a (μ+λ)
     *                            ES and undesirable for a (μ,λ) ES.
     * @param rng                 A source of randomness.
     * @return The updated population after the evolution strategy has advanced.
     */
    @Override
    protected List<EvaluatedCandidate<MealyMachine>> nextEvolutionStep(
            List<EvaluatedCandidate<MealyMachine>> evaluatedPopulation, int eliteCount, Random rng) {
        // Elite count is ignored.  If it's non-zero it doesn't really matter, but if assertions are
        // enabled we will flag it as wrong.
        assert eliteCount == 0 : "Explicit elitism is not supported for an ES, eliteCount should be 0.";

        // Select candidates that will be operated on to create the offspring.
        int offspringCount = offspringMultiplier * evaluatedPopulation.size();

        List<MealyMachine> parents = new ArrayList<MealyMachine>(offspringCount);
        for (int i = 0; i < offspringCount; i++) {
            parents.add(evaluatedPopulation.get(rng.nextInt(evaluatedPopulation.size())).getCandidate());
        }

//       evolutionScheme.getClass().cast(MooreAutomatonMutation);
        // Then evolve the parents.

        List<MealyMachine> offspring = evolutionScheme.apply(parents, rng);

        List<EvaluatedCandidate<MealyMachine>> evaluatedOffspring = evaluatePopulation(offspring);
        boolean flag = false;
        for (int i = 100; i > 0; --i) {

            offspring = evolutionScheme.apply(parents, rng);
            evaluatedOffspring = evaluatePopulation(offspring);
            double rate = getFitterCandidatesRate(evaluatedOffspring, evaluatedPopulation);

            if (rate >= fitterCandidates) {
                flag = true;
                i = 0;
            }
        }
        if (!flag) {
            for (int i = 10000; i > 0; --i) {
                offspring = evolutionScheme.apply(parents, rng);
                evaluatedOffspring = evaluatePopulation(offspring);
                double rate = getFitterCandidatesRate(evaluatedOffspring, evaluatedPopulation);

                if (rate < fitterCandidates) {
                    evolutionScheme.setMutationPoints(rng.nextInt(numberOfMutationPoints));
                } else {
                    i = 0;
                }
            }

        }


        if (plusSelection) // Plus-selection means parents are considered for survival as well as offspring.
        {
            evaluatedOffspring.addAll(evaluatedPopulation);
        }
        EvolutionUtils.sortEvaluatedPopulation(evaluatedOffspring, fitnessEvaluator.isNatural());

        if (evolutionStepTheory > 0) {
            deleteEqualsEvaluatedPopulation(evaluatedOffspring, evaluatedPopulation.size(), evolutionStepTheory,
                                            evolutionStepPractice);
        }
        // Retain the fittest of the candidates that are eligible for survival.
        return evaluatedOffspring.subList(0, evaluatedPopulation.size());
    }


    private static <T> void deleteEqualsEvaluatedPopulation(List<EvaluatedCandidate<T>> evaluatedPopulation,
                                                            int minNumber, int stepTheory, int stepPractice) {
        Collections.sort(evaluatedPopulation);
        int i = 1;
        while (i < evaluatedPopulation.size() && evaluatedPopulation.size() > minNumber) {
            if (evaluatedPopulation.get(i).getFitness() == evaluatedPopulation.get(i - 1).getFitness()) {
                evaluatedPopulation.remove(i);
            }
            if (i < evaluatedPopulation.size() - minNumber) {
                i += stepPractice;
            } else {
                i += stepTheory;
            }
        }
        Collections.sort(evaluatedPopulation, Collections.reverseOrder());
    }

    private static <T> double getFitterCandidatesRate(List<EvaluatedCandidate<T>> evaluatedOffsping,
                                                      List<EvaluatedCandidate<T>> evaluatedPopulation) {
        double result = 0;
        for (int i = 0; i < evaluatedPopulation.size(); ++i) {
            for (int j = 0; j < offspringMultiplier; ++j) {
                if (evaluatedOffsping.get(i * offspringMultiplier + j).getFitness() > evaluatedPopulation.get(
                        i).getFitness()) {
                    result++;
                }
            }
        }
        result = result / evaluatedOffsping.size();
        return result;
    }
}
