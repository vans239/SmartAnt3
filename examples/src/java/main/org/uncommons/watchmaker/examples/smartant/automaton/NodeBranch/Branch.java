package org.uncommons.watchmaker.examples.smartant.automaton.NodeBranch;

/**
 * @author Yulia Chebotareva
 */
public class Branch {
    private String name;

    private String destinationNodeName;

    public Branch(String name, String destinationNodeName) {
        this.name = name;
        this.destinationNodeName = destinationNodeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestinationNodeName() {
        return destinationNodeName;
    }

    public void setDestinationNodeName(String destinationNodeName) {
        this.destinationNodeName = destinationNodeName;
    }
}
