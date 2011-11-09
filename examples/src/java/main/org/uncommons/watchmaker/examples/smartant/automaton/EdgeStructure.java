package org.uncommons.watchmaker.examples.smartant.automaton;

/**
 * @author Yulia Chebotareva
 */
public class EdgeStructure {
    int sourceId;
    int sinkId;
    String name;

    EdgeStructure(int sourceId, int sinkId, String name) {
        this.sourceId = sourceId;
        this.sinkId = sinkId;
        this.name = name;
    }

    int getSourceId() {
        return sourceId;
    }

    int getSinkId() {
        return sinkId;
    }

    String getName() {
        return name;
    }
}
