package org.uncommons.watchmaker.examples.smartant3.Mealy;
/*
 *  Date: 10.11.11
 *  Time: 10:12
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;


/**
 * Factory that generates basic Moore automaton for evolution
 *
 * @author Alexander Buslaev
 */

public class MealyMachineFactory extends AbstractCandidateFactory<MealyMachine> {
    public static final int COUNTOFIMPACTS = 256;

    private int numberOfStates;


    public MealyMachineFactory(int numberOfStates) {
        this.numberOfStates = numberOfStates;
    }

    public MealyMachine generateRandomCandidate(Random rng) {
        int start = rng.nextInt(numberOfStates);
        MealyMachine machine = new MealyMachine(numberOfStates, start);
        for (int i = 0; i < numberOfStates; ++i) {
            MealyNode node = new MealyNode();
            for(int j = 0; j < COUNTOFIMPACTS; ++j){
                node.setAction(j, MealyNode.Action.getRandomAction(rng));
                node.setNextNode(j, rng.nextInt(numberOfStates));
            }
            machine.addNode(i, node);
        }
        return machine;
    }
}

