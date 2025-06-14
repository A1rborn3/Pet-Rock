/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ethan
 */
public class SaveManagerTest {

    int testSlot = 6;
    SaveManager instance;

    public SaveManagerTest() {
        instance = new SaveManager(null);
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of saveRock method, of class SaveManager.
     */
    @Test
    public void testSaveAndLoadPet() {
        String name = "TestRock";
        int hunger = 50, happiness = 60, fitness = 70, energy = 80, age = 230;

        instance.savePet(testSlot, name, hunger, happiness, fitness, energy, age);
        String nameTest = instance.getPetName(testSlot);
        int[] stats = instance.getPetStats(testSlot);

        assertEquals(name, nameTest);
        assertEquals(hunger, stats[0]);
        assertEquals(happiness, stats[1]);
        assertEquals(fitness, stats[2]);
        assertEquals(energy, stats[3]);
        assertEquals(age, stats[4]);
    }

    @Test
    public void testGetSlotForPetName() {
        instance.savePet(testSlot, "TestRock", 1, 1, 1, 1, 1);
        int result = instance.getSlotForPetName("TestRock");
        assertEquals(testSlot, result);
    }

    @Test
    public void testDeleteSave() {
        Rock testRock = new Rock("TestRock", 50, 60, 70, 80, 230);
        instance.saveRock(testRock);
        instance.deleteSave(testRock);
        String nameAfterDelete = instance.getPetName(testSlot);
        assertNull(nameAfterDelete);
    }

    /**
     * Test of Log method, of class SaveManager.
     */
    @Test
    public void testLog() {
        SaveManager instance = new SaveManager(null);
        String logMsg = "JUnit log test";

        instance.Log(logMsg);
        List<String> logs = instance.getRecentLogs(1);
        assertNotNull(logs);
        assertTrue(logs.get(0).contains("JUnit log test"));
    }

}
