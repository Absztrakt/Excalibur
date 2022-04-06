package entity;

import excalibur.GamePanel;
import java.util.Random;


public class ANIMAL_Cat extends Entity{
    
    public ANIMAL_Cat(GamePanel gp) {
        super(gp);
        
        direction = "down";
        speed = 1;
        name = "Cat";
        
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 30;
        solidArea.height = 20;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        getImage();
    }
    
    final public void getImage() {
        up1 = setup2("/animals/feketecica_hatra_1");
        up2 = setup2("/animals/feketecica_hatra_2");
        down1 = setup2("/animals/feketecica_elore_1");
        down2 = setup2("/animals/feketecica_elore_2");
        left1 = setup2("/animals/feketecica_balra_1");
        left2 = setup2("/animals/feketecica_balra_2");
        right1 = setup2("/animals/feketecica_jobbra_1");
        right2 = setup2("/animals/feketecica_jobbra_2");
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
    
}
