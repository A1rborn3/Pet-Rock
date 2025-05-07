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
import java.util.concurrent.TimeUnit;

public class Moodlets {
    private String name;      // The name of the moodlet
    private int value;        // Current value
    private int minValue;     // Min value
    private int maxValue;     // Max value
    private int decayRate; // How much to decrease per intervil
    private ScheduledExecutorService scheduler;

    public Moodlets(String name, int minValue, int maxValue, int initialValue, int decayRate) {
        this.name = name;
        this.minValue = 0;
        this.maxValue = maxValue;
        this.decayRate = decayRate;
        this.value = Math.min(maxValue, Math.max(minValue, initialValue)); // Ensure valid start value
        startDecay();
    }
    //encaptulation
    public void increase(int amount) {
        value = Math.min(value + amount, maxValue); // Prevent exceeding max
    }

    public void decrease(int amount) {
        value = Math.max(value - amount, minValue); // Prevent dropping below min
    }
    
    private void startDecay() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            decrease(decayRate);
        }, 0, 5, TimeUnit.SECONDS); // time intervil 
    }
    
    public void stopDecay() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }


    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    
}
