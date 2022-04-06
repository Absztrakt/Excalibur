package object;

import entity.Entity;
import excalibur.GamePanel;


public class OBJ_Potion_Purple extends Entity {
    GamePanel gp;
    
    public OBJ_Potion_Purple(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        name = "Purple Potion";
        name2 = "Lila ital";
        type = type_consumable;
        description = "[" + name + "]" + "\nA foul purple liquid.\nLooks like it's moving.\nUnkown effects.";
        description2 = "[" + name2 + "]" + "\nEgy undorító, lila folyadék.\nOlyan mintha mozogna.\nIsmeretlen hatások.";
        down1 = setup("/objects/potion_purple", gp.tileSize, gp.tileSize);
    }
     
    @Override
    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        
        if(gp.player.life > 2) {
            if(gp.keyH.languageChange) {
                gp.ui.currentDialogue = "You drank the [" + name + "].\nYou start to feel dizzy!";
            } else if(!gp.keyH.languageChange) {
                gp.ui.currentDialogue = "Megittad a [" + name2 + "]-t.\nKezdesz szédülni!";
            }
            entity.life -= 2;
        } else if(gp.player.life <= 2) {
            if(gp.keyH.languageChange) {
                gp.ui.currentDialogue = "You drank the [" + name + "].\nYou feel it burn through your throat!";
            } else if(!gp.keyH.languageChange) {
                gp.ui.currentDialogue = "Megittad a [" + name2 + "]-t.\nÉrzed, ahogy átégeti magát a torkodon!";
            }
            gp.player.life = 0;
        }
        if(gp.keyH.SEOnOff) {
            gp.playSE(8);
        }
    }
}
