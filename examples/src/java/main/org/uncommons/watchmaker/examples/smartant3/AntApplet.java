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
package org.uncommons.watchmaker.examples.smartant3;

import org.uncommons.swing.SpringUtilities;
import org.uncommons.swing.SwingBackgroundTask;
import org.uncommons.watchmaker.examples.AbstractExampleApplet;
import org.uncommons.watchmaker.examples.smartant3.mealy.*;
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
    private MealyMachine machine;
    private static AbortControl abort;
    private static AntEvolutionMonitor monitor;
    private int steps;
    JTabbedPane tabs;
    JTabbedPane tabs2;

    private static int step1 = 1;
    private static int step2 = 1;


    public AntApplet(MealyMachine machine) {
        this.machine = machine;
        renderer = new AntRenderer(machine);
    }

    protected void prepareGUI(Container container) {
        container.add(createButtonPanel(), BorderLayout.SOUTH);
        container.add(createControls(), BorderLayout.NORTH);
        tabs = new JTabbedPane();
        monitor = new AntEvolutionMonitor();
        tabs.add("Ant visualizer", renderer);
        tabs.add("Monitor", monitor);
        tabs2 = new JTabbedPane();
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
                steps++;
                stepButton.setText("Do next step / " + steps + " done");
            }
        });
        gameButton = new JButton("Play 200 steps");
        gameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                restartButton.setEnabled(true);
                for (int i = 0; i < 200; ++i) {
                    renderer.move();
                }
            }
        });
        restartButton = new JButton("Restart visualizator");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                restartButton.setEnabled(false);
                renderer.setMachine(machine);
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

        innerPanel.add(new JLabel("Number of fitter candidates: " + Properties.numberOfFilterCandidates));
        innerPanel.add(new JLabel("Maximum number of mutation points: " + Properties.countOfMutationPoints));
        innerPanel.add(new JLabel("Mu Lambda: " + Properties.muLambda));
        innerPanel.add(new JLabel("Population Size: " + Properties.populationSize));
        innerPanel.add(new JLabel("Number of states: " + Properties.countOfStates));
        innerPanel.add(new JLabel("Target fitness: " + Properties.targetFitness));
        innerPanel.add(new JLabel("Count of fields: " + Properties.countOFfields));
        innerPanel.add(new JLabel("p(food in cell) = " + Properties.mu));
        innerPanel.add(new JLabel("Size: " + Properties.SIZE));
        innerPanel.add(new JLabel("STEPS: " + Properties.STEPS));


        SpringUtilities.makeCompactGrid(innerPanel, 10, 1, 0, 10, 10, 10);
        innerPanel.setBorder(BorderFactory.createTitledBorder("Configuration"));
        controls.add(innerPanel, BorderLayout.CENTER);
        return controls;
    }

    public static MealyMachine evolve() {
        Random random = new Random();
        MealyMachineFactory factory = new MealyMachineFactory(Properties.countOfStates);
        MealyMachineMutation operators = new MealyMachineMutation();
        MealyMachineEvaluator mme = new MealyMachineEvaluator(Properties.countOFfields, random);
        EvolutionEngine<MealyMachine> engine = new AntESEngine(factory, operators, mme, true, Properties.muLambda,
                                                               step1, step2, Properties.numberOfFilterCandidates,
                                                               Properties.countOfMutationPoints, random);
        engine.addEvolutionObserver(monitor);
        return engine.evolve(Properties.populationSize, 0, new TargetFitness(Properties.targetFitness, true),
                             abort.getTerminationCondition(), new Stagnation(1000, true));
    }

    private SwingBackgroundTask<MealyMachine> createTask() {
        return new SwingBackgroundTask<MealyMachine>() {
            @Override
            protected MealyMachine performTask() {
                MealyMachine machine = evolve();
                return machine;
            }

            protected void postProcessing(MealyMachine machine) {
                AntApplet.this.machine = machine;
                renderer.setMachine(machine);
                gameButton.setEnabled(true);
                stepButton.setEnabled(true);
                initButton.setEnabled(true);

                renderer.reset();
                if (new MealyMachineEvaluator(Properties.countOFfields, new Random()).getFitness(machine,
                                                           null) < Properties.targetFitness && abort.getControl().isEnabled()) {
                    restartButton.setEnabled(false);
                    initButton.setEnabled(false);
                    abort.reset();
                    createTask().execute();
                } else {
                    abort.getControl().setEnabled(false);
                }

            }
        };
    }

    public static void main(String args[]) {
        //todo
        MealyMachineFactory mmf = new MealyMachineFactory(Properties.countOfStates);
        MealyMachine machine = mmf.generateRandomCandidate(new Random());
        new AntApplet(machine).displayInFrame("Smart Ant 3");
    }

}
