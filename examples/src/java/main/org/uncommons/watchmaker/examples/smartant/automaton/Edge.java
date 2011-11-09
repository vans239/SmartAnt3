package org.uncommons.watchmaker.examples.smartant.automaton;

import java.awt.*;

/**
 * @author Yulia Chebotareva
 */
public class Edge {

    private Color defaultColor = Color.BLACK;
    private Color defaultLabelColor = Color.BLACK;
    private Font defaultLabelFont = new Font("SansSerif", Font.PLAIN, 11);
    private int defaultWidth = 1;

    private String label;

    public Edge() {
        label = "";
    }

    public Edge(String name) {
        this();
        this.label = name;
    }

    Color getColor() {
        return defaultColor;
    }

    Color getLabelColor() {
        return defaultLabelColor;
    }

    Font getLabelFont() {
        return defaultLabelFont;
    }

    int getWidth() {
        return defaultWidth;
    }

    String getLabel() {
        return label;
    }

    void setLabel(String label) {
        this.label = label;
    }

    public static double GetAngle(Point source, Point sink) {
        double angle = 0;
        if (((sink.getX() - source.getX()) == 0) && ((sink.getY() - source.getY()) > 0)) {
            angle = 90;
        }
        if (((sink.getX() - source.getX()) == 0) && ((sink.getY() - source.getY()) < 0)) {
            angle = -90;
        }
        if ((sink.getX() - source.getX()) > 0) {
            angle = Math.atan((1 * (sink.getY() - source.getY())) /
                    (1 * (sink.getX() - source.getX()))) * 180 / Math.PI;
        }
        if (((sink.getX() - source.getX()) < 0) && ((sink.getY() - source.getY()) >= 0)) {
            angle = Math.atan((1 * (sink.getY() - source.getY())) /
                    (1 * (sink.getX() - source.getX()))) * 180 / Math.PI + 180;
        }
        if (((sink.getX() - source.getX()) < 0) && ((sink.getY() - source.getY()) < 0)) {
            angle = Math.atan((1 * (sink.getY() - source.getY())) /
                    (1 * (sink.getX() - source.getX()))) * 180 / Math.PI - 180;
        }
        return angle;
    }
}
