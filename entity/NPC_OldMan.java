package entity;

import excalibur.GamePanel;
import java.util.Random;

public class NPC_OldMan extends Entity {
    
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        
        direction = "down";
        speed = 1;
        name = "Oldman";
        
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        getImage();
        setDialogue();
    }
    
    public void getImage() {
        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }
    
    public void setDialogue() {
        dialogues[0] = "Dönci the Wise:\nHello, lad!";
        dialogues[1] = "Dönci the Wise:\nSo you've come to this land to obtain the might of the [Excalibur].";
        dialogues[2] = "Dönci the Wise:\nThat's why I've been here too... for the past 52 years... Anyway...";
        dialogues[3] = "Dönci the Wise:\nThe sword cannot be approached,\nbecause the path hides unbelievable dangers!";
        dialogues[4] = "Dönci the Wise:\nHo-ho, sounds like a real adventure!\nGood luck little one, may the force be with you...";
        dialogues[5] = "Dönci the Wise:\nLooks like it'll rain...";
        dialogues[6] = "Dönci the Wise:\nNow where did I put my glasses?";
        dialogues[7] = "Dönci the Wise:\nI threw up this morning.\nI guess those mushrooms weren't edible after all...";
        dialogues[8] = "Dönci the Wise:\nTup-tup-tutu... Ah, I love this song!";
        dialogues[9] = "Dönci the Wise:\nDo I know you?";
        dialogues2[0] = "Dönci a bölcs:\nÜdvözlöm, kalandor!";
        dialogues2[1] = "Dönci a bölcs:\nSzóval azért jöttél e földre, hogy megszerezd az [Excalibur]-t.";
        dialogues2[2] = "Dönci a bölcs:\nNos, én is ezért vagyok itt! Már 52 éve... Mindegy is...";
        dialogues2[3] = "Dönci a bölcs:\nA kard megközelíthetetlen,\nmivel az odavezető út rettenetes veszélyeket rejt.";
        dialogues2[4] = "Dönci a bölcs:\nHa-ha, úgy hangzik, mint egy igazi kaland!\nSok sikert ifjú, az erő kísérjen utadon...";
        dialogues2[5] = "Dönci a bölcs:\nÚgy néz ki esni fog...";
        dialogues2[6] = "Dönci a bölcs:\nNa már megint hová tettem a szemüvegem?";
        dialogues2[7] = "Dönci a bölcs:\nAz imént jött vissza a reggeli gombapörkölt.\nTalán mégis galóca volt az a pettyes...";
        dialogues2[8] = "Dönci a bölcs:\nTup-tup-tutu... Ah, kedvemre való ez a zene!";
        dialogues2[9] = "Dönci a bölcs:\nBocsáss meg, te ki is vagy?";
    }
    
    @Override
    public void setAction() {
        
        actionLockCounter++;
        
        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100)+1; // 1-100
            if(i <= 25) {
                direction = "up";
            }
            if(i > 25 && i <= 50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }
            if(i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
    
    @Override
    public void speak() {
        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 5;
        }
        
        if(gp.keyH.languageChange) {
            gp.ui.currentDialogue = dialogues[dialogueIndex];
        } else if(!gp.keyH.languageChange) {
            gp.ui.currentDialogue = dialogues2[dialogueIndex];
        }
        
        dialogueIndex++;
        
        switch(gp.player.direction) {
        case "up":
            direction = "down";
            break;
        case "down":
            direction = "up";
            break;
        case "left":
            direction = "right";
            break;
        case "right":
            direction = "left";
            break;
        }
    }
    
}
