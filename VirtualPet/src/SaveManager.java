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

    public SaveManager() {
        petData = new HashMap<>();
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
                if (parts.length == 6) {
                    String name = parts[0];
                    int hunger = Integer.parseInt(parts[1]);
                    int happiness = Integer.parseInt(parts[2]);
                    int fitness = Integer.parseInt(parts[3]);
                    int energy = Integer.parseInt(parts[4]);
                    int age = Integer.parseInt(parts[5]);

                    petData.put(name, new int[]{hunger, happiness, fitness, energy, age});
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading: " + e.getMessage());
        }
    }

    public void savePet(String name, int hunger, int happiness, int fitness, int energy, int age) {
        petData.put(name, new int[]{hunger, happiness, fitness, energy, age});
        writeToFile();
    }

    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FilePath))) {
            for (Map.Entry<String, int[]> entry : petData.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue()[0] + "," + entry.getValue()[1] + "," + entry.getValue()[2] + "," + entry.getValue()[3] + "," + entry.getValue()[4]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving pet: " + e.getMessage());
        }
    }

    public boolean petExists(String name) {
        return petData.containsKey(name);
    }
    
    public int[] getPetStats(String name) {
        return petData.getOrDefault(name, new int[]{100, 100, 100, 100, 400}); // Default stats if pet isn't found
    }

}
