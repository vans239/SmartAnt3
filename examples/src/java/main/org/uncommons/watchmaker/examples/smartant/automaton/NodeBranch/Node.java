package org.uncommons.watchmaker.examples.smartant.automaton.NodeBranch;

/**
 * @author Yulia Chebotareva
 */
public class Node {
    private String name;
    private Branch branches[];
    private boolean start;

    public Node(String name, Branch[] branches, boolean start) {
        this.name = name;
        this.branches = branches;
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Branch[] getBranches() {
        return branches;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isStart() {
        return start;
    }
}
