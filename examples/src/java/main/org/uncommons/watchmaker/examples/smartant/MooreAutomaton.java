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
package org.uncommons.watchmaker.examples.smartant;

/**
 * A Moore automaton to control Ant
 *
 * @author Alexander Buslaev
 */
public class MooreAutomaton {
    private MooreNode[] automaton;
    private int nodes;

    private int start;

    public MooreAutomaton(int nodes, int start) {
        this.start = start;
        automaton = new MooreNode[nodes];
        this.nodes = nodes;
    }

    public MooreAutomaton(MooreAutomaton automato) {
        this.start = automato.getStart();
        automaton = new MooreNode[automato.getSize()];
        for (int i = 0; i < automato.getSize(); ++i) {
            automaton[i] = new MooreNode(automato.getNode(i).getTransitionFalse(), automato.getNode(i).getTransitionTrue(), automato.getNode(i).getAction());
        }
        this.nodes = automato.getSize();
    }

    public void addNode(int pos, MooreNode node) {
        automaton[pos] = node;
    }

    public MooreNode getNode(int pos) {
        return automaton[pos];
    }

    public int getStart() {
        return start;
    }

    public void setStart(int s) {
        start = s;
    }

    public int getSize() {
        return nodes;
    }

    public String toString() {
        String a = "";
        for (int i = 0; i < nodes; ++i) {
            a = a + automaton[i].toString() + ' ';
        }
        return a;
    }

}
