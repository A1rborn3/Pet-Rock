/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ethan
 */
public class GameGUI extends JPanel {

    private Rock rock;
    private JLabel petImageLabel;
    private JButton feedButton, petButton, walkButton, shopButton, exitButton, pauseButton;
    private JProgressBar HungerBar, HappyBar, FitnessBar, EnergyBar;
    Map<String, JLabel> statLabels = new HashMap<>();

    public GameGUI(MainGameGUI parent) {
        setLayout(new BorderLayout());

        //replace with switch and a loop to change the image or do it later in logic
        petImageLabel = new JLabel(new ImageIcon("rock.png")); // Replace with your image path
        petImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(petImageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JPanel StatsPannel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));

        feedButton = new JButton("Feed");
        petButton = new JButton("Pet");
        walkButton = new JButton("Walk");
        shopButton = new JButton("Shop");
        exitButton = new JButton("Exit");

        setBars();

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Centered with spacing

        // One stat panel: label on top, bar below
        statsPanel.add(createStatPanel("Hunger", HungerBar));
        statsPanel.add(createStatPanel("Happiness", HappyBar));
        statsPanel.add(createStatPanel("Fitness", FitnessBar));
        statsPanel.add(createStatPanel("Energy", EnergyBar));

        // Add to GameGUI
        add(statsPanel, BorderLayout.SOUTH);

        buttonPanel.add(shopButton);
        buttonPanel.add(feedButton);
        buttonPanel.add(petButton);
        buttonPanel.add(walkButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.WEST);

        JPanel pause = new JPanel(new FlowLayout(FlowLayout.RIGHT)); //right side of sublayout
        pauseButton = new JButton("Pause");
        pause.add(pauseButton);

        add(pause, BorderLayout.NORTH); //sublayout goes as top of main layout

        shopButton.addActionListener((ActionEvent e) -> {
            // for when i add a shop
        });

        feedButton.addActionListener((ActionEvent e) -> {
            rock.feed();
        });

        petButton.addActionListener((ActionEvent e) -> {
            rock.pet();
        });

        walkButton.addActionListener((ActionEvent e) -> {
            //add walking gui
        });

        exitButton.addActionListener((ActionEvent e) -> {
            //add exit function
        });

        //have some fun drawing the rock at diffrent stages
    }

    private void StatUpdate() {
        ScheduledExecutorService scheduler;
        scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(
                () -> {
                    HungerBar.setValue(rock.hungervalue());
                    HappyBar.setValue(rock.happyvalue());
                    FitnessBar.setValue(rock.fitnessvalue());
                    EnergyBar.setValue(rock.energyvalue());

                    statLabels.get("Hunger").setText("Hunger: " + rock.hungervalue() + "%");
                    statLabels.get("Happiness").setText("Happiness: " + rock.happyvalue() + "%");
                    statLabels.get("Fitness").setText("Fitness: " + rock.fitnessvalue() + "%");
                    statLabels.get("Energy").setText("Energy: " + rock.energyvalue() + "%");
                },
                0, 1, TimeUnit.SECONDS
        ); //checks for stat changes every second to update
    }

    private JPanel createStatPanel(String labelText, JProgressBar bar) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(90, 100)); //pannel size for the stats

        JLabel label = new JLabel(labelText + ": " + bar.getValue() + "%", SwingConstants.CENTER);
        bar.setPreferredSize(new Dimension(100, 100));

        panel.add(label, BorderLayout.NORTH);
        panel.add(bar, BorderLayout.CENTER);

        statLabels.put(labelText, label);//so i can change the labels after making it

        return panel;
    }

    public void setBars() {
        HungerBar = new JProgressBar(SwingConstants.VERTICAL);
        HappyBar = new JProgressBar(SwingConstants.VERTICAL);
        FitnessBar = new JProgressBar(SwingConstants.VERTICAL);
        EnergyBar = new JProgressBar(SwingConstants.VERTICAL);

        Dimension barSize = new Dimension(100, 100);
        HungerBar.setPreferredSize(barSize);
        HappyBar.setPreferredSize(barSize);
        FitnessBar.setPreferredSize(barSize);
        EnergyBar.setPreferredSize(barSize);

        HungerBar.setMinimum(0);
        HungerBar.setMaximum(100);
        HungerBar.setValue(75);

        HappyBar.setMinimum(0);
        HappyBar.setMaximum(100);
        HappyBar.setValue(75);

        FitnessBar.setMinimum(0);
        FitnessBar.setMaximum(100);
        FitnessBar.setValue(75);

        EnergyBar.setMinimum(0);
        EnergyBar.setMaximum(100);
        EnergyBar.setValue(75);

    }

    public void setRock(Rock rock) {
        this.rock = rock;
        StatUpdate();
    }

}
