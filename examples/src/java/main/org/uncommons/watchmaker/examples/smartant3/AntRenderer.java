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

import org.uncommons.watchmaker.examples.smartant3.mealy.MealyMachine;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alexander Buslaev
 */
class AntRenderer extends JPanel {
    private final boolean field[][];
    private static final int SIZE = Properties.SIZE;

    private AntMover mover;

    public AntRenderer(MealyMachine m) {
        setPreferredSize(new Dimension(400, 400));
        AntMover.generateRandomField(Properties.mu);
        mover = new AntMover(m);
        field = mover.getCurrentField();
        setLayout(null);
    }

    public AntMover getMover() {
        return mover;
    }

    public void reset() {
        setLayout(null);
        mover.reset();
        repaint();
    }

    public void setMachine(MealyMachine a) {
        mover = new AntMover(a);
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < SIZE + 1; i++) {
            g.drawLine(0, i * (getHeight() - 1) / SIZE, getWidth(), i * (getHeight() - 1) / SIZE);
        }
        for (int i = 0; i < SIZE + 1; i++) {
            g.drawLine(i * (getWidth() - 1) / SIZE, 0, i * (getWidth() - 1) / SIZE, getHeight());
        }
        final boolean food[][];
        if (mover != null) {
            food = mover.getCurrentField();
        } else {
            food = mover.getStartField();
        }

        Color temp = g.getColor();
        g.setColor(Color.BLACK);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (food[i][j]) {
                    g.fillRect(j * getWidth() / SIZE, i * getHeight() / SIZE,
                               (getWidth() + SIZE - 1) / SIZE, (getHeight() + SIZE - 1) / SIZE);
                }
            }
        }

        g.setColor(temp);
        AntMover.Cell cell;
        AntMover.Direction dir;
        if (mover != null) {
            cell = mover.getCell();
            dir = mover.getDirection();
        } else {
            cell = new AntMover.Cell(0, 0);
            dir = AntMover.Direction.RIGHT;
        }

        switch (dir) {
            case LEFT:
                paintLeft(g, cell.y * getWidth() / SIZE, cell.x * getHeight() / SIZE, getWidth() / SIZE,
                          getHeight() / SIZE);
                break;
            case RIGHT:
                paintRight(g, cell.y * getWidth() / SIZE, cell.x * getHeight() / SIZE, getWidth() / SIZE,
                           getHeight() / SIZE);
                break;
            case TOP:
                paintTop(g, cell.y * getWidth() / SIZE, cell.x * getHeight() / SIZE, getWidth() / SIZE,
                         getHeight() / SIZE);
                break;
            case BOTTOM:
                paintBottom(g, cell.y * getWidth() / SIZE, cell.x * getHeight() / SIZE,
                            getWidth() / SIZE, getHeight() / SIZE);
                break;
        }
    }

    public void paintRight(Graphics g, int x, int y, int width, int height) {
        Color temp = g.getColor();
        g.setColor(Color.RED);
        g.drawLine(x, y, x + width, y + height / 2);
        g.drawLine(x + width, y + height / 2, x, y + height);
        g.setColor(temp);
    }

    public void paintLeft(Graphics g, int x, int y, int width, int height) {
        Color temp = g.getColor();
        g.setColor(Color.RED);
        g.drawLine(x + width, y, x, y + height / 2);
        g.drawLine(x, y + height / 2, x + width, y + height);
        g.setColor(temp);
    }

    public void paintTop(Graphics g, int x, int y, int width, int height) {
        Color temp = g.getColor();
        g.setColor(Color.RED);
        g.drawLine(x, y + height / 2, x + width / 2, y);
        g.drawLine(x + width / 2, y, x + width, y + height / 2);
        g.setColor(temp);
    }

    public void paintBottom(Graphics g, int x, int y, int width, int height) {
        Color temp = g.getColor();
        g.setColor(Color.RED);
        g.drawLine(x, y + height / 2, x + width / 2, y + height);
        g.drawLine(x + width / 2, y + height, x + width, y + height / 2);
        g.setColor(temp);
    }

    public void move() {
        mover.move();
        repaint();
    }
}
