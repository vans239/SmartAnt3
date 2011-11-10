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

import org.uncommons.watchmaker.examples.smartant3.Moore.MooreAutomaton;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alexander Buslaev
 */
class AntRenderer extends JPanel {
    private final boolean field[][];
    private final int fieldSize = 32;

    private AntMover mover;

    public AntRenderer(MooreAutomaton m) {
        setPreferredSize(new Dimension(400, 400));
        AntMover.generateRandomField(0.11);
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

    public void setAutomaton(MooreAutomaton a) {
        mover = new AntMover(a);
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < fieldSize + 1; i++) {
            g.drawLine(0, i * (getHeight() - 1) / fieldSize, getWidth(), i * (getHeight() - 1) / fieldSize);
        }
        for (int i = 0; i < fieldSize + 1; i++) {
            g.drawLine(i * (getWidth() - 1) / fieldSize, 0, i * (getWidth() - 1) / fieldSize, getHeight());
        }
        final boolean food[][];
        if (mover != null) {
            food = mover.getCurrentField();
        } else {
            food = mover.getStartField();
        }

        Color temp = g.getColor();
        g.setColor(Color.BLACK);
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (food[i][j]) {
                    g.fillRect(j * getWidth() / fieldSize, i * getHeight() / fieldSize,
                               (getWidth() + fieldSize - 1) / fieldSize, (getHeight() + fieldSize - 1) / fieldSize);
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
                paintLeft(g, cell.y * getWidth() / fieldSize, cell.x * getHeight() / fieldSize, getWidth() / fieldSize,
                          getHeight() / fieldSize);
                break;
            case RIGHT:
                paintRight(g, cell.y * getWidth() / fieldSize, cell.x * getHeight() / fieldSize, getWidth() / fieldSize,
                           getHeight() / fieldSize);
                break;
            case TOP:
                paintTop(g, cell.y * getWidth() / fieldSize, cell.x * getHeight() / fieldSize, getWidth() / fieldSize,
                         getHeight() / fieldSize);
                break;
            case BOTTOM:
                paintBottom(g, cell.y * getWidth() / fieldSize, cell.x * getHeight() / fieldSize,
                            getWidth() / fieldSize, getHeight() / fieldSize);
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
