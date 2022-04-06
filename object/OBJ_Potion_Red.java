package object;

import entity.Entity;
import excalibur.GamePanel;


public class OBJ_Potion_Red extends Entity{
    
    GamePanel gp;
    
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        name = "Red Potion";
        name2 = "Vörös ital";
        type = type_consumable;
        value = 5;
        description = "[" + name + "]" + "\nA lustrous red liquid.\nIt restores 2 health.";
        description2 = "[" + name2 + "]" + "\nEgy csillogó, vörös folyadék.\nVisszaállít 2 életet.";
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
    }
     
    @Override
    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        
        if(gp.player.life < gp.player.maxLife - 1) {
            if(gp.keyH.languageChange) {
                gp.ui.currentDialogue = "You drank the [" + name + "].\nYou start to feel better!";
            } else if(!gp.keyH.languageChange) {
                gp.ui.currentDialogue = "Megittad a [" + name2 + "]-t.\nKezded jobban érezni magad!";
            }
            entity.life += 2;
        } else if(gp.player.life == gp.player.maxLife - 1) {
            if(gp.keyH.languageChange) {
                gp.ui.currentDialogue = "You drank the [" + name + "].\nYou start to feel better!";
            } else if(!gp.keyH.languageChange) {
                gp.ui.currentDialogue = "Megittad a [" + name2 + "]-t.\nKezded jobban érezni magad!";
            }
            gp.player.life = gp.player.maxLife;
        } else if(gp.player.life >= gp.player.maxLife) {
            if(gp.keyH.languageChange) {
                gp.ui.currentDialogue = "You drank the [" + name + "].\nNothing seems to happen...";
            } else if(!gp.keyH.languageChange) {
                gp.ui.currentDialogue = "Megittad a [" + name2 + "]-t.\nNem tapasztalsz változást...";
            }
            gp.player.life = gp.player.maxLife;
        }
        if(gp.keyH.SEOnOff) {
            gp.playSE(3);
        }
    }
}
