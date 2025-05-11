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
    SaveMenuGUI saveMenuGUI = new SaveMenuGUI(this);
    private GameGUI gameGUI;
    
    public MainGameGUI() {

        setTitle("Pet Rock");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //GameGUI gameGUI = new GameGUI(this);
        saveMenuGUI = new SaveMenuGUI(this);
        gameGUI = new GameGUI(this);
        
        mainPanel.add(saveMenuGUI, "save_menu");
        mainPanel.add(gameGUI, "game");

        add(mainPanel);

        setVisible(true);
    }

    public void RockLoadToGame(Rock rock) {
        this.gameGUI.setRock(rock); // pass pet data to GameGUI
        showCard("game");
    }
    
    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }
    
    public Rock getRock(){
        return saveMenuGUI.getRock();
    }
}

