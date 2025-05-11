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
public class SaveMenuGUI extends JPanel {

    private final SaveManager saveManager = new SaveManager();
    private Rock rock;

    public SaveMenuGUI(MainGameGUI parent) {
        setLayout(new GridLayout(5, 1, 10, 10));
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
                    String newName = JOptionPane.showInputDialog(this, "Enter new pet name:");
                    if (newName != null && !newName.isBlank()) {
                        saveManager.savePet(slot, newName, 100, 100, 100, 100, 400);
                        JOptionPane.showMessageDialog(this, "Saved new pet: " + newName);
                        int[] stats = saveManager.getPetStats(slot);
                        rock = new Rock(newName, stats[0], stats[1], stats[2], stats[3], stats[4]);
                        saveManager.savePet(slot, newName, 100, 100, 100, 100, 400);
                        System.out.println(newName);

                    }
                }
                if (rock != null) {
                    parent.RockLoadToGame(rock); // <-- this triggers the card swap
                }
            }
            );

        }

    }

    public Rock getRock() {
        return rock;
    }
}
