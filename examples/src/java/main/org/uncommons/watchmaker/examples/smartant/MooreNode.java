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
 * This is a node for Moore automaton.
 *
 * @author Alexander Buslaev
 */
public class MooreNode {
    public static final char[] ACTION_VALUES = {'R', 'M', 'L'};  // ant's actions: right, middle, left

    private int[] transitions = new int[2];
    private char action;

    public MooreNode(int falseto, int trueto, char a) {
        transitions[0] = falseto;
        transitions[1] = trueto;
        action = a;
    }

    public char getAction() {
        return action;
    }

    public String toString() {
        return action + " ";
    }

    public void setAction(char c) {
        action = c;
    }

    public int getTransitionFalse() {
        return transitions[0];
    }

    public int getTransitionTrue() {
        return transitions[1];
    }

    public void setTransitionFalse(int i) {
        transitions[0] = i;
    }

    public void setTransitionTrue(int i) {
        transitions[1] = i;
    }
}
