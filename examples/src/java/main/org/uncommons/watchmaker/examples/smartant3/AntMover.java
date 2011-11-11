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
package org.uncommons.watchmaker.examples.smartant3;

import org.uncommons.watchmaker.examples.smartant3.mealy.MealyImpact;
import org.uncommons.watchmaker.examples.smartant3.mealy.MealyMachine;
import org.uncommons.watchmaker.examples.smartant3.mealy.MealyNode.Action;

import java.util.Random;

/**
 * Moving algorithm for ant
 *
 * @author Alexander Buslaev
 */
public class AntMover {
    private static final int SIZE = Properties.SIZE;
    private static final int STEPS = Properties.STEPS;

    private boolean initField[][];

    private MealyMachine machine;
    private boolean[][] field;
    private Cell currentCell;
    private Direction direction;
    private int state;
    private int done;
    private int eaten;
    private int stepped;

    public enum Direction {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM;

        public Direction left() {
            switch (this) {
                case LEFT:
                    return BOTTOM;
                case BOTTOM:
                    return RIGHT;
                case RIGHT:
                    return TOP;
                case TOP:
                    return LEFT;
            }
            return null;
        }

        public Direction right() {
            switch (this) {
                case LEFT:
                    return TOP;
                case BOTTOM:
                    return LEFT;
                case RIGHT:
                    return BOTTOM;
                case TOP:
                    return RIGHT;
            }
            return null;
        }
    }

    public static class Cell {
        public int x;
        public int y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Cell next(Direction d) {
            switch (d) {
                case LEFT:
                    return new Cell((x - 1 + SIZE) % SIZE, y);
                case RIGHT:
                    return new Cell((x + 1) % SIZE, y);
                case TOP:
                    return new Cell(x, (y - 1 + SIZE) % SIZE);
                case BOTTOM:
                    return new Cell(x, (y + 1) % SIZE);
            }
            return null;
        }
    }

    public AntMover(MealyMachine machine, boolean field[][]) {
        this.machine = machine;
        state = machine.getStart();
        currentCell = new Cell(0, 0);
        direction = Direction.RIGHT;
        eaten = 0;
        stepped = 0;
        initField = copyField(field);
        this.field = copyField(field);
    }

    public boolean[][] getCurrentField() {
        return field;
    }

    public int getState() {
        return state;
    }

    public void doStep(int i) {
        int impact = MealyImpact.impact(this);
        Action action = machine.getNode(state).getAction(impact);
        int transition = machine.getNode(state).getNextNode(impact);

        switch (action) {
            case MIDDLE:
                currentCell = currentCell.next(direction);
                break;
            case LEFT:
                direction = direction.left();
                break;
            case RIGHT:
                direction = direction.right();
                break;
        }
        if (field[currentCell.y][currentCell.x]) {
            field[currentCell.y][currentCell.x] = false;
            ++eaten;
            done = i;
        }
        state = transition;
    }

    public double moveAnt() {
        for (int i = 0; i < STEPS; ++i) {
            doStep(i);
        }

        return (eaten + ((double) STEPS - (double) done) / (double) STEPS);

    }

    public Cell getCell() {
        return currentCell;
    }

    public Direction getDirection() {
        return direction;
    }

    public void move() {
        doStep(stepped++);
    }

    public void reset() {
        currentCell.x = 0;
        currentCell.y = 0;
        direction = Direction.RIGHT;
        this.field = copyField(initField);
    }


    /*public static void generateRandomField(double mu) {
        // todo
        Random random = new Random();
        initField = new boolean[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (random.nextDouble() < mu) {
                    initField[i][j] = true;
                }
            }
        }
    }*/

    public boolean[][] getStartField() {
        return initField;
    }

    private static boolean[][] copyField(boolean field[][]) {
        boolean result[][] = new boolean[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                result[i][j] = field[i][j];
            }
        }
        return result;
    }
}
