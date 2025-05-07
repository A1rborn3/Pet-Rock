/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ethan
 */
public class Energy extends Moodlets {

    public Energy(int value) {
        super("Energy", 0, 100, value, 0); //energy only decreases when you go for a walk, no natural decay
    }
    
}
