package org.uncommons.watchmaker.examples.smartAnt3;
/*
 *  Date: 10.11.11
 *  Time: 10:24
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.testng.annotations.Test;
import org.uncommons.watchmaker.examples.smartant3.mealy.MealyMachine;
import org.uncommons.watchmaker.examples.smartant3.mealy.MealyMachineFactory;
import org.uncommons.watchmaker.examples.smartant3.mealy.MealyMachineMutation;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MealyTest {
    @Test
    public void testFactory() {
        Random random = new Random();
        MealyMachineFactory factory = new MealyMachineFactory(10);
        MealyMachine machine = factory.generateRandomCandidate(random);
        assert true;
    }

    @Test
    public void testMutation() {
        Random random = new Random();
        MealyMachineFactory factory = new MealyMachineFactory(1);
        MealyMachine machine = factory.generateRandomCandidate(random);
        List<MealyMachine> list = new ArrayList<MealyMachine>();
        list.add(machine);
        MealyMachineMutation mmm = new MealyMachineMutation();
        List<MealyMachine> result = mmm.apply(list, random);
        assert true;
    }
}
