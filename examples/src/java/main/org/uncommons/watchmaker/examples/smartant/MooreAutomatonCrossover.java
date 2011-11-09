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
package org.uncommons.watchmaker.examples.smartant;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Alexander Buslaev
 */
public class MooreAutomatonCrossover extends AbstractCrossover<MooreAutomaton> {

    public MooreAutomatonCrossover(int crossoverPoints,
                                   Probability crossoverProbability) {
        super(crossoverPoints, crossoverProbability);
    }

    public List<MooreAutomaton> mate(MooreAutomaton parent1,
                                     MooreAutomaton parent2,
                                     int numberOfCrossoverPoints,
                                     Random rng) {


        List<MooreAutomaton> offspring = new ArrayList<MooreAutomaton>(2);

        MooreAutomaton offspring1 = parent1;
        MooreAutomaton offspring2 = parent2;
        for (int i = 0; i < numberOfCrossoverPoints; i++) {


            int random1 = rng.nextInt(offspring1.getSize());
            int random2 = rng.nextInt(offspring2.getSize());
            MooreNode o1 = offspring1.getNode(random1);
            MooreNode o2 = offspring2.getNode(random2);
            int rand = rng.nextInt(4);
            int o1t = o1.getTransitionTrue();
            int o2t = o2.getTransitionTrue();
            int o1f = o1.getTransitionFalse();
            int o2f = o2.getTransitionFalse();
            char o1a = o1.getAction();
            char o2a = o2.getAction();

            switch (rand) {
                case (0):
                    o1.setTransitionTrue(o2t);
                    o2.setTransitionTrue(o1t);
                    break;
                case (1):
                    o1.setTransitionFalse(o2f);
                    o2.setTransitionTrue(o1t);
                    break;
                case (2):
                    o1.setTransitionFalse(o2f);
                    o2.setTransitionFalse(o1f);
                    break;
                case (3):
                    o1.setTransitionTrue(o2t);
                    o2.setTransitionFalse(o1f);
                    break;
                /*              case (4):
              o1.setTransitionTrue(o2f);
              o2.setTransitionTrue(o1f);
              break;
          case (5):
              o1.setTransitionFalse(o2t);
              o2.setTransitionTrue(o1f);
              break;
          case (6):
              o1.setTransitionFalse(o2t);
              o2.setTransitionFalse(o1t);
              break;
          case (7):
              o1.setTransitionTrue(o2f);
              o2.setTransitionFalse(o1t);
              break;  */
            }
            if (rng.nextBoolean()) {
                o1.setAction(o2a);
                o2.setAction(o1a);
            }
            offspring1.addNode(random1, o1);
            offspring2.addNode(random2, o2);
        }

        if (rng.nextBoolean()) {
            int s1a = offspring1.getStart();
            int s2a = offspring2.getStart();
            offspring1.setStart(s2a);
            offspring2.setStart(s1a);
        }

        offspring.add(offspring1);
        offspring.add(offspring2);

        return offspring;
    }


}
