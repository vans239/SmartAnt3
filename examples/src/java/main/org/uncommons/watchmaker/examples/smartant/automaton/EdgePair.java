package org.uncommons.watchmaker.examples.smartant.automaton;

/**
 * @author Yulia Chebotareva
 */
public class EdgePair {

    InternalNode source, sink;

    EdgePair(InternalNode source, InternalNode sink) {
        this.source = source;
        this.sink = sink;
    }

    InternalNode getSource() {
        return this.source;
    }

    InternalNode getSink() {
        return this.sink;
    }
}
