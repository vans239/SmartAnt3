package org.uncommons.watchmaker.examples.smartant3.mealy;
/*
 *  Date: 10.11.11
 *  Time: 09:15
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */


public class MealyMachine{
    private int startState;
    private ShortcutMealyNode states[];

    public MealyMachine(int states, int start) {
        this.startState = start;
        this.states = new ShortcutMealyNode[states];
    }

    public MealyMachine clone() {
        MealyMachine machine = new MealyMachine(this.getSize(), this.getStart());
        for (int i = 0; i < this.getSize(); ++i) {
            machine.states[i] = this.getNode(i).clone();

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
