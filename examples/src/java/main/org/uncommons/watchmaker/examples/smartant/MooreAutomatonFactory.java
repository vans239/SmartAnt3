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

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

/**
 * Factory that generates basic Moore automaton for evolution
 *
 * @author Alexander Buslaev
 */

public class MooreAutomatonFactory extends AbstractCandidateFactory<MooreAutomaton> {

    private int numberOfStates;


    public MooreAutomatonFactory(int n) {
        numberOfStates = n;
    }

    public MooreAutomaton generateRandomCandidate(Random rng) {
        int start = rng.nextInt(numberOfStates);
        MooreAutomaton automaton = new MooreAutomaton(numberOfStates, start);
        for (int i = 0; i < numberOfStates; ++i) {
            MooreNode node = new MooreNode(rng.nextInt(numberOfStates), rng.nextInt(numberOfStates), MooreNode.ACTION_VALUES[rng.nextInt(2)]);
            automaton.addNode(i, node);
        }
        return automaton;
    }
}