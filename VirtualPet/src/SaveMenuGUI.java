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
public final class SaveMenuGUI extends JPanel {

    private final SaveManager saveManager;
    private Rock rock;
    private final MainGameGUI parent;

    public SaveMenuGUI(MainGameGUI parent) {
        this.parent = parent;
        saveManager = new SaveManager(parent);
        loadSaveMenu();
    }

    public void loadSaveMenu() {
        this.removeAll();
        setLayout(new GridLayout(6, 1, 10, 10));
        for (int i = 1; i <= 5; i++) {
            String petName = saveManager.getPetName(i);
            String buttonText;
            if (petName != null) {
                buttonText = "Slot " + i + ": " + petName;
            } else {
                buttonText = "Slot " + i + ": [Empty]";
            }
            JButton slotButton = new JButton(buttonText);
            add(slotButton);

            int slot = i;
            slotButton.addActionListener((ActionEvent e) -> {
                String existingPetName = saveManager.getPetName(slot);
                
                if (existingPetName != null) {
                    int[] stats = saveManager.getPetStats(slot);
                    rock = new Rock(existingPetName, stats[0], stats[1], stats[2], stats[3], stats[4]);

                } else {
                    // Create new save
                    String newName = null;
                    while (true) {
                        newName = JOptionPane.showInputDialog(this, "Enter new pet name:");
                        if (newName == null) {
                            // user hit cancel
                            return;
                        } else if (!newName.isBlank()) {
                            // valid name
                            break;
                        }
                        JOptionPane.showMessageDialog(this, "Name cannot be empty. Please try again.");
                    }

                    saveManager.savePet(slot, newName, 100, 100, 100, 100, 300);
                    JOptionPane.showMessageDialog(this, "Saved new pet: " + newName);
                    int[] stats = saveManager.getPetStats(slot);
                    rock = new Rock(newName, stats[0], stats[1], stats[2], stats[3], stats[4]);
                    

                }
                if (rock != null) {
                    parent.RockLoadToGame(rock); //this triggers card swap
                    getSaveManager().Log("Rock loaded: " + rock.getName());
                }
            }
            );

        }
        JButton ExitButton = new JButton("Exit Game");
        add(ExitButton);
        ExitButton.addActionListener((ActionEvent e) -> {
            getSaveManager().Log("System exiting safely, game ending");
            System.exit(0);//no need to save anything so just a simple exit
        });
        
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }

    public Rock getRock(){
        
        return rock;
    }
}
