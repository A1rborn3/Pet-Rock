/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ethan
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {
    
    //4 read 4 write interctions (or 8 write if you cound the tips)

    //swapping out my I/O files with DBMS
    //private static final String SAVE_FILE = "resources/pet_saves.txt";
    //private static final String DELETED_FILE = "resources/pet_deleted.txt";
    //private final Map<String, int[]> petData;
    //private final Map<String, String> petNames;
    public static final String DB_URL = "jdbc:derby:petdb;create=true";
    private final MainGameGUI parent;
    private static final String LOG_FILE = "resources/pet_Logs.txt";

    public SaveManager(MainGameGUI Parent) {
        this.parent = Parent;
        //petData = new HashMap<>();
        //petNames = new HashMap<>();
        createTable();
        //loadpet();
        Log("SaveManager setup complete, game started");
    }
    boolean tipsTableCreated = false;

    private void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
            try {
                stmt.executeUpdate("""
                               CREATE TABLE pets (slot INT PRIMARY KEY, name VARCHAR(50), hunger INT, happiness INT, fitness INT, energy INT, age INT)
                               """);
            } catch (SQLException e) {
                if (!"X0Y32".equals(e.getSQLState())) {
                    throw e; // Ignore "table already exists"
                }
            }

            //Logs table
            try {

                stmt.executeUpdate("""
                               CREATE TABLE logs ( id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, message VARCHAR(255))
                               """);
            } catch (SQLException e) {
                if (!"X0Y32".equals(e.getSQLState())) {
                    throw e;
                }
            }

            //Deleted pets table
            try {

                stmt.executeUpdate("""
                               CREATE TABLE deleted_pets (id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, name VARCHAR(50), hunger INT, happiness INT, fitness INT, energy INT, age INT)
                               """);
            } catch (SQLException e) {
                if (!"X0Y32".equals(e.getSQLState())) {
                    throw e;
                }
            }

            //Tips table
            try {
                stmt.executeUpdate("""
            CREATE TABLE tips (
                id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                message VARCHAR(255)
            )
        """);
                tipsTableCreated = true;
            } catch (SQLException e) {
                if (!"X0Y32".equals(e.getSQLState())) {
                    throw e;
                }
            }
            if (tipsTableCreated) {//only add if tips created on this run
                stmt.executeUpdate("INSERT INTO tips (message) VALUES ('You can feed your pet to give it more energy')");
                stmt.executeUpdate("INSERT INTO tips (message) VALUES ('if your pets happieness gets too low, it may die of depression')");
                stmt.executeUpdate("INSERT INTO tips (message) VALUES ('Don't walk your pet too much, otherwise you may exhaust them')");
                stmt.executeUpdate("INSERT INTO tips (message) VALUES ('You can exit or change saves at any time from the pause menu')");//do these count as seperate write interactions?? i hope so
            };

        } catch (SQLException e) {
            if (!e.getSQLState().equals("X0Y32")) { // Table already exists
                e.printStackTrace();
                Log("Error creating table: " + e.getMessage());
            }

        }

        int deletedCount = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM deleted_pets")) { 
            if (rs.next()) {
                deletedCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Log("Number of deleted pets: " + deletedCount); //my final read interaction from DBMS
    }

    public void saveRock(Rock rock) {
        int slot = getSlotForPetName(rock.getName());

        if (slot != -1) {
            savePet(slot, rock.getName(),
                    rock.hungervalue(),
                    rock.happyvalue(),
                    rock.fitnessvalue(),
                    rock.energyvalue(),
                    rock.agevalue());
            Log("Saved rock: " + rock.getName());
        } else {
            System.err.println("No save slot found for rock: " + rock.getName());
            Log("No save slot found for rock: " + rock.getName());
        }
    }

    public void savePet(int slot, String name, int hunger, int happiness, int fitness, int energy, int age) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String updateSql = """
            UPDATE pets SET name=?, hunger=?, happiness=?, fitness=?, energy=?, age=?
            WHERE slot=?""";
            try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                updatePs.setString(1, name);
                updatePs.setInt(2, hunger);
                updatePs.setInt(3, happiness);
                updatePs.setInt(4, fitness);
                updatePs.setInt(5, energy);
                updatePs.setInt(6, age);
                updatePs.setInt(7, slot);

                int rowsAffected = updatePs.executeUpdate();
                Log("Update affected rows: " + rowsAffected);

                //if no rows changed then row doesnt exist, insert new
                if (rowsAffected == 0) {
                    String insertSql = """
                    INSERT INTO pets (slot, name, hunger, happiness, fitness, energy, age)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
                    try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                        insertPs.setInt(1, slot);
                        insertPs.setString(2, name);
                        insertPs.setInt(3, hunger);
                        insertPs.setInt(4, happiness);
                        insertPs.setInt(5, fitness);
                        insertPs.setInt(6, energy);
                        insertPs.setInt(7, age);
                        insertPs.executeUpdate();
                        Log("Inserted new pet: " + name);
                    }
                } else {
                    Log("Updated existing pet: " + name);
                }
            }

            Log("Saved pet to DB: " + name);
        } catch (SQLException e) {
            Log("Error saving pet: " + e.getMessage());
        }
    }

    public int[] getPetStats(int slot) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT hunger, happiness, fitness, energy, age FROM pets WHERE slot=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, slot);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new int[]{
                        rs.getInt(1), rs.getInt(2), rs.getInt(3),
                        rs.getInt(4), rs.getInt(5)
                    };
                }
            }
        } catch (SQLException e) {
            Log("Error reading pet stats: " + e.getMessage());
        }
        return new int[]{100, 100, 100, 100, 300};// Default stats if pet isn't found
    }

    public String getPetName(int slot) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT name FROM pets WHERE slot=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, slot);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            Log("Error reading pet name: " + e.getMessage());
        }
        return null;
    }

    public int getSlotForPetName(String name) {
        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement ps = conn.prepareStatement("SELECT slot FROM pets WHERE name = ?")) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("slot");
            }
        } catch (SQLException e) {
            Log("Error finding slot for pet: " + name + " - " + e.getMessage());
        }
        return -1;
    }

    public void deleteSave(Rock rock) {
        int slot = getSlotForPetName(rock.getName());

        if (slot == -1) {
            Log("No save slot found for rock: " + rock.getName());
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false); // transaction start

            // Archive the pet
            try (PreparedStatement archive = conn.prepareStatement("""
            INSERT INTO deleted_pets (name, hunger, happiness, fitness, energy, age)
            VALUES (?, ?, ?, ?, ?, ?)
            """)) {
                archive.setString(1, rock.getName());
                archive.setInt(2, rock.hungervalue());
                archive.setInt(3, rock.happyvalue());
                archive.setInt(4, rock.fitnessvalue());
                archive.setInt(5, rock.energyvalue());
                archive.setInt(6, rock.agevalue());
                archive.executeUpdate();
                Log("Deleted " + rock.getName() + " and added to archive table");
            }

            // Delete from main pets table
            try (PreparedStatement delete = conn.prepareStatement("DELETE FROM pets WHERE slot = ?")) {
                delete.setInt(1, slot);
                int deleted = delete.executeUpdate();
                if (deleted > 0) {
                    Log("Deleted " + rock.getName());
                } else {
                    Log("Error deleting " + rock.getName());
                }
            }

            conn.commit();

        } catch (SQLException e) {
            Log("Error archiving or deleting pet: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    

    public void Log(String log) {
        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement ps = conn.prepareStatement("INSERT INTO logs (message) VALUES (?)")) {
            ps.setString(1, log);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to log message to DB: " + e.getMessage());
        }
        //Log("Error Logging" + e.getMessage()); calling this might send into loop
    }

    public List<String> getRecentLogs(int limit) {
        List<String> logs = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement ps = conn.prepareStatement("SELECT timestamp, message FROM logs ORDER BY timestamp DESC FETCH FIRST ? ROWS ONLY")) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                logs.add(rs.getTimestamp("timestamp") + " - " + rs.getString("message"));//all timestamp processer were taught by chatGpt
            }
        } catch (SQLException e) {
            logs.add("Error reading logs: " + e.getMessage());
        }
        return logs;
    }
    

}
