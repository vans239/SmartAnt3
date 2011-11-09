package org.uncommons.watchmaker.examples.smartant.automaton;

import java.awt.*;
import java.util.*;

/**
 * @author Yulia Chebotareva
 */
public class Graph extends TreeMap<InternalNode, HashMap<InternalNode, LinkedList<Edge>>> {

    private HashSet<Integer> ids;

    public Graph() {
        super();
        ids = new HashSet<Integer>();
    }

    public int getId() {
        int id = size();
        if (ids.contains(id) || id == Integer.MAX_VALUE) {
            for (int i = id - 1; i >= 0; i--) {
                if (!ids.contains(i)) {
                    return i;
                }
            }
        }
        return id;
    }

    public void addNode(InternalNode internalNode) {
        put(internalNode, new HashMap<InternalNode, LinkedList<Edge>>());
        ids.add(internalNode.getId());
    }

    public void addEdge(InternalNode source, InternalNode sink, Edge edge) {
        if (containsKey(source) && containsKey(sink)) {
            if (!get(source).containsKey(sink)) {
                get(source).put(sink, new LinkedList<Edge>());
            }
            get(source).get(sink).add(edge);
        }
    }

    public void removeNode(InternalNode internalNode) {
        if (containsKey(internalNode)) {
            for (InternalNode temp : keySet()) {
                if (get(internalNode).containsKey(temp)) {
                    get(internalNode).get(temp).clear();
                }
                if (get(temp).containsKey(internalNode)) {
                    get(temp).get(internalNode).clear();
                }
            }
            ids.remove(internalNode.getId());
            remove(internalNode);
        }
    }

    public void removeEdge(InternalNode source, InternalNode sink, Edge edge) {
        if (containsKey(source) && containsKey(sink)) {
            get(source).get(sink).remove(edge);
        }
    }

    public Set<InternalNode> getNeighbours(InternalNode internalNode) {
        return get(internalNode).keySet();
    }

    public InternalNode nodeContains(Point p, double eps) {
        int foundedId = -1;
        InternalNode result = null;
        for (InternalNode temp : keySet()) {
            if (temp.contains(p, eps) && (foundedId < temp.getId())) {
                foundedId = temp.getId();
                result = temp;
            }
        }
        return result;
    }

    public boolean checkIfDoubleSideEdge(InternalNode node1, InternalNode node2) {
        if (containsKey(node1) && get(node1).containsKey(node2) && !get(node1).get(node2).isEmpty()) {
            if (containsKey(node2) && get(node2).containsKey(node1) && !get(node2).get(node1).isEmpty()) {
                return true;
            }
        }
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

    public EdgePair edgeContains(Point p, int eps, double edgeEps) {
        //boolean found = false;
        for (Iterator it = keySet().iterator(); it.hasNext(); ) {
            InternalNode current = (InternalNode) it.next();
            for (Iterator ei = getNeighbours(current).iterator(); ei.hasNext(); ) {

                InternalNode neighbour = (InternalNode) ei.next();
                if (checkIfDoubleSideEdge(current, neighbour)) {
                    //if (current.getId() < neighbour.getId()) {
                    int a = current.getX() + current.getWidth() / 2;
                    int b = current.getY() + current.getHeight() / 2;

                    double angle = Edge.GetAngle(new Point(current.getX(), current.getY()), new Point(neighbour.getX(), neighbour.getY()));
                    if (angle < 0) {
                        angle += 360;
                    } else if (angle > 360) {
                        angle -= 360;
                    }

                    angle = -angle * Math.PI / 180;

                    double x1 = (p.getX() - a) * Math.cos(-angle) + (p.getY() - b) * Math.sin(-angle);
                    double y1 = -(p.getX() - a) * Math.sin(-angle) + (p.getY() - b) * Math.cos(-angle);

                    double d = Math.sqrt(Math.pow(current.getX() - neighbour.getX(), 2) + Math.pow(current.getY() - neighbour.getY(), 2));
                    int a1 = (int) (d - current.getWidth()) / 2;
                    int b1 = DocumentPanel.ELLIPSE_HEIGHT / 2;

                    if (Math.abs((x1 - a1 - current.getWidth() / 2) * (x1 - a1 - current.getWidth() / 2) / (a1 * a1) + y1 * y1 / (b1 * b1) - 1) < edgeEps && y1 > 0) {
                        return new EdgePair(current, neighbour);
                    }
                } else {
                    double a = current.getY() + current.getHeight() / 2 - neighbour.getY() - neighbour.getHeight() / 2;
                    double b = neighbour.getX() + neighbour.getWidth() / 2 - current.getX() - current.getWidth() / 2;
                    double c = (current.getX() + current.getWidth() / 2) * (neighbour.getY() + neighbour.getHeight() / 2) - (current.getY() + current.getHeight() / 2) * (neighbour.getX() + neighbour.getWidth() / 2);
                    double d1 = (a * p.getX() + b * p.getY() + c) / Math.sqrt(a * a + b * b);
                    int x1 = Math.min(current.getX() + current.getWidth() / 2, neighbour.getX() + neighbour.getWidth() / 2) - eps;
                    int y1 = Math.min(current.getY() + current.getHeight() / 2, neighbour.getY() + neighbour.getHeight() / 2) - eps;
                    int x2 = Math.max(current.getX() + current.getWidth() / 2, neighbour.getX() + neighbour.getWidth() / 2) + eps;
                    int y2 = Math.max(current.getY() + current.getHeight() / 2, neighbour.getY() + neighbour.getHeight() / 2) + eps;
                    if ((Math.abs(d1) <= edgeEps) && (p.getX() >= x1) && (p.getX() <= x2) && (p.getY() >= y1) && (p.getY() <= y2)) {
                        return new EdgePair(current, neighbour);
                    }
                }
            }
        }
        return null;
    }

    public void addLoop(InternalNode source, Loop loop) {
        if (containsKey(source)) {
            if (!get(source).containsKey(source)) {
                get(source).put(source, new LinkedList<Edge>());
            }
            get(source).get(source).add(loop);
        }
    }

    public EdgePair loopContains(Point p, double deps) {
        boolean found = false;
        for (Iterator it = keySet().iterator(); it.hasNext() && !found; ) {
            InternalNode current = (InternalNode) it.next();
            for (Iterator ei = getNeighbours(current).iterator(); ei.hasNext() && !found; ) {
                InternalNode neighbour = (InternalNode) ei.next();
                if (neighbour.equals(current)) {
                    if (Loop.Contains(current, p, deps)) return new EdgePair(current, current);
                }
            }
        }
        return null;
    }

    public InternalNode getNode(int index) {
        for (InternalNode temp : keySet()) {
            if (temp.getId() == index) {
                return temp;
            }
        }
        return null;
    }
}
