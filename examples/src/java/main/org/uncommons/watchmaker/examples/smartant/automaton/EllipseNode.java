package org.uncommons.watchmaker.examples.smartant.automaton;

import java.awt.*;

/**
 * @author Yulia Chebotareva
 */
public class EllipseNode extends InternalNode {

    public EllipseNode(int count, String label, int x, int y, boolean isStart) {
        super(count, label, x, y, isStart);
    }

    public EllipseNode(InternalNode internalNode) {
        super(internalNode);
    }

    public double getArrowRadius(Point p) {
        if ((x + defaultWidth / 2) == p.x) {
            return defaultHeight / 2;
        } else {
            double px = p.x - x - defaultWidth / 2;
            double py = p.y - y - defaultHeight / 2;
            double a = defaultWidth / 2;
            double b = defaultHeight / 2;
            double k = py / px;
            double xp = Math.sqrt(a * a * b * b / (b * b + k * k * a * a));
            double yp = k * xp;
            return Math.sqrt(xp * xp + yp * yp);
        }
    }

    public boolean contains(Point p, double eps) {
        return Math.pow((p.x - x - (double) defaultWidth / 2) / ((double) defaultWidth / 2), 2) +
                Math.pow((p.y - y - (double) defaultHeight / 2) / ((double) defaultHeight / 2), 2) - 1 < eps;
    }
}
