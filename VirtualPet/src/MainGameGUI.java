/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;


/**
 *
 * @author ethan
 */
public class MainGameGUI extends JFrame {


    private CardLayout cardLayout;
    private JPanel mainPanel;
    SaveMenuGUI saveMenuGUI;
    public GameGUI gameGUI;
    private PauseGUI pauseGUI;
    private DeathGUI deathGUI;
    
    public MainGameGUI() {

        setTitle("Pet Rock");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        saveMenuGUI = new SaveMenuGUI(this);
        gameGUI = new GameGUI(this, saveMenuGUI.getSaveManager());
        pauseGUI = new PauseGUI(this);
        deathGUI = gameGUI.getDeathGUI();
        
        
        mainPanel.add(saveMenuGUI, "save_menu");
        mainPanel.add(gameGUI, "game");
        mainPanel.add(pauseGUI, "pause");
        mainPanel.add(deathGUI, "deathGUI");

        add(mainPanel);

        setVisible(true);
        SwingUtilities.invokeLater(() -> cardLayout.show(mainPanel, "save_menu"));
    }

    public void RockLoadToGame(Rock rock) {
        this.gameGUI.setRock(rock); // pass pet data to GameGUI
        showCard("game");
    }
    
    public void showCard(String cardName) {
        if("save_menu".equals(cardName)){
            saveMenuGUI.loadSaveMenu();//evertime i switch back to save manager i have to check if there are new saved or deleted ones
        }
        if (gameGUI.rock != null){
            if("game".equals(cardName)){
            gameGUI.setPause(false);
        }
        else gameGUI.setPause(true);//if any card is showing apart from main game it pauses the decay
        }
        
        cardLayout.show(mainPanel, cardName);
    }
    
    public Rock getRock(){
        return saveMenuGUI.getRock();
    }
    
    public static void main(String[] args) {
        MainGameGUI mainGameGUI = new MainGameGUI();
        mainGameGUI.setVisible(true);
        
    }
    

}

