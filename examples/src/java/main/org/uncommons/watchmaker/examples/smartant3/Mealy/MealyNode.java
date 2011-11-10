package org.uncommons.watchmaker.examples.smartant3.Mealy;
/*
 *  Date: 10.11.11
 *  Time: 09:15
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import java.util.Random;

public class MealyNode {
    public static final int COUNTOFIMPACTS = 256;

    public static enum Action {
        RIGHT,
        LEFT,
        MIDDLE;

        public static Action getRandomAction(Random random) {
            int countOfActions = 3;
            // todo if actions will be more: Action.values();
            int i = random.nextInt(countOfActions);
            if (i == 0) {
                return Action.LEFT;
            } else if (i == 1) {
                return Action.MIDDLE;
            }
            return Action.RIGHT;
        }
    }

    private int transitions[] = new int[COUNTOFIMPACTS];
    private Action actions[] = new Action[COUNTOFIMPACTS];

    public void setAction(int impact, Action action) {
        actions[impact] = action;
    }

    public void setNextNode(int impact, int nextNode) {
        transitions[impact] = nextNode;
    }

    public int getNextNode(int impact) {
        return transitions[impact];
    }

    public Action getAction(int impact) {
        return actions[impact];
    }

}
