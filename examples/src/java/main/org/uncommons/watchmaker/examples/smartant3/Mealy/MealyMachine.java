package org.uncommons.watchmaker.examples.smartant3.mealy;
/*
 *  Date: 10.11.11
 *  Time: 09:15
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.uncommons.watchmaker.examples.smartant3.Properties;
import org.uncommons.watchmaker.examples.smartant3.mealy.MealyNode;

public class MealyMachine{
    public static final int COUNTOFIMPACTS = Properties.COUNTOFIMPACTS;
    private int startState;
    private MealyNode states[];

    public MealyMachine(int states, int start) {
        this.startState = start;
        this.states = new MealyNode[states];
    }

    public MealyMachine(MealyMachine machine) {
        this.startState = machine.getStart();
        states = new MealyNode[machine.getSize()];
        for (int i = 0; i < machine.getSize(); ++i) {
            states[i] = new MealyNode();
            for (int j = 0; j < COUNTOFIMPACTS; ++j) {
                MealyNode node = machine.getNode(i);
                states[i].setAction(j, node.getAction(j));
                states[i].setNextNode(j, node.getNextNode(j));
            }
        }
    }

    public void addNode(int pos, MealyNode node) {
        states[pos] = node;
    }

    public MealyNode getNode(int pos) {
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
