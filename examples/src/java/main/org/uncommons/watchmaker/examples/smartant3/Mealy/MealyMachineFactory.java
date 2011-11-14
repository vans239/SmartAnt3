package org.uncommons.watchmaker.examples.smartant3.mealy;
/*
 *  Date: 10.11.11
 *  Time: 10:12
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.uncommons.watchmaker.examples.smartant3.Properties;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;


/**
 * Factory that generates basic Moore automaton for evolution
 *
 * @author Alexander Buslaev
 */

public class MealyMachineFactory extends AbstractCandidateFactory<MealyMachine> {
    public static final int COUNTOFIMPACTS = Properties.COUNTOFIMPACTS;

    private int numberOfStates;


    public MealyMachineFactory(int numberOfStates) {
        this.numberOfStates = numberOfStates;
    }

    public MealyMachine generateRandomCandidate(Random rng) {
        int start = rng.nextInt(numberOfStates);
        MealyMachine machine = new MealyMachine(numberOfStates, start);
        for (int i = 0; i < numberOfStates; ++i) {
            ShortcutMealyNode node = new ShortcutMealyNode();
            for(int j = 0; j < COUNTOFIMPACTS; ++j){
                node.setAction(j, ShortcutMealyNode.Action.getRandomAction(rng));
                node.setNextNode(j, rng.nextInt(numberOfStates));
            }
            machine.addNode(i, node);
        }
        return machine;
    }
}

