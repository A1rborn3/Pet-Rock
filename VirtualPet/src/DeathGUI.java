
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ethan
 */
public class DeathGUI extends JPanel {

    private GameGUI Parent;
    private Rock rock;
    private ImageIcon rockHappy;//is equal to file path
    private ImageIcon rockSad;
    private String Death;

    public DeathGUI(GameGUI parent) {
        this.Parent = parent;
        setLayout(new BorderLayout());
        
        rockHappy = new ImageIcon("resources/images/DeathHappy.png");
        rockSad = new ImageIcon("resources/images/DeathSad.png");

        //rock will be saved to an archive but deleted from main save db
        

    }
    
    public void loadDeath(){
        Death = Parent.getDeathChecks().DeathCause();
        
        JLabel deathLabel = new JLabel();
        if ("old age".equals(Death)) {
            deathLabel = new JLabel("Your pet rock, "+ rock.getName() +", has turned into a beautiful geode :). The save file has been archived.", SwingConstants.CENTER); 
        } else {
            deathLabel = new JLabel("Unfortunatly your pet rock has died of " + Death + ". The save file has been archied.", SwingConstants.CENTER);
        }
        
        
        deathLabel.setFont(new Font("Arial", Font.BOLD, 18));
        deathLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); //text padding
        add(deathLabel, BorderLayout.NORTH);

        JLabel rockImageLabel;
        if ("old age".equals(Death)) {
            rockImageLabel = new JLabel(rockHappy);
        } else {
            rockImageLabel = new JLabel(rockSad);
        }
        rockImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(rockImageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton loadButton = new JButton("Load New Save");
        JButton QuitButton = new JButton("Quit");

        loadButton.addActionListener(e -> {
            Parent.getMain().showCard("save_menu");
        });
        QuitButton.addActionListener(e -> {
            Parent.getMain().saveMenuGUI.getSaveManager().Log("System exiting safely, game ending");
            System.exit(0);
        });
        
        buttonPanel.add(loadButton);
        buttonPanel.add(QuitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setRock(Rock rock) {
        this.rock = rock;

    }
}
