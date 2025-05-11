/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author ethan
 */
public class GameGUI extends JPanel {

    private Rock rock;
    private JLabel petImageLabel;
    private JButton feedButton, petButton, walkButton, infoButton, exitButton, pauseButton;

    public GameGUI(MainGameGUI parent) {
        setLayout(new BorderLayout());

        //replace with switch and a loop to change the image or do it later in logic
        petImageLabel = new JLabel(new ImageIcon("rock.png")); // Replace with your image path
        petImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(petImageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));

        feedButton = new JButton("Feed");
        petButton = new JButton("Pet");
        walkButton = new JButton("Walk");
        infoButton = new JButton("Info");
        exitButton = new JButton("Exit");

        buttonPanel.add(infoButton);
        buttonPanel.add(feedButton);
        buttonPanel.add(petButton);
        buttonPanel.add(walkButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.WEST);

        JPanel pause = new JPanel(new FlowLayout(FlowLayout.RIGHT)); //right side of sublayout
        pauseButton = new JButton("Pause");
        pause.add(pauseButton);

        add(pause, BorderLayout.NORTH); //sublayout goes as top of main layout
        
        
        //next step to add active listeners and have them control what used to be startGame() in main// then have some fun drawing the rock at diffrent stages

    }

    public void setRock(Rock rock) {
        this.rock = rock;
        //UpdateRockInfo(); // to be made
    }

}
