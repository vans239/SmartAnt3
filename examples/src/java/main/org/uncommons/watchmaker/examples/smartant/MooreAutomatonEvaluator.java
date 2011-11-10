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

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

/**
 * @author Alexander Buslaev
 */
public class MooreAutomatonEvaluator implements FitnessEvaluator<MooreAutomaton> {

    public double getFitness(MooreAutomaton candidate, List<? extends MooreAutomaton> population) {
        AntMover f = new AntMover(candidate);
        double a = f.moveAnt();
        return a;
    }

    public boolean isNatural() {
        return true;
    }
}
