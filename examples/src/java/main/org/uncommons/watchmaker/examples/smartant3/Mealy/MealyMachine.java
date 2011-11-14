package org.uncommons.watchmaker.examples.smartant3.mealy;
/*
 *  Date: 10.11.11
 *  Time: 09:15
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.uncommons.watchmaker.examples.smartant3.Properties;

public class MealyMachine{
    public static final int COUNTOFIMPACTS = Properties.COUNTOFIMPACTS;
    private int startState;
    private ShortcutMealyNode states[];

    public MealyMachine(int states, int start) {
        this.startState = start;
        this.states = new ShortcutMealyNode[states];
    }

    public MealyMachine clone() {
        MealyMachine machine = new MealyMachine(this.getSize(), this.getStart());
        for (int i = 0; i < this.getSize(); ++i) {
            machine.states[i] = new ShortcutMealyNode();
            for (int j = 0; j < COUNTOFIMPACTS; ++j) {
                ShortcutMealyNode node = this.getNode(i);
                machine.states[i].setAction(j, node.getAction(j));
                machine.states[i].setNextNode(j, node.getNextNode(j));
            }
        }
        return machine;
    }

    public void addNode(int pos, ShortcutMealyNode node) {
        states[pos] = node;
    }

    public ShortcutMealyNode getNode(int pos) {
        return states[pos];
    }

    public int getStart() {
        return startState;
    }

    public void setStart(int startState) {
        this.startState = startState;
    }

    public int getSize() {
        return states.length;
    }
}
