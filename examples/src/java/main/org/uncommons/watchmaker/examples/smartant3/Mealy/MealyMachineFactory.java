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
    private int numberOfStates;


    public MealyMachineFactory(int numberOfStates) {
        this.numberOfStates = numberOfStates;
    }

    public MealyMachine generateRandomCandidate(Random rng) {
        int start = rng.nextInt(numberOfStates);
        MealyMachine machine = new MealyMachine(numberOfStates, start);
        for (int i = 0; i < numberOfStates; ++i) {
            ShortcutMealyNode node = new ShortcutMealyNode(rng);
            for(int j = 0; j < node.getCountOfImpacts(); ++j){
                node.setAction(j, ShortcutMealyNode.Action.getRandomAction(rng));
                node.setNextNode(j, rng.nextInt(numberOfStates));
            }
            machine.addNode(i, node);
        }
        return machine;
    }
/*
    public  static void main(String argv[]){
        Random random = new Random();
        MealyMachineFactory mmf = new MealyMachineFactory(7);
        MealyMachine machine = mmf.generateRandomCandidate(random);
        System.out.println(machine.getNode(4).getPredictImpact(255));
        System.out.println(machine.getNode(4).getPredictImpact(15));
    }*/
}

