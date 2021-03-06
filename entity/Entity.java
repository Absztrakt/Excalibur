package entity;

import excalibur.GamePanel;
import excalibur.UtilityTool;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Entity {
    
    GamePanel gp;
    public BufferedImage image, image2, image3;
    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;
    String dialogues[] = new String[20];
    String dialogues2[] = new String[20];
    
    // STATE
    public int worldX;
    public int worldY;
    public int spriteNum  = 1;
    int dialogueIndex = 0;
    public String direction = "down";
    public boolean collision = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean cannotPickUp = false;
    
    // COUNTER
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int spriteCounter = 0;
    public int dyingCounter = 0;
    int hpBarCounter = 0;
    public int shotAvailableCounter = 0;
    public int cannotPickUpCounter = 0;
    public int changeDirection1 = 0;
    public int changeDirection2 = 0;
    public int changeDirection3 = 0;
    public int changeDirection4 = 0;


    
    // CHARACTER ATTRIBUTES
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int speed;
    public String name;
    public String name2;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;
    
    // ITEM ATTRIBUTES
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public String description2 = "";
    public boolean unlocked = false;
    public int useCost;
    
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword_normal = 3;
    public final int type_sword_long = 4;
    public final int type_shield_normal = 5;
    public final int type_consumable = 6;
    public final int type_axe = 7;
    public final int type_key_normal = 8;
    public final int type_key_bronz = 9;
    public final int type_key_silver = 10;
    public final int type_key_gold = 11;
    public final int type_key_diamond = 12;
    public final int type_door_normal = 13;
    public final int type_chest_normal = 14;
    public final int type_pickUpOnly = 15;
      
    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setAction() {}
    public void damageReaction() {}
    public void use(Entity entity) {}
    public void speak() {}
    public void speak2() {}
    public void checkDrop() {}
    public void dropItem(Entity droppedItem) {
        for(int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] == null) {
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX;
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }
    
    public void update() {
        setAction();
        
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        
        if(this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }
        
        if(!collisionOn) {
            switch(direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
            }
        }
        
        if(type == 2 && name.equals("Orange Spider")) {
            spriteCounter++;
            if(spriteCounter > 8) {
                switch (spriteNum) {
                    case 1 -> spriteNum = 2;
                    case 2 -> spriteNum = 3;
                    case 3 -> spriteNum = 1;
                    default -> {
                    }
                }
                spriteCounter = 0;
            }
        } else {
            spriteCounter++;
            if(spriteCounter > 8) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                } else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }
    
    public void damagePlayer(int attack) {
        if(!gp.player.invincible) {
            gp.playSE(8);
            int damage = attack - gp.player.defense;
            if(damage < 0) {
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;  
        
        if(type == 2 && name.equals("Orange Spider")) {
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX - gp.tileSize*15 && 
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX + gp.tileSize*15 && 
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY - gp.tileSize*15 &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY + gp.tileSize*15) {  
                switch(direction) {
                case "up":
                    if(spriteNum == 1) {
                        image = up1;
                    }
                    if(spriteNum == 2) {
                        image = up2;
                    }
                    if(spriteNum == 3) {
                        image = up3;
                    }
                    break;
                case "down":
                    if(spriteNum == 1) {
                        image = down1;
                    }
                    if(spriteNum == 2) {
                        image = down2;
                    }
                    if(spriteNum == 3) {
                        image = down3;
                    }
                    break;
                case "left":
                    if(spriteNum == 1) {
                        image = left1;
                    }
                    if(spriteNum == 2) {
                        image = left2;
                    }
                    if(spriteNum == 3) {
                        image = left3;
                    }
                    break;
                case "right":
                    if(spriteNum == 1) {
                        image = right1;
                    }
                    if(spriteNum == 2) {
                        image = right2;
                    }
                    if(spriteNum == 3) {
                        image = right3;
                    }
                    break;
                }

                int x = screenX;
                int y = screenY;

                if(screenX > worldX) {
                    x = worldX;
                }
                if(screenY > worldY) {
                    y = worldY;
                }
                int rightOffset = gp.screenWidth - screenX;
                if(rightOffset > gp.worldWidth - worldX) {
                    x = gp.screenWidth - (gp.worldWidth - worldX);
                }
                int bottomOffset  = gp.screenHeight - screenY;
                if(bottomOffset > gp.worldHeight - worldY) {
                    y = gp.screenHeight - (gp.worldHeight - worldY);
                }

                if(type == 2 && hpBarOn) {
                    double oneScale = (double)gp.tileSize/maxLife;
                    double hpBarValue = oneScale*life;

                    if(hpBarValue >= 0) {
                        g2.setColor(Color.black);
                        g2.fillRect(x - 1, y - 16, gp.tileSize+2, 12);
                        g2.setColor(Color.red);
                        g2.fillRect(x, y - 15, (int)hpBarValue, 10);
                    } else {
                        g2.setColor(Color.black);
                        g2.fillRect(x - 1, y - 16, gp.tileSize+2, 12);
                    }                

                    hpBarCounter++;

                    if(hpBarCounter > 600) {
                        hpBarCounter = 0;
                        hpBarOn = false;
                    }
                }

                if(invincible) {
                    hpBarOn = true;
                    hpBarCounter = 0;
                    changeAlpha(g2, 0.4f);
                }
                if(dying) {
                    dyingAnimation(g2);
                }
                
                g2.drawImage(image, x, y, 180, 140, null);

                changeAlpha(g2, 1f);
            }
        } else {
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX - gp.tileSize*15 && 
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX + gp.tileSize*15 && 
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY - gp.tileSize*15 &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY + gp.tileSize*15) {  
                switch(direction) {
                case "up":
                    if(spriteNum == 1) {
                        image = up1;
                    }
                    if(spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if(spriteNum == 1) {
                        image = down1;
                    }
                    if(spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if(spriteNum == 1) {
                        image = left1;
                    }
                    if(spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNum == 1) {
                        image = right1;
                    }
                    if(spriteNum == 2) {
                        image = right2;
                    }
                    break;
                }

                int x = screenX;
                int y = screenY;

                if(screenX > worldX) {
                    x = worldX;
                }
                if(screenY > worldY) {
                    y = worldY;
                }
                int rightOffset = gp.screenWidth - screenX;
                if(rightOffset > gp.worldWidth - worldX) {
                    x = gp.screenWidth - (gp.worldWidth - worldX);
                }
                int bottomOffset  = gp.screenHeight - screenY;
                if(bottomOffset > gp.worldHeight - worldY) {
                    y = gp.screenHeight - (gp.worldHeight - worldY);
                }

                if(type == 2 && hpBarOn) {
                    double oneScale = (double)gp.tileSize/maxLife;
                    double hpBarValue = oneScale*life;

                    if(hpBarValue >= 0) {
                        g2.setColor(Color.black);
                        g2.fillRect(x - 1, y - 16, gp.tileSize+2, 12);
                        g2.setColor(Color.red);
                        g2.fillRect(x, y - 15, (int)hpBarValue, 10);
                    } else {
                        g2.setColor(Color.black);
                        g2.fillRect(x - 1, y - 16, gp.tileSize+2, 12);
                    }                

                    hpBarCounter++;

                    if(hpBarCounter > 600) {
                        hpBarCounter = 0;
                        hpBarOn = false;
                    }
                }

                if(invincible) {
                    hpBarOn = true;
                    hpBarCounter = 0;
                    changeAlpha(g2, 0.4f);
                }
                if(dying) {
                    dyingAnimation(g2);
                }

                g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

                changeAlpha(g2, 1f);
            }
        }
    }
    
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    
    public BufferedImage setup2(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        if(dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i && dyingCounter <= i*2) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i*2 && dyingCounter <= i*3) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i*3 && dyingCounter <= i*4) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i*4 && dyingCounter <= i*5) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i*5 && dyingCounter <= i*6) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i*6 && dyingCounter <= i*7) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i*7 && dyingCounter <= i*8) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 40) {
            alive = false;
        }
    }
    
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    
}
