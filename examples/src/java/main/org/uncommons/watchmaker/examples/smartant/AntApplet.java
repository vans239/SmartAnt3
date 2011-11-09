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

import org.uncommons.swing.SpringUtilities;
import org.uncommons.swing.SwingBackgroundTask;
import org.uncommons.watchmaker.examples.AbstractExampleApplet;
import org.uncommons.watchmaker.examples.smartant.automaton.DocumentPanel;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.termination.Stagnation;
import org.uncommons.watchmaker.framework.termination.TargetFitness;
import org.uncommons.watchmaker.swing.AbortControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * @author Alexander Buslaev
 */
public class AntApplet extends AbstractExampleApplet {
    private JButton stepButton;
    private JButton gameButton;
    private JButton initButton;
    private JButton restartButton;
    private AntRenderer renderer;
    private MooreAutomaton automaton;
    private DocumentPanel vis;
    private static JSpinner populationSizeSpinner;
    private static JSpinner stateSpinner;
    private static JSpinner targetFitnessSpinner;
    private static AbortControl abort;
    private static JSpinner numberOfCrossoverPointsSpinner;
    private static JSpinner fitterSpinner;
    private static JSpinner muLambdaSpinner;
    private static AntEvolutionMonitor monitor;
    private int steps;
    JTabbedPane tabs;
    JTabbedPane tabs2;

    private static int step1 = 1;
    private static int step2 = 1;


    public AntApplet(MooreAutomaton a) {
        automaton = a;
        renderer = new AntRenderer(a);
        vis = new DocumentPanel(true, automaton, renderer.getMover(), 400, 400);
    }

    protected void prepareGUI(Container container) {
        container.add(createButtonPanel(), BorderLayout.SOUTH);
        container.add(createControls(), BorderLayout.NORTH);
        tabs = new JTabbedPane();
        monitor = new AntEvolutionMonitor();
        tabs.add("Ant visualizer", renderer);
        tabs.add("Monitor", monitor);
        tabs2 = new JTabbedPane();
        tabs2.add("Automaton visualizer", vis);
        //    tabs.add("Ant visualizer", new AntEvolutionMonitor(false));
        container.add(tabs, BorderLayout.WEST);
        container.add(tabs2, BorderLayout.EAST);
    }

