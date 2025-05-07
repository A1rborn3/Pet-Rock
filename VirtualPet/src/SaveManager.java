/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ethan
 */
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaveManager {

    private static final String FilePath = "resources/pet_saves.txt";
    private final Map<String, int[]> petData;
    private final Map<String, String> petNames;
    
    public SaveManager() {
        petData = new HashMap<>();
        petNames = new HashMap<>();
        loadpet();
    }

    private void loadpet() {
        File file = new File(FilePath);
        if (!file.exists()) {
            System.out.println("Failed to find file");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String slot = parts[0];
                    String name = parts[1];
                    int hunger = Integer.parseInt(parts[2]);
                    int happiness = Integer.parseInt(parts[3]);
                    int fitness = Integer.parseInt(parts[4]);
                    int energy = Integer.parseInt(parts[5]);
                    int age = Integer.parseInt(parts[6]);

                    petData.put(slot, new int[]{ hunger, happiness, fitness, energy, age});
                    petNames.put(slot, name); // new hash map so i can display names in menu
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading: " + e.getMessage());
        }
    }

    public void savePet(int slot, String name, int hunger, int happiness, int fitness, int energy, int age) {
        petData.put("slot" + slot, new int[]{ hunger, happiness, fitness, energy, age});
        writeToFile();
    }

    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FilePath))) {
            for (String key : petData.keySet()) {
            int[] stats = petData.get(key);
            String name = petNames.get(key);
            writer.write(key + "," + name + "," + stats[0] + "," + stats[1] + "," + stats[2] + "," + stats[3] + "," + stats[4]);
            writer.newLine();
        } //updaed to work betwen two hashmaps (name and stats)
        } catch (IOException e) {
            System.err.println("Error saving pet: " + e.getMessage());
        }
    }

    public boolean petExists(String name) {
        return petData.containsKey(name);
    }

    public int[] getPetStats(int slot) {
        return petData.getOrDefault("slot" + slot, new int[]{100, 100, 100, 100, 400}); // Default stats if pet isn't found
    }
    
    public String getPetName(int slot){
        return petNames.get("slot" + slot);
    }
}