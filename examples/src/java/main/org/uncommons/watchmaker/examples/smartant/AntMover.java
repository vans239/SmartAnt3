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

/**
 * Moving algorithm for ant
 *
 * @author Alexander Buslaev
 */
public class AntMover {

    private static final int STEPS = 200;
    public static String[] stringmap = {

            "_**********_____________________",
            "__________*_____________________",
            "__________*_____________________",
            "__________*_____________________",
            "__________*____*________________",
            "****______*______________*******",
            "___*______*_____________*_______",
            "___*______*___*_________*_______",
            "___*______*_*___________*_______",
            "___*______*_____________*_______",
            "___********_____________*_______",
            "___________*_______*****________",
            "______________*___*_____________",
            "__________________*_____________",
            "_______________*__*_____________",
            "____________*_____*_____________",
            "__________________*_____________",
            "___________*______*_____________",
            "________*_______________________",
            "________________________________",
            "__________________*_____________",
            "_______*__________*_____________",
            "_____*____________*_____________",
            "__________________*_____________",
            "____*_____________*_____________",
            "____*_____________*_____________",
            "____*___________________________",
            "____*__****_******______________",
            "________________________________",
            "________________________________",
            "________________________________",
            "________________________________"

    };

    private MooreAutomaton automaton;
    private boolean[][] field;
    private Cell currentCell;
    private Direction direction;
    private int state;
    private int done;
    private int eaten;
    private int stepped;

    public static final char[] ACTION_VALUES = {'L', 'R', 'M'};

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
                    return new Cell(x, (y + 31) % 32);
                case RIGHT:
                    return new Cell(x, (y + 1) % 32);
                case TOP:
                    return new Cell((x + 31) % 32, y);
                case BOTTOM:
                    return new Cell((x + 1) % 32, y);
            }
            return null;
        }

    }


    public AntMover(MooreAutomaton a) {
        automaton = a;
        field = getField();
        state = automaton.getStart();
        currentCell = new Cell(0, 0);
        direction = Direction.RIGHT;
        state = automaton.getStart();
        eaten = 0;
        stepped = 0;
    }

    public static boolean[][] getField() {
        boolean[][] field = new boolean[32][32];
        for (int i = 0; i < 32; i++) {
            String s = stringmap[i];
            for (int j = 0; j < 32; j++) {
                field[i][j] = s.charAt(j) == '*';
            }
        }
        return field;
    }

    public boolean[][] getCurrentField() {
        return field;
    }

    public int getState() {
        return state;
    }

    public void DoStep(int i) {
        Cell foodCell = new Cell(currentCell.x, currentCell.y).next(direction);
        if (field[foodCell.x][foodCell.y]) {
            state = automaton.getNode(state).getTransitionTrue();
            char action = automaton.getNode(state).getAction();
            //          System.out.println(direction);
            switch (action) {
                case ('M'):
                    currentCell = currentCell.next(direction);
                    if (field[currentCell.x][currentCell.y]) eaten++;
                    //       System.out.println(eaten);
                    done = i;
                    field[currentCell.x][currentCell.y] = false;
                    break;
                case ('L'):
                    direction = direction.left();
                    break;
                case ('R'):
                    direction = direction.right();
                    break;
            }
        } else {
            state = automaton.getNode(state).getTransitionFalse();
            char action = automaton.getNode(state).getAction();
            switch (action) {
                case ('M'):
                    currentCell = currentCell.next(direction);
                    break;
                case ('L'):
                    direction = direction.left();
                    break;
                case ('R'):
                    direction = direction.right();
                    break;
            }
        }
    }


    public double MoveAnt() {
        for (int i = 0; i < STEPS; ++i) {
            DoStep(i);
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
        DoStep(stepped++);
    }

    public void reset() {
        currentCell.x = 0;
        currentCell.y = 0;
        direction = Direction.RIGHT;
    }
}
