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

public class HUD {
    private ScheduledExecutorService monitor;

    public HUD() {
        monitor = Executors.newScheduledThreadPool(1);
        monitor.scheduleAtFixedRate(
            () -> updateDisplay(), // Calls updateDisplay() every 10 seconds
            0, 10, TimeUnit.SECONDS
        );
    }

    private void updateDisplay() {
        clearConsole();
        System.out.println("HUD is updating...");
        //display logic
    }

    public static void clearConsole() {
        try {
            System.out.print("\b".repeat(1000));
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Windows
        } catch (Exception e) {
            System.out.println("\033[H\033[2J"); // ANSI escape for Linux/Mac
            System.out.flush();
        }
    }

    public void stopHUD() {
        if (monitor != null) {
            monitor.shutdown();
        }
    }
}