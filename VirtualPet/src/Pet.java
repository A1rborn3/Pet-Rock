/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ethan
 */
abstract class Pet { //abstraction

    protected String name;
    protected Hunger hunger;
    protected Happy happy;
    protected Fitness fitness;
    protected Energy energy;
    protected Age age;

    public Pet(String name, int hunger, int happiness, int fitness, int energy, int age) {
        this.name = name;
        this.hunger = new Hunger(hunger);
        this.happy = new Happy(happiness);
        this.fitness = new Fitness(fitness);
        this.energy = new Energy(energy);
        this.age = new Age(age);
    }

    public abstract void pet();

    public abstract void feed();

    public abstract void hurt();

    public abstract void walk(int distance);

    public int happyvalue() {
        return happy.getValue();
    }

    public void stopAllDecay() {
        hunger.stopDecay();
        happy.stopDecay();
        fitness.stopDecay();
        energy.stopDecay();
    }
    //encaptulation

    public int hungervalue() {
        return hunger.getValue();
    }

    public int fitnessvalue() {
        return fitness.getValue();
    }
    
    public int energyvalue(){
        return energy.getValue();
    }
    
    public String getName() {
        return name;
    }
    
    public int agevalue(){
        return age.getValue();
    }

    public void showInfo() {//also going to cheack age
        System.out.println("------ " + name + "'s INFO ------");
        if (age.getValue() >300) System.out.println(name + " is a Pebble");
        else if (age.getValue()>200) System.out.println(name + " is a Stone");
        else if (age.getValue()>100) System.out.println(name + " is a Rock");
        else System.out.println(name + " is a Boulder");
        System.out.println("Food: " + hunger.getValue());
        System.out.println("Happiness: " + happy.getValue());
        System.out.println("Fitness: " + fitness.getValue());
        System.out.println("Energy: " + energy.getValue());
        
        System.out.println("----------------------");
    }
    
    public void SetPause(boolean set){
        hunger.SetPause(set);
        happy.SetPause(set);
        fitness.SetPause(set);
        energy.SetPause(set);
    }
}
