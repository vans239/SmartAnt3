package org.uncommons.watchmaker.examples.smartant.automaton;

import java.awt.*;

/**
 * @author Yulia Chebotareva
 */
public abstract class InternalNode implements Comparable<InternalNode> {

    protected static int defaultWidth = 40;
    protected static int defaultHeight = 40;
    protected static int defaultBorderWidth = 1;
    protected static Color defaultBgColor = new Color(255, 215, 0);
    protected static Color defaultBorderColor = Color.BLACK;
    protected static Color defaultFontColor = Color.BLACK;
    protected static Font defaultLabelFont = new Font("SansSerif", Font.PLAIN, 14);

    protected String label;

    protected int x;
    protected int y;

    protected boolean isStart;

    private int id;

    private Color c;

    protected InternalNode() {
        c = getBgColor();
    }

    protected InternalNode(int count, String label, int x, int y, boolean isStart) {
        this.id = count;
        this.label = label;
        this.x = x;
        this.y = y;
        this.isStart = isStart;
        c = getBgColor();
    }

    protected InternalNode(InternalNode internalNode) {
        id = internalNode.id;
        label = internalNode.label;
        isStart = internalNode.isStart;
        c = getBgColor();
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return defaultWidth;
    }

    public int getHeight() {
        return defaultHeight;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getBorderColor() {
        return defaultBorderColor;
    }

    public Color getBgColor() {
        return defaultBgColor;
    }

    public Color getColor() {
        return c;
    }

    public void setColor(Color c) {
        this.c = c;
    }


    public int getBorderWidth() {
        return defaultBorderWidth;
    }

    public Color getFontColor() {
        return defaultFontColor;
    }

    public Font getFont() {
        return defaultLabelFont;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean getIsStart() {
        return isStart;
    }

    public abstract double getArrowRadius(Point p);

    public abstract boolean contains(Point p, double eps);

    public int compareTo(InternalNode o) {
        return new Integer(id).compareTo(o.getId());
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }
}
