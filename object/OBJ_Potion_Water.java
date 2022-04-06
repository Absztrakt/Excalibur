package object;

import entity.Entity;
import excalibur.GamePanel;


public class OBJ_Potion_Water extends Entity{
    GamePanel gp;
    
    public OBJ_Potion_Water(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        name = "Water in bottle";
        name2 = "Vizes fiola";
        type = type_consumable;
        description = "[" + name + "]" + "\nA bottle of filtered water.\nIt restores 1 health.";
        description2 = "[" + name2 + "]" + "\nEgy fiolányi víz.\nVisszaállít 1 életet.";
        down1 = setup("/objects/potion_water", gp.tileSize, gp.tileSize);
    }
     
    @Override
    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        
        if(gp.player.life < gp.player.maxLife) {
            if(gp.keyH.languageChange) {
                gp.ui.currentDialogue = "You drank the water from the bottle.\nYou start to feel better!";
            } else if(!gp.keyH.languageChange) {
                gp.ui.currentDialogue = "Megittad a vizet a fiolából.\nKezded jobban érezni magad!";
            }
            entity.life += 1;
        } else if(gp.player.life >= gp.player.maxLife) {
            if(gp.keyH.languageChange) {
                gp.ui.currentDialogue = "You drank the water from the bottle.\nNothing seems to happen...";
            } else if(!gp.keyH.languageChange) {
                gp.ui.currentDialogue = "Megittad a vizet a fiolából.\nNem tapasztalsz változást...";
            }
            gp.player.life = gp.player.maxLife;
        }
        if(gp.keyH.SEOnOff) {
            gp.playSE(3);
        }
    }
}
