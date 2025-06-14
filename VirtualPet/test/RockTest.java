/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

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
public class RockTest {

    public RockTest() {
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
     * Test of pet method, of class Rock.
     */
    @Test
    public void testPet() {
        Rock rock = new Rock("TestRock", 50, 50, 50, 50, 1);
        int before = rock.happyvalue();
        rock.pet();
        int after = rock.happyvalue();
        assertEquals(before + 10, after);
    }

    /**
     * Test of feed method, of class Rock.
     */
    @Test
    public void testFeed() {
        Rock rock = new Rock("TestRock", 40, 50, 50, 30, 1);
        int hungerBefore = rock.hungervalue();
        int energyBefore = rock.energyvalue();

        rock.feed();

        assertEquals(hungerBefore + 10, rock.hungervalue());
        assertEquals(energyBefore + 15, rock.energyvalue());
    }

    /**
     * Test of hurt method, of class Rock.
     */
    @Test
    public void testHurt() {
        Rock rock = new Rock("TestRock", 50, 60, 50, 50, 1);
        int before = rock.happyvalue();
        rock.hurt();
        int after = rock.happyvalue();
        assertEquals(before - 40, after);
    }

    /**
     * Test of walk method, of class Rock.
     */
    @Test
    public void testWalk() {
        Rock rock = new Rock("TestRock", 50, 50, 50, 100, 1);
        int fitnessBefore = rock.fitnessvalue();
        int happyBefore = rock.happyvalue();
        int energyBefore = rock.energyvalue();

        rock.walk(100); // 100 units of distance

        assertEquals(fitnessBefore + 10, rock.fitnessvalue()); // 100 / 10
        assertEquals(happyBefore + 10, rock.happyvalue());
        assertEquals(energyBefore - 5, rock.energyvalue());     // 100 / 20
    }

}
