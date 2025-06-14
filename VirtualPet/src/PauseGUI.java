
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ethan
 */
public class PauseGUI extends JPanel {

    private MainGameGUI parent;
    private Rock rock;

    public PauseGUI(MainGameGUI parent) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));

        JButton resumeButton = new JButton("Resume");
        JButton saveQuitButton = new JButton("Save and Quit");
        JButton loadNewButton = new JButton("Save and Load New");
        JButton DeleteButton = new JButton("Delete Current Save");

        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveQuitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        DeleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(resumeButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(loadNewButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(DeleteButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(saveQuitButton);
        add(Box.createVerticalGlue());
        
        resumeButton.addActionListener((ActionEvent e) -> {
            parent.showCard("game");
        });
        saveQuitButton.addActionListener((ActionEvent e) -> {
            Rock current = parent.getRock();
            parent.saveMenuGUI.getSaveManager().saveRock(current);
            parent.saveMenuGUI.getSaveManager().Log("System exiting safely, game ending");
            System.exit(0);
        });
        loadNewButton.addActionListener((ActionEvent e) -> {
            Rock current = parent.getRock();
            parent.saveMenuGUI.getSaveManager().saveRock(current); //saves rock ready to load new
            current.stopAllDecay();//stops background process and avoids a rock dying that isnt loaded
            parent.showCard("save_menu");
        });
        DeleteButton.addActionListener((ActionEvent e) -> {
            Rock current = parent.getRock();
            int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this save?","Confirm Deletion",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                parent.gameGUI.rock.stopAllDecay();
                parent.saveMenuGUI.getSaveManager().deleteSave(current);
            }
            parent.showCard("save_menu");
        });
    }
}
