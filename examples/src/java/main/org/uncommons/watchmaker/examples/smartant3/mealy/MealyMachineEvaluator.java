package org.uncommons.watchmaker.examples.smartant3.mealy;
/*
 *  Date: 10.11.11
 *  Time: 17:34
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.uncommons.watchmaker.examples.smartant3.AntMover;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

public class MealyMachineEvaluator implements FitnessEvaluator<MealyMachine> {

    public double getFitness(MealyMachine candidate, List<? extends MealyMachine> population) {
        //todo population ???
        AntMover f = new AntMover(candidate);
        double a = f.moveAnt();
        return a;
    }

    public boolean isNatural() {
        return true;
    }
}
