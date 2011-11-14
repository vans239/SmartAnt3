package org.uncommons.watchmaker.examples.smartant3.mealy;
/*
 *  Date: 10.11.11
 *  Time: 09:15
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.uncommons.watchmaker.examples.smartant3.Properties;

import java.util.Arrays;
import java.util.Random;

public class ShortcutMealyNode {
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

        public String toString() {
            if (this == RIGHT) {
                return "RIGHT";
            }
            if (this == LEFT) {
                return "LEFT";
            }
            return "MIDDLE";
        }
    }

    public int countOfImpacts = (int) Math.pow( 2,  Properties.countOfUsedPredicts);
    private int transitions[] = new int[countOfImpacts];
    private Action actions[] = new Action[countOfImpacts];
    private boolean predicts[];

    private ShortcutMealyNode() {

    }

    public ShortcutMealyNode(Random random) {
        generateRandomPredicts(random);
    }

    public ShortcutMealyNode clone() {
        ShortcutMealyNode mealyNode = new ShortcutMealyNode();
        mealyNode.predicts = this.predicts.clone();
        mealyNode.actions = this.actions.clone();
        mealyNode.transitions = this.transitions.clone();
        return mealyNode;
    }

    public void setAction(int impact, Action action) {
        actions[impact] = action;
    }

    public void setNextNode(int impact, int nextNode) {
        assert (nextNode < transitions.length) : "Error!!!";
        transitions[impact] = nextNode;
    }

    public int getNextNode(int impact) {
        impact = getPredictImpact(impact);
        return transitions[impact];
    }

    public Action getAction(int impact) {
        impact = getPredictImpact(impact);
        return actions[impact];
    }

    public int getCountOfImpacts(){
     return countOfImpacts;
    }
    //todo rewrite this method
    public void generateRandomPredicts(Random random) {
        predicts = new boolean[Properties.countOfPredicts];
        int i = 0;
        while (i < Properties.countOfUsedPredicts) {
            int j = random.nextInt(predicts.length);
            if (!predicts[j]) {
                predicts[j] = true;
                ++i;
            }
        }
    }
    public int getPredictImpact(int impact){
        int k = 128;
        int result = 0;
        for(int i = 0; i < predicts.length; ++i){
            if(predicts[i] && ((impact & k) != 0)){
               result = 2 * result + 1;
            }
            k /= 2;
        }
        return result;
    }
}
