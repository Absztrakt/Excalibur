package monterC;

import entity.Entity;
import excalibur.GamePanel;
import java.util.Random;
import object.OBJ_BronzeCoin;
import object.OBJ_Potion_Red;
import object.OBJ_Rock;


public class MON_GreenSlime extends Entity {
    
    GamePanel gp;
    
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        type = type_monster;
        name = "Green Slime";
        name2 = "Zöld nyálkát";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 3;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);
        
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        getImage();
    }
    
    public void getImage() {
        up1 = setup("/monster/greenslime_down_1", gp.tileSize*2, gp.tileSize*2);
        up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
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
                /*
                int k = new Random().nextInt(100)+1;
                if(k > 99 && !projectile.alive && shotAvailableCounter == 30) {
                    projectile.set(worldX, worldY, direction, true, this);
                    gp.projectileList.add(projectile);
                    shotAvailableCounter = 0;
                }
                */
            }
        }	
    }
    
    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
    
    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100)+1;
        if(i < 50) {
            dropItem(new OBJ_BronzeCoin(gp));
        }
        if(i >= 50 && i < 75) {
            dropItem(new MON_GreenSlime(gp));
        }
        if(i > 75) {
            dropItem(new OBJ_Potion_Red(gp));
        }
    }
    
}
