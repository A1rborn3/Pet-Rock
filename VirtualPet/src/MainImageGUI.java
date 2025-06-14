/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ethan
 */
public class MainImageGUI {

    private JLabel rockPNG;
    private Map<String, ImageIcon> RockStates;

    public MainImageGUI() {
        rockPNG = new JLabel();
        rockPNG.setHorizontalAlignment(SwingConstants.CENTER);

        String path = "resources/Images/";
        // Preload all images, 1-4 responding to age.. dir still to be made
        RockStates = new HashMap<>();
        RockStates.put("happy1", new ImageIcon(path + "happy1.png"));
        RockStates.put("hungry1", new ImageIcon(path + "hungry1.png"));//pebble
        RockStates.put("tired1", new ImageIcon(path + "tired1.png"));
        RockStates.put("sad1", new ImageIcon(path + "sad1.png"));
        RockStates.put("default1", new ImageIcon(path + "default1.png"));

        RockStates.put("happy2", new ImageIcon(path + "happy2.png"));
        RockStates.put("hungry2", new ImageIcon(path + "hungry2.png"));
        RockStates.put("tired2", new ImageIcon(path + "tired2.png"));//rock
        RockStates.put("sad2", new ImageIcon(path + "sad2.png"));
        RockStates.put("default2", new ImageIcon(path + "default2.png"));

        RockStates.put("happy3", new ImageIcon(path + "happy3.png"));
        RockStates.put("hungry3", new ImageIcon(path + "hungry3.png"));
        RockStates.put("tired3", new ImageIcon(path + "tired3.png"));//boulder
        RockStates.put("sad3", new ImageIcon(path + "sad3.png"));
        RockStates.put("default3", new ImageIcon(path + "default3.png"));

    }

    public JLabel getImageLabel() {
        return rockPNG;
    }

    public void updateEmotionImage(Rock rock) {
        String emotion = determineEmotion(rock);
        ImageIcon icon = RockStates.getOrDefault(emotion, RockStates.get("default1"));
        rockPNG.setIcon(icon);
    }

    private String determineEmotion(Rock rock) {
        
        int age;
        if (rock.agevalue()<100){
            age = 3;
        }
        else if (rock.agevalue()<200){
            age = 2;
        }
        else{
            age = 1;
        }

        
        if (rock.hungervalue() < 40 && rock.hungervalue() < rock.energyvalue() && rock.hungervalue() < rock.happyvalue()) { //makes sure its the lowest stat
            return "hungry" + age;
        }
        if (rock.energyvalue() < 40 && rock.energyvalue() < rock.happyvalue()) {
            return "tired" + age;
        }
        if (rock.happyvalue() > 70) {
            return "happy" + age;
        }
        if (rock.happyvalue() < 40) {
            return "sad" + age;
        }
        return "default" + age;
    }

}
