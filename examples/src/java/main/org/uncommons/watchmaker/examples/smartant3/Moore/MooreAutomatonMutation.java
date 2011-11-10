package org.uncommons.watchmaker.examples.smartant3.Moore;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Alexander Buslaev
 */
public class MooreAutomatonMutation implements EvolutionaryOperator<MooreAutomaton> {
    private int mutationPoints;


    public MooreAutomatonMutation() {
        mutationPoints = 1;
    }


    /**
     * Randomly mutate each selected candidate.
     *
     * @param selectedCandidates {@inheritDoc}
     * @param rng                {@inheritDoc}
     * @return {@inheritDoc}
     */
    public List<MooreAutomaton> apply(List<MooreAutomaton> selectedCandidates, Random rng) {
        List<MooreAutomaton> mutatedPopulation = new ArrayList<MooreAutomaton>(selectedCandidates.size());
        for (int i = 0; i < selectedCandidates.size(); ++i) {
            MooreAutomaton automaton = selectedCandidates.get(i);
            MooreAutomaton mutated = mutateAutomaton(automaton, rng);
            mutatedPopulation.add(mutated);
        }
        //       for (int i = 0; i < selectedCandidates.size(); ++i) {
        //                   System.out.println(selectedCandidates.get(i).toString());
        //       System.out.println(mutatedPopulation.get(i).toString());
        //       }
        return mutatedPopulation;
    }


    /**
     * Mutates a single automaton.
     *
     * @param automato The automaton to mutate.
     * @param rng      The source of randomness to use for mutation.
     * @return A mutated version of the automaton.
     */
    private MooreAutomaton mutateAutomaton(MooreAutomaton automato, Random rng) {

        ArrayList<Integer> nodes = new ArrayList<Integer>(mutationPoints);
        int numberOfMutants = 3 * automato.getSize() + 1;
        for (int i = 0; i < mutationPoints; ++i) {
            int random = rng.nextInt(numberOfMutants);
            if (nodes.contains(random)) {
                i--;
            } else nodes.add(i, random);
        }
        MooreAutomaton automaton = new MooreAutomaton(automato);

        for (int i = 0; i < nodes.size(); ++i) {
            int nodeNumber = nodes.get(i) / 3;
            if (nodeNumber > (automato.getSize() - 1)) {
                automaton.setStart(rng.nextInt(automato.getSize()));
            } else {
                MooreNode mutant = automaton.getNode(nodeNumber);
                switch (nodes.get(i) % 3) {
                    case (0):
                        mutant.setAction(MooreNode.ACTION_VALUES[rng.nextInt(2)]);
                        break;
                    case (1):
                        mutant.setTransitionFalse(rng.nextInt(automaton.getSize()));
                        break;
                    case (2):
                        mutant.setTransitionTrue(rng.nextInt(automaton.getSize()));
                        break;
                }
                automaton.addNode(nodeNumber, mutant);
            }
        }
        return automaton;
    }

    public void setMutationPoints(int i) {
        mutationPoints = i;
    }

}
