package org.uncommons.watchmaker.examples.smartant3.mealy;
/*
 *  Date: 10.11.11
 *  Time: 17:34
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.uncommons.watchmaker.examples.smartant3.AntMover;
import org.uncommons.watchmaker.examples.smartant3.Properties;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;
import java.util.Random;

public class MealyMachineEvaluator implements FitnessEvaluator<MealyMachine> {
    boolean fields[][][];

    public MealyMachineEvaluator(int countOfFields, Random random){
        assert(countOfFields > 0  && countOfFields < 100);

        fields = new boolean[countOfFields][][];;
        for(int i = 0; i < countOfFields; ++i){
            fields[i] = generateRandomField(Properties.mu, random);
        }
    }
    public double getFitness(MealyMachine candidate, List<? extends MealyMachine> population) {
        //todo population ???
        AntMover mover;
        double sum = 0;
        for(int i = 0; i < fields.length; ++i){
            mover = new AntMover(candidate, fields[i]);
            sum += mover.moveAnt();
        }
        return sum / fields.length;
    }

    public boolean isNatural() {
        return true;
    }

    private boolean[][] generateRandomField(double mu, Random random) {
        int size = Properties.SIZE;
        boolean field[][] = new boolean[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (random.nextDouble() < mu) {
                    field[i][j] = true;
                }
            }
        }
        return field;
    }
}
