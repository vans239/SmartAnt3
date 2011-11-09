package org.uncommons.watchmaker.examples.smartant.automaton;

import java.awt.*;

/**
 * @author Yulia Chebotareva
 */
public class Loop extends Edge {

    public Loop(String destinationNodeName) {
        super(destinationNodeName);
    }

    public static boolean Contains(InternalNode internalNode, Point p, double eps) {
        double x = internalNode.getX() - 20 + (double) internalNode.getWidth() / 2;
        double y = internalNode.getY() - 30;
        double width = 40;
        double height = 55;

        return Math.abs(Math.pow((p.getX() - x - width / 2) / (width / 2), 2) +
                Math.pow((p.getY() - y - height / 2) / (height / 2), 2) - 1) < eps;
    }
}