    private JComponent createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
        abort = new AbortControl();
        initButton = new JButton("Find solution");
        initButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                restartButton.setEnabled(false);
                gameButton.setEnabled(false);
                stepButton.setEnabled(false);
                initButton.setEnabled(false);
                abort.reset();
                createTask().execute();
            }
        });
        stepButton = new JButton("Do step");
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                restartButton.setEnabled(true);
                renderer.move();
                vis.repaint();
                steps++;
                stepButton.setText("Do next step / " + steps + " done");
            }
        });
        gameButton = new JButton("Play 200 steps");
        gameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                restartButton.setEnabled(true);
                for (int i = 0; i < 200; ++i)
                    renderer.move();
            }
        });
        restartButton = new JButton("Restart visualizator");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                restartButton.setEnabled(false);
                renderer.setAutomaton(automaton);
                vis.setA(automaton);
                vis.setM(renderer.getMover());
                vis.init(automaton, 350, 400);
                vis.repaint();
                gameButton.setEnabled(true);
                stepButton.setEnabled(true);
                initButton.setEnabled(true);
                abort.getControl().setEnabled(false);
                renderer.reset();
                steps = 0;
                stepButton.setText("Do step");
            }
        });
        gameButton.setEnabled(false);
        stepButton.setEnabled(false);
        restartButton.setEnabled(false);
        buttonPanel.add(initButton);
        buttonPanel.add(abort.getControl());
        buttonPanel.add(gameButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(restartButton);
        return buttonPanel;
    }

    private JComponent createControls() {
        JPanel controls = new JPanel(new BorderLayout());
        JPanel innerPanel = new JPanel(new SpringLayout());

        innerPanel.add(new JLabel("Number of fitter candidates: "));
        fitterSpinner = new JSpinner(new SpinnerNumberModel(0.2, 0, 1, 0.1));
        innerPanel.add(fitterSpinner);

        innerPanel.add(new JLabel("Maximum number of mutation points: "));
        numberOfCrossoverPointsSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        innerPanel.add(numberOfCrossoverPointsSpinner);

        innerPanel.add(new JLabel("Mu Lambda: "));
        muLambdaSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 500, 1));
        innerPanel.add(muLambdaSpinner);

        innerPanel.add(new JLabel("Population Size: "));
        populationSizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50000, 1));
        innerPanel.add(populationSizeSpinner);
        innerPanel.add(new JLabel("Number of states: "));
        stateSpinner = new JSpinner(new SpinnerNumberModel(8, 1, 150, 1));
        innerPanel.add(stateSpinner);
        innerPanel.add(new JLabel("Target fitness: "));
        targetFitnessSpinner = new JSpinner(new SpinnerNumberModel(89.0, 50.0, 89.2, 0.1));
        innerPanel.add(targetFitnessSpinner);
        SpringUtilities.makeCompactGrid(innerPanel, 6, 2, 0, 6, 6, 6);
        innerPanel.setBorder(BorderFactory.createTitledBorder("Configuration"));
        controls.add(innerPanel, BorderLayout.CENTER);
        return controls;
    }

    public static MooreAutomaton evolve() {
        MooreAutomatonFactory factory = new MooreAutomatonFactory(new Integer(stateSpinner.getValue().toString()).intValue());
        //    java.util.List<EvolutionaryOperator<MooreAutomaton>> operators = new ArrayList<EvolutionaryOperator<MooreAutomaton>>(1);
        MooreAutomatonMutation operators = new MooreAutomatonMutation();
        //     operators.add(new MooreAutomatonCrossover(numberOfCrossoverNodes, probabilityCrossover));
        //    EvolutionaryOperator<MooreAutomaton> pipeline = new EvolutionPipeline<MooreAutomaton>(operators);
        EvolutionEngine<MooreAutomaton> engine = new AntESEngine
                (factory,
                        operators,
                        new MooreAutomatonEvaluator(),
                        true,
                        new Integer(muLambdaSpinner.getValue().toString()).intValue(),
                        step1,
                        step2,
                        new Double(fitterSpinner.getValue().toString()).doubleValue(),
                        new Integer(numberOfCrossoverPointsSpinner.getValue().toString()).intValue(),
                        new Random());
        engine.addEvolutionObserver(monitor);
        return engine.evolve(new Integer(populationSizeSpinner.getValue().toString()).intValue(),
                0,
                new TargetFitness(new Double(targetFitnessSpinner.getValue().toString()).doubleValue(), true),
                abort.getTerminationCondition(),
                new Stagnation(25, true));
    }

    private SwingBackgroundTask<MooreAutomaton> createTask() {
        return new SwingBackgroundTask<MooreAutomaton>() {
            @Override
            protected MooreAutomaton performTask() {
                MooreAutomaton automaton = evolve();
                return automaton;
            }

            protected void postProcessing(MooreAutomaton automato) {
                automaton = automato;
                renderer.setAutomaton(automaton);
                vis.setA(automaton);
                vis.setM(renderer.getMover());
                vis.init(automaton, 350, 400);
                vis.repaint();
                gameButton.setEnabled(true);
                stepButton.setEnabled(true);
                initButton.setEnabled(true);

                renderer.reset();
                if (new MooreAutomatonEvaluator().getFitness(automato, null) < new Double(targetFitnessSpinner.getValue().toString()).doubleValue() && (abort.getControl().isEnabled())) {
                    restartButton.setEnabled(false);
                    initButton.setEnabled(false);
                    abort.reset();
                    createTask().execute();
                } else abort.getControl().setEnabled(false);

            }
        };
    }

    public static void main(String args[]) {
        MooreAutomaton automaton = new MooreAutomaton(1, 0);
        automaton.addNode(0, new MooreNode(0, 0, 'M'));
        new AntApplet(automaton).displayInFrame("Smart Ant");
    }

}