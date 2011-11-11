package org.uncommons.watchmaker.examples.smartant3.mealy;
/*
 *  Date: 10.11.11
 *  Time: 18:07
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.uncommons.watchmaker.examples.smartant3.AntMover;
import org.uncommons.watchmaker.examples.smartant3.AntMover.Direction;
import org.uncommons.watchmaker.examples.smartant3.AntMover.Cell;

public class MealyImpact {
    public static int impact(AntMover mover) {
        Direction direction = mover.getDirection();
        boolean impacts[] = null;
        switch (direction) {
            case LEFT:
                impacts = impactsLeft(mover.getCurrentField(), mover.getCell());
                break;
            case RIGHT:
                impacts = impactsRight(mover.getCurrentField(), mover.getCell());
                break;
            case BOTTOM:
                impacts = impactsBottom(mover.getCurrentField(), mover.getCell());
                break;
            case TOP:
                impacts = impactsTop(mover.getCurrentField(), mover.getCell());
                break;
        }
        int impact = (impacts[0] ? 1 : 0);
        impact = 2 * impact +  (impacts[1] ? 1 : 0);
        impact = 2 * impact +  (impacts[2] ? 1 : 0);
        impact = 2 * impact +  (impacts[3] ? 1 : 0);
        impact = 2 * impact +  (impacts[4] ? 1 : 0);
        impact = 2 * impact +  (impacts[5] ? 1 : 0);
        impact = 2 * impact +  (impacts[6] ? 1 : 0);
        impact = 2 * impact +  (impacts[7] ? 1 : 0);
        return impact;
    }

    private static boolean[] impactsRight(boolean field[][], Cell cell) {
        boolean impacts[] = new boolean[8];
        int size = field.length;
        impacts[0] = field[(cell.x - 2 + size) % size][(cell.y + size) % size];
        impacts[1] = field[(cell.x - 1 + size) % size][(cell.y + size) % size];
        impacts[2] = field[(cell.x + 1 + size) % size][(cell.y + size) % size];
        impacts[3] = field[(cell.x + 2 + size) % size][(cell.y + size) % size];
        impacts[4] = field[(cell.x - 1 + size) % size][(cell.y + 1 + size) % size];
        impacts[5] = field[(cell.x + size) % size][(cell.y + 1 + size) % size];
        impacts[6] = field[(cell.x + 1 + size) % size][(cell.y + 1 + size) % size];
        impacts[7] = field[(cell.x + size) % size][(cell.y + 2 + size) % size];
        return impacts;
    }

    private static boolean[] impactsLeft(boolean field[][], Cell cell) {
        boolean impacts[] = new boolean[8];
        int size = field.length;
        impacts[0] = field[(cell.x + 2 + size) % size][(cell.y + size) % size];
        impacts[1] = field[(cell.x + 1 + size) % size][(cell.y + size) % size];
        impacts[2] = field[(cell.x - 1 + size) % size][(cell.y + size) % size];
        impacts[3] = field[(cell.x - 2 + size) % size][(cell.y + size) % size];
        impacts[4] = field[(cell.x + 1 + size) % size][(cell.y - 1 + size) % size];
        impacts[5] = field[(cell.x + size) % size][(cell.y - 1 + size) % size];
        impacts[6] = field[(cell.x - 1 + size) % size][(cell.y - 1 + size) % size];
        impacts[7] = field[(cell.x + size) % size][(cell.y - 2 + size) % size];
        return impacts;
    }

    private static boolean[] impactsTop(boolean field[][], Cell cell) {
        boolean impacts[] = new boolean[8];
        int size = field.length;
        impacts[0] = field[(cell.x + size) % size][(cell.y + 2 + size) % size];
        impacts[1] = field[(cell.x + size) % size][(cell.y + 1 + size) % size];
        impacts[2] = field[(cell.x + size) % size][(cell.y - 1 + size) % size];
        impacts[3] = field[(cell.x + size) % size][(cell.y - 2 + size) % size];
        impacts[4] = field[(cell.x + 1 + size) % size][(cell.y + 1 + size) % size];
        impacts[5] = field[(cell.x + 1 + size) % size][(cell.y + size) % size];
        impacts[6] = field[(cell.x + 1 + size) % size][(cell.y - 1 + size) % size];
        impacts[7] = field[(cell.x + 2 + size) % size][(cell.y + size) % size];
        return impacts;
    }

    private static boolean[] impactsBottom(boolean field[][], Cell cell) {
        boolean impacts[] = new boolean[8];
        int size = field.length;
        impacts[0] = field[(cell.x + size) % size][(cell.y - 2 + size) % size];
        impacts[1] = field[(cell.x + size) % size][(cell.y - 1 + size) % size];
        impacts[2] = field[(cell.x + size) % size][(cell.y + 1 + size) % size];
        impacts[3] = field[(cell.x + size) % size][(cell.y + 2 + size) % size];
        impacts[4] = field[(cell.x - 1 + size) % size][(cell.y - 1 + size) % size];
        impacts[5] = field[(cell.x - 1 + size) % size][(cell.y + size) % size];
        impacts[6] = field[(cell.x - 1 + size) % size][(cell.y + 1 + size) % size];
        impacts[7] = field[(cell.x - 2 + size) % size][(cell.y + size) % size];
        return impacts;
    }
}
