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

    public SaveMenuGUI(MainGameGUI parent) {
        setLayout(new GridLayout(5, 1, 10, 10));
        for (int i = 1; i <= 5; i++) {
            String petName = saveManager.getPetName(i);
            String buttonText = (petName != null) ? "Slot " + i + ": " + petName : "Slot " + i + ": [Empty]";
            JButton slotButton = new JButton(buttonText);
            add(slotButton);

        }

    }
}