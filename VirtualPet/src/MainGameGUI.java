/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author ethan
 */
public class MainGameGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainGameGUI() {
        setTitle("Pet Rock");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //GameGUI gameGUI = new GameGUI(this);
        SaveMenuGUI saveMenuGUI = new SaveMenuGUI(this);
        mainPanel.add(saveMenuGUI, "save_menu");

        add(mainPanel);

        setVisible(true);
    }

    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }
}

