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
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author ethan
 */
public class GameGUI extends JPanel {

    public Rock rock;
    private MainImageGUI MainImage;
    private JButton feedButton, petButton, walkButton, shopButton, exitButton, pauseButton;
    private JProgressBar HungerBar, HappyBar, FitnessBar, EnergyBar;
    Map<String, JLabel> statLabels = new HashMap<>();
    private DeathChecks deathChecks;
    private DeathGUI deathGUI;
    private MainGameGUI Parent;
    private SaveManager SaveManager;
    private JLabel tipLabel;
    private List<String> tips;
    private int currentTipIndex = 0;
    private final String TIPS_FILE = "resources/tips.txt";
    private boolean IsDead = false;

    public GameGUI(MainGameGUI parent, SaveManager saveManager) {
        Parent = parent;
        SaveManager = saveManager;
        deathChecks = new DeathChecks(saveManager, this);
        deathGUI = new DeathGUI(this);
        setLayout(new BorderLayout());

        //handeled by main image logic
        MainImage = new MainImageGUI();
        add(MainImage.getImageLabel(), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));

        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));

        feedButton = new JButton("Feed");
        petButton = new JButton("Pat");
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

        //buttonPanel.add(shopButton);
        buttonPanel.add(feedButton);
        buttonPanel.add(petButton);
        buttonPanel.add(walkButton);

        add(buttonPanel, BorderLayout.WEST);

        JPanel topPanel = new JPanel(new BorderLayout());
        //setting the tips at the top
        tipLabel = new JLabel("Tips are loading", SwingConstants.CENTER);
        topPanel.add(tipLabel, BorderLayout.CENTER);
        tips = loadTipsFromDB();
        
        TipRotation();

        JPanel pause = new JPanel(new FlowLayout(FlowLayout.RIGHT)); //right side of sublayout
        pauseButton = new JButton("Pause");
        pause.add(pauseButton);
        topPanel.add(pause, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH); //sublayout goes as top of main layout

        pauseButton.addActionListener((ActionEvent e) -> {
            parent.showCard("pause");
        });

        shopButton.addActionListener((ActionEvent e) -> {
            // for if i add a shop
        });

        feedButton.addActionListener((ActionEvent e) -> {
            rock.feed();
        });

        petButton.addActionListener((ActionEvent e) -> {
            rock.pet();
        });

        walkButton.addActionListener((ActionEvent e) -> {
            JSlider slider = new JSlider(0, 1000, 500);
            slider.setMajorTickSpacing(200);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true); //making a walk slider

            int result = JOptionPane.showConfirmDialog(null, slider, "Select Walk Distance", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                int distance = slider.getValue();

                rock.fitness.increase(distance / 10);
                rock.happy.increase(10);
                rock.energy.decrease(distance / 20);
            }

        });

    }

    private void StatUpdate() {

        ScheduledExecutorService scheduler;
        scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(
                () -> {
                    if (!IsDead) {
                        HungerBar.setValue(rock.hungervalue());
                        HappyBar.setValue(rock.happyvalue());
                        FitnessBar.setValue(rock.fitnessvalue());
                        EnergyBar.setValue(rock.energyvalue());

                        statLabels.get("Hunger").setText("Hunger: " + rock.hungervalue() + "%");
                        statLabels.get("Happiness").setText("Happiness: " + rock.happyvalue() + "%");
                        statLabels.get("Fitness").setText("Fitness: " + rock.fitnessvalue() + "%");
                        statLabels.get("Energy").setText("Energy: " + rock.energyvalue() + "%");

                        MainImage.updateEmotionImage(rock); //update main image to match emotion
                        deathChecks.CheckStats();
                    }

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

        deathChecks.setRock(rock);

    }

    //making some tips on rotation to serve as another read I/O file
    private List<String> loadTipsFromDB() {
        List<String> tips = new ArrayList<>();
        String sql = "SELECT message FROM tips";
        String DB_URL = SaveManager.DB_URL;

        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tips.add(rs.getString("message"));
            }

        } catch (SQLException e) {
            System.err.println("Error loading tips: " + e.getMessage());
            SaveManager.Log("Error loading tips: " + e.getMessage());
        }

        return tips;
    }
    
    

    private void TipRotation() {
        if (tips == null || tips.isEmpty()) {
            return;
        }

        Timer timer = new Timer(5000, e -> {
            tipLabel.setText(tips.get(currentTipIndex));
            if (currentTipIndex == (tips.size() - 1)) {
                currentTipIndex = 0;
            } else {
                currentTipIndex++;
            }
        });
        timer.start();
    }

    public void setRock(Rock rock) {
        this.rock = rock;
        rock.SetPause(false);
        setDeath(false);
        deathChecks.setRock(rock);
        deathGUI.setRock(rock);
        SwingUtilities.invokeLater(() -> StatUpdate()); //allow the timer to start after GUI has loaded
    }

    public void setPause(boolean set) {
        rock.SetPause(set);//setting from main gui
    }

    public SaveManager getSaveManager() {
        return SaveManager;
    }

    public MainGameGUI getMain() {
        return Parent;
    }

    public DeathGUI getDeathGUI() {
        return deathGUI;
    }

    public DeathChecks getDeathChecks() {
        return deathChecks;
    }

    public void setDeath(boolean set) {
        IsDead = set;
    }

}
