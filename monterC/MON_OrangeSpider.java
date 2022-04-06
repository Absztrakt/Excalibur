package monterC;

import entity.Entity;
import excalibur.GamePanel;
import java.util.Random;


public class MON_OrangeSpider extends Entity{
    GamePanel gp;
    
    public MON_OrangeSpider(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        type = type_monster;
        name = "Orange Spider";
        name2 = "Narancspókot";
        speed = 2;
        maxLife = 50;
        life = maxLife;
        attack = 5;
        defense = 1;
        exp = 10;
        
        solidArea.x = 5;
        solidArea.y = 5;
        solidArea.width = 170;
        solidArea.height = 130;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        getImage();
    }
    
    public void getImage() {
        up1 = setup2("/monster/narancspok_balra_1");
        up2 = setup2("/monster/narancspok_balra_2");
        up3 = setup2("/monster/narancspok_balra_3");
        down1 = setup2("/monster/narancspok_jobbra_1");
        down2 = setup2("/monster/narancspok_jobbra_2");
        down3 = setup2("/monster/narancspok_jobbra_3");
        left1 = setup2("/monster/narancspok_balra_1");
        left2 = setup2("/monster/narancspok_balra_2");
        left3 = setup2("/monster/narancspok_balra_3");
        right1 = setup2("/monster/narancspok_jobbra_1");
        right2 = setup2("/monster/narancspok_jobbra_2");
        right3 = setup2("/monster/narancspok_jobbra_3");
    }
    
    @Override
    public void setAction() {
        int xDistance;
        int yDistance;
        int Distance;

        for(int i = 0; i < gp.monster.length; i++) {
            if(gp.monster[i] != null) {
                xDistance = Math.abs(gp.player.worldX - gp.monster[i].worldX);
                yDistance = Math.abs(gp.player.worldY - gp.monster[i].worldY);
                Distance = Math.max(xDistance, yDistance);
                if(gp.monster[i].alive == true && gp.monster[i].dying ==false) {
                    if(Distance < 4*gp.tileSize) {
                        if(gp.player.worldX > gp.monster[i].worldX && gp.player.worldY > gp.monster[i].worldY) {
                            if(changeDirection1 < 30) {
                                gp.monster[i].direction = "right";
                            } else if(changeDirection1 >=45 && changeDirection1 < 90) {
                                gp.monster[i].direction = "down";
                            } else if(changeDirection1 > 90){
                                changeDirection1 = 0;
                            }
                            changeDirection1++;
                        } else if(gp.player.worldX > gp.monster[i].worldX && gp.player.worldY < gp.monster[i].worldY) {
                            if(changeDirection2 < 45) {
                                gp.monster[i].direction = "right";
                            } else if(changeDirection2 >=45 && changeDirection2 < 90) {
                                gp.monster[i].direction = "up";
                            } else if(changeDirection2 > 90){
                                changeDirection2 = 0;
                            }
                            changeDirection2++;
                        } else if(gp.player.worldX < gp.monster[i].worldX && gp.player.worldY > gp.monster[i].worldY) {
                            if(changeDirection3 < 45) {
                                gp.monster[i].direction = "left";
                            } else if(changeDirection3 >=45 && changeDirection3 < 90) {
                                gp.monster[i].direction = "down";
                            } else if(changeDirection3 > 90){
                                changeDirection3 = 0;
                            }
                            changeDirection3++;
                        } else if(gp.player.worldX < gp.monster[i].worldX && gp.player.worldY < gp.monster[i].worldY) {
                            if(changeDirection4 < 45) {
                                gp.monster[i].direction = "left";
                            } else if(changeDirection4 >=45 && changeDirection4 < 90) {
                                gp.monster[i].direction = "up";
                            } else if(changeDirection4 > 90){
                                changeDirection4 = 0;
                            }
                            changeDirection4++;
                        } else if(gp.player.worldX == gp.monster[i].worldX && gp.player.worldY < gp.monster[i].worldY) {
                            gp.monster[i].direction = "up";
                        } else if(gp.player.worldX == gp.monster[i].worldX && gp.player.worldY > gp.monster[i].worldY) {
                            gp.monster[i].direction = "down";
                        } else if(gp.player.worldX < gp.monster[i].worldX && gp.player.worldY == gp.monster[i].worldY) {
                            gp.monster[i].direction = "left";
                        } else if(gp.player.worldX > gp.monster[i].worldX && gp.player.worldY == gp.monster[i].worldY) {
                            gp.monster[i].direction = "right";
                        }
                    } else {
                        actionLockCounter++;
                        if(actionLockCounter == 600) {
                            Random random = new Random();
                            int a = random.nextInt(100)+1; //picks up a number from 1 to 100
                            if(a <= 25) {
                                direction = "up";
                            } else if(a > 25 && a <= 50) {
                                direction = "left";
                            } else if(a > 50 && a <= 75) {
                                direction = "down";
                            } else if(a > 75 && a <= 100) {
                                direction = "right";
                            }
                            actionLockCounter = 0;
                        }
                    }
                }				
            }
        }	
    }
    
    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}
