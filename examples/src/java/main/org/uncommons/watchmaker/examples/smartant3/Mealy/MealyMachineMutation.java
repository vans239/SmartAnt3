package org.uncommons.watchmaker.examples.smartant3.mealy;
/*
 *  Date: 10.11.11
 *  Time: 11:02
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Alexander Buslaev
 */
public class MealyMachineMutation implements EvolutionaryOperator<MealyMachine> {
    private static final int COUNTOFIMPACTS = 256;
    private int mutationPoints;
    /*
        mutation start state
        mutation transiton nextNode
        mutation action on transition
    */
    private int typesOfMutations = 3;

    public MealyMachineMutation() {
        mutationPoints = 1;
    }


    /**
     * Randomly mutate each selected candidate.
     *
     * @param selectedCandidates {@inheritDoc}
     * @param rng                {@inheritDoc}
     * @return {@inheritDoc}
     */
    public List<MealyMachine> apply(List<MealyMachine> selectedCandidates, Random rng) {
        List<MealyMachine> mutatedPopulation = new ArrayList<MealyMachine>(selectedCandidates.size());
        for (int i = 0; i < selectedCandidates.size(); ++i) {
            MealyMachine machine = selectedCandidates.get(i);
            MealyMachine mutated = mutateMachine(machine, rng);
            mutatedPopulation.add(mutated);
        }
        return mutatedPopulation;
    }


    /**
     * Mutates a single machine.
     *
     * @param machine The machine to mutate.
     * @param rng      The source of randomness to use for mutation.
     * @return A mutated version of the machine.
     */
    private MealyMachine mutateMachine(MealyMachine machine, Random rng) {
        machine = machine.clone();  // copying!!
        int mutated = 0;
        //realy mutated points can be less then mutationPoints
        while (mutated < mutationPoints) {
            // p(mutation start state) = 0.1%
            double random = rng.nextDouble();
            if(random < 0.001){
                machine.setStart(rng.nextInt(machine.getSize()));
            }
            if(random < 0.5){
                int i = rng.nextInt(machine.getSize());
                int j = rng.nextInt(COUNTOFIMPACTS);
                machine.getNode(i).setNextNode(j, rng.nextInt(machine.getSize()));
            }
            if(random > 0.5){
                int i = rng.nextInt(machine.getSize());
                int j = rng.nextInt(COUNTOFIMPACTS);
                machine.getNode(i).setAction(j, ShortcutMealyNode.Action.getRandomAction(rng));
            }
            ++mutated;

        }
        return machine;
    }

    public void setMutationPoints(int i) {
        mutationPoints = i;
    }
}

