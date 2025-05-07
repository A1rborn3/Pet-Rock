/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ethan
 */
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit; //all time keeping services have been taught though Chat GPB, although writen by me 
import java.util.Scanner;
import javax.swing.*;


/*
have hit all design criteria (i think?) apart from using 2 kinds of collections, currently only using a hash map for the save and load functions.
may be able to inplement a list somewhere like for the age function
saves have to be manually deleted when dead but im not sure how to do that with code
dead pets can still be loaded but it will immideatly print the death message and end the program, it checks for death stats before allowing you to wrinte a command to edit pet (intentional)
 */
public class Main {

    private Rock rock; // undefined pet, ill define it in loadpet method

    /*public void loadPet() {
        System.out.print("Enter your pet's name: ");
        String petName = scanner.nextLine().trim();//allows users to name their pets :) and then trims the input to only use the charactors. thats important for the formatting when i add "'s" to the stats output

        SaveManager saveManager = new SaveManager();//starts the save manager class

        if (saveManager.petExists(petName)) {
            System.out.println("Loading " + petName);
            int[] stats = saveManager.getPetStats(petName);
            rock = new Rock(petName, stats[0], stats[1], stats[2], stats[3], stats[4]); // Load pet with stats from txt file
        } else {
            System.out.println("Creating a new pet");
            rock = new Rock(petName, 100, 100, 100, 100, 400); // Default stats for rock
            saveManager.savePet(petName, 100, 100, 100, 100, 400); // create new pet save to be overwritten when the program closes
        }
    }*/
    private final Scanner scanner = new Scanner(System.in);
    private final Scanner scanner1 = new Scanner(System.in); // second scanner for switch function, done to avoid bugs in how the scanner procecces. was reading invalid command after walk funtion. pretty sure theres a prettier solution for this but it was all i could think of

    public void end() {//so i can end the game with other classes and functions IE pet dies bc theres a loop that goes on forever in game start (while (true))
        rock.stopAllDecay(); //also allows me to cleanly stop all threads and the build itsself after saving so nothing is left running in the background
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        //needs to invoke a save function to text file -- done
        System.out.println("Program shutting down");
        //new SaveManager().savePet(rock.getName(), rock.hungervalue(), rock.happyvalue(), rock.fitnessvalue(), rock.energyvalue(), rock.agevalue());
        System.out.println(rock.getName() + " saved");
        System.exit(0); //program shutdown
    }
    private ScheduledExecutorService scheduler;
    //for death checker, could put this in another class but doesnt feel neccacery 

    public void DeathCheck() {//checks stats every 5 secs to see if pet is alive
        scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(
                () -> {
                    if (rock.happyvalue() <= 0) {
                        System.out.println(rock.getName() + " has become very unhappy and has unfortunatly cracked :(");
                        end();
                    }
                    if (rock.hungervalue() <= 0) {
                        System.out.println(rock.getName() + " has starved and has unfortunatly cracked :(");
                        end();
                    }
                    if (rock.fitnessvalue() <= 0) {
                        System.out.println(rock.getName() + " has become fat, please excersise"); //doesnt end game
                    }
                    if (rock.energyvalue() <= 0) {
                        System.out.println(rock.getName() + " has become exhausted and has unfortunatly cracked :(");
                        end();
                    }
                    if (rock.agevalue() <= 0 && rock.happyvalue() > 49) {
                        System.out.println(rock.getName() + " has died happy of natural causes, they have cracked open to reveal they are a geode! you won");
                        end();
                    }
                    if (rock.agevalue() <= 0 && rock.happyvalue() <= 49) {
                        System.out.println(rock.getName() + " has unfortunatly died of natural causes whilst being unhappy :(");
                        end();
                    }

                    //add other deaths -- done
                },
                0, 2, TimeUnit.SECONDS
        ); // Check every 2 seconds for system warnings Eg too sad, hungry, unfit
    }

    public void startGame() {

        String command;

        while (true) {
            System.out.print("\nEnter command: ");
            command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "info" ->
                    rock.showInfo();
                case "feed" ->
                    rock.feed();
                case "pet" ->
                    rock.pet();
                case "smack" ->
                    rock.hurt();
                case "walk" -> {
                    System.out.println("You can walk your pet between 0 and 1000 meters. How far would you like to walk?");
                    int distance = scanner1.nextInt();
                    if (distance > 0 && distance < 1000) {

                        System.out.println("Walking " + distance + " meters...");
                        try {
                            // Pauses code for 1 second per hundred meters walked to simulate walking
                            Thread.sleep(distance * 10);
                        } catch (InterruptedException e) {
                        }
                        rock.walk(distance);
                    } else {
                        System.out.println("Invalid distance");
                    }
                }
                case "exit" -> {
                    end();
                    return;
                }
                case "help" -> {
                    System.out.println("Avalible commands: Info, Feed, Pet, Smack, walk, Exit, Help");
                }
                default ->
                    System.out.println("Unknown command");
            }

        }
    }

    // Main method to run the game
    public static void main(String[] args) throws InterruptedException {
        Main game = new Main(); //call methods
        SwingUtilities.invokeLater(() -> {
            MainGameGUI mainGameGUI = new MainGameGUI();
            mainGameGUI.setVisible(true); // Make the GUI visible
        });
        //game.loadPet();
        System.out.println("Avalible commands: Info, Feed, Pet, Smack, walk, Exit, Help"); //smack is primarilty there for testing although i may keep it as a function
        game.DeathCheck(); //self exsplanatory
        
        game.startGame(); // This handles the interactive gameplay loop
        game.scanner.close(); // Close the scanner when the game ends

    }
}
