/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ethan
 */
class Rock extends Pet {
    public Rock(String name, int hunger, int happiness, int fitness, int energy, int age) {
        super(name, hunger, happiness, fitness, energy, age);
    }

    //polymorphiusm
    @Override
    public void pet() {
        happy.increase(10);
        //System.out.println("Happiness increased to: " + happy.getValue());
    }

    @Override
    public void feed() {
        hunger.increase(10);
        energy.increase(15);
        //System.out.println("Food increased to: " + hunger.getValue());
        //System.out.println("Energy increased to: " + energy.getValue());
    }

    @Override
    public void hurt() {
        happy.decrease(40);
        //System.out.println("ouch wtf. Happiness decresed to: " + happy.getValue());
    }
    
    @Override
    public void walk(int distance){
        fitness.increase(distance/10);
        happy.increase(10);
        energy.decrease(distance/20); // max energy loss is 50
        //System.out.println("The Rock likes walks, Happiness increased to: " + happy.getValue() + " , Fitness increased to: " + fitness.getValue() + ", and Energy decreased to: " + energy.getValue());
    }
    
    
}
