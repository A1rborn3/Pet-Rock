

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ethan
 */
public class DeathChecks {
    
    private Rock rock;
    private final SaveManager saveManager;
    private final GameGUI gameGUI;
    
    public DeathChecks(SaveManager saveManager, GameGUI gameGUI) {
        this.saveManager = saveManager;
        this.gameGUI = gameGUI;
    }
    
    public void CheckStats(){
        if (rock == null) return; //handles the player being in the title screen
        if (rock.hungervalue() <= 0 || rock.happyvalue() <= 0 || rock.fitnessvalue() <= 0 || rock.energyvalue() <= 0 || rock.agevalue() <= 0) {
            handleDeath();
        }

        
    }
    
    public String DeathCause(){
        if (rock == null) return null;
        if (rock.hungervalue() <= 1 ){
            return "hunger";
        }
        else if(rock.energyvalue() <= 1){
            return "exhaustion";
        }
        else if(rock.happyvalue()<= 1){
            return "depression";
        }
        else if(rock.fitnessvalue() <= 1){
            return "organ failure";
        }
        else if (rock.agevalue() <= 1){
            return "old age";
        }
        else return "unknown";
    }
    
    private void handleDeath(){
        gameGUI.getDeathGUI().loadDeath();
        gameGUI.getMain().showCard("deathGUI");
        gameGUI.rock.stopAllDecay();
        saveManager.deleteSave(rock);
        gameGUI.setDeath(true);
        
        
    }
    
    public void setRock(Rock rock) {
        this.rock = rock;

    }
}
