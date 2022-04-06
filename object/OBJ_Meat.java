package object;

import entity.Entity;
import excalibur.GamePanel;


public class OBJ_Meat extends Entity {
    
    GamePanel gp;
    
    public OBJ_Meat(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        name = "Meat";
        name2 = "Hús";
        type = type_consumable;
        description = "[" + name + "]" + "\nAn unkown delicious looking meat.\nIt restores 1 health.";
        description2 = "[" + name2 + "]" + "\nEgy ismeretlen, finomnak tűnő hús.\nVisszaállít 1 életet.";
        down1 = setup("/objects/meat1_chopped", gp.tileSize, gp.tileSize);
    }
    
    @Override
    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        
        if(gp.player.life < gp.player.maxLife-1) {
            if(gp.keyH.languageChange) {
                gp.ui.currentDialogue = "You ate the [" + name + "].\nYou start to feel better!";
            } else if(!gp.keyH.languageChange) {
                gp.ui.currentDialogue = "Megetted a [" + name2 + "]-t.\nKezded jobban érezni magad!";
            }
            entity.life += 2;
        } else if(gp.player.life == gp.player.maxLife-1) {
            if(gp.keyH.languageChange) {
                gp.ui.currentDialogue = "You ate the [" + name + "].\nYou start to feel better!";
            } else if(!gp.keyH.languageChange) {
                gp.ui.currentDialogue = "Megetted a [" + name2 + "]-t.\nKezded jobban érezni magad!";
            }
            gp.player.life = gp.player.maxLife;
        } else if(gp.player.life >= gp.player.maxLife) {
            if(gp.keyH.languageChange) {
                gp.ui.currentDialogue = "You ate the [" + name + "].\nNothing seems to happen...";
            } else if(!gp.keyH.languageChange) {
                gp.ui.currentDialogue = "Megetted a [" + name2 + "]-t.\nNem tapasztalsz változást...";
            }
            gp.player.life = gp.player.maxLife;
        }
        gp.playSE(3);
    }
}
