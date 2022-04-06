package entity;

import excalibur.GamePanel;
import excalibur.KeyHandler;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import object.OBJ_Axe;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Meat;
import object.OBJ_Potion_Purple;
import object.OBJ_Potion_Red;
import object.OBJ_Potion_Water;
import object.OBJ_Rock;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Long;
import object.OBJ_Sword_Normal;


public class Player extends Entity{
    
    KeyHandler keyH;
    public int screenX, screenY;
    public final int inventorySize = 28;
    public boolean attackCanceled = false;
    public boolean newGameStatus = true;
    public ArrayList<Entity> inventory = new ArrayList();
    public int characterType = 0;
    
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = 8;
        solidAreaDefaultY = 16;
        solidArea.height = 32;
        solidArea.width = 32;
        
        
        setDefaultValues();
        setItems();
        getPlayerImage();
        getPlayerAttackImages();
        
    }
    
    public void setDefaultValues() {
        worldX = gp.tileSize * 3;
        worldY = gp.tileSize * 3;
        speed = 3;
        direction = "down";
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 10;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        //projectile = new OBJ_Rock(gp);
        attack = getAttack();
        defense = getDefense();
    }
    
    public void setSavedValues(int worldX, int worldY, String direction, int level, int life, int maxLife, int strength, int dexterity, int exp, int nextLevelExp, int coin, int characterType, String currentWeapon, String currentShield) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.level = level;
        this.maxLife = maxLife;
        this.strength = strength;
        this.dexterity = dexterity;
        this.exp = exp;
        this.nextLevelExp = nextLevelExp;
        this.coin = coin;
        this.characterType = characterType;
        if(currentWeapon.equals("Normal Sword")) {
            this.currentWeapon = new OBJ_Sword_Normal(gp);
        }
        if(currentWeapon.equals("Long Sword")) {
            this.currentWeapon = new OBJ_Sword_Long(gp);
        }
        if(currentWeapon.equals("Axe")) {
            this.currentWeapon = new OBJ_Axe(gp);
        }
        if(currentShield.equals("Wooden Shield")) {
            this.currentShield = new OBJ_Shield_Wood(gp);
        }
        
        attack = getAttack();
        defense = getDefense();
    }
    
    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield); 
    }
    
    public void setSavedItems(ArrayList<String> ar) {
        inventory.clear();
        for(int i = 0; i < ar.size(); i++) {
            if(ar.get(i).equals("Key")) {
                inventory.add(new OBJ_Key(gp));
            } else if(ar.get(i).equals("Red Potion")) {
                inventory.add(new OBJ_Potion_Red(gp));
            } else if(ar.get(i).equals("Purple Potion")) {
                inventory.add(new OBJ_Potion_Purple(gp));
            } else if(ar.get(i).equals("Water in bottle")) {
                inventory.add(new OBJ_Potion_Water(gp));
            } else if(ar.get(i).equals("Meat")) {
                inventory.add(new OBJ_Meat(gp));
            } else if(ar.get(i).equals("Wooden Shield")) {
                inventory.add(new OBJ_Shield_Wood(gp));
            } else if(ar.get(i).equals("Normal Sword")) {
                inventory.add(new OBJ_Sword_Normal(gp));
            } else if(ar.get(i).equals("Long Sword")) {
                inventory.add(new OBJ_Sword_Long(gp));
            } else if(ar.get(i).equals("Axe")) {
                inventory.add(new OBJ_Axe(gp));
            }
        }
    }
    
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndex();
        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            
            if(selectedItem != currentWeapon) {
                if(selectedItem.type == type_sword_normal || selectedItem.type == type_sword_long || selectedItem.type == type_axe) {
                    currentWeapon = selectedItem;
                    attack = getAttack();
                    getPlayerAttackImages();
                    if(gp.keyH.SEOnOff) {
                        gp.playSE(10);
                    }
                }
            }
            
            if(selectedItem != currentShield) {
                if(selectedItem.type == type_shield_normal) {
                    currentShield = selectedItem;
                    defense = getDefense();
                    if(gp.keyH.SEOnOff) {
                        gp.playSE(10);
                    }
                }       
            }
            
            if(selectedItem.type == type_consumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    
    public void getPlayerImage() {
        if(characterType == 1) {
            up1 = setup("/player/player_hatra_1", gp.tileSize, gp.tileSize);
            up2 = setup("/player/player_hatra_2", gp.tileSize, gp.tileSize);
            down1 = setup("/player/player_elore_1", gp.tileSize, gp.tileSize);
            down2 = setup("/player/player_elore_2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/player_balra_1", gp.tileSize, gp.tileSize);
            left2 = setup("/player/player_balra_2", gp.tileSize, gp.tileSize);
            right1 = setup("/player/player_jobbra_1", gp.tileSize, gp.tileSize);
            right2 = setup("/player/player_jobbra_2", gp.tileSize, gp.tileSize);
        } else if(characterType == 2)  {
            up1 = setup("/player/player2_hatra_1", gp.tileSize, gp.tileSize);
            up2 = setup("/player/player2_hatra_2", gp.tileSize, gp.tileSize);
            down1 = setup("/player/player2_elore_1", gp.tileSize, gp.tileSize);
            down2 = setup("/player/player2_elore_2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/player2_balra_1", gp.tileSize, gp.tileSize);
            left2 = setup("/player/player2_balra_2", gp.tileSize, gp.tileSize);
            right1 = setup("/player/player2_jobbra_1", gp.tileSize, gp.tileSize);
            right2 = setup("/player/player2_jobbra_2", gp.tileSize, gp.tileSize);
        }
        
    }
    
    public void getPlayerAttackImages() {
        if(currentWeapon.type == type_sword_normal) {
            attackUp1 = setup("/player/player_hatra_1_tamad", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/player_hatra_2_tamad", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/player_elore_1_tamad", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/player_elore_2_tamad", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/player_balra_1_tamad", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/player_balra_2_tamad", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/player_jobbra_1_tamad", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/player_jobbra_2_tamad", gp.tileSize*2, gp.tileSize);
        } else if(currentWeapon.type == type_sword_long) {
            attackUp1 = setup("/player/player_hatra_1_tamad_long_sord", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/player_hatra_2_tamad_long_sord", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/player_elore_1_tamad_long_sord", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/player_elore_2_tamad_long_sord", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/player_balra_1_tamad_long_sord", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/player_balra_2_tamad_long_sord", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/player_jobbra_1_tamad_long_sord", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/player_jobbra_2_tamad_long_sord", gp.tileSize*2, gp.tileSize);
        } else if(currentWeapon.type == type_axe) {
            attackUp1 = setup("/player/player_hatra_1_tamad_axe", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/player_hatra_2_tamad_axe", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/player_elore_1_tamad_axe", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/player_elore_2_tamad_axe", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/player_balra_1_tamad_axe", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/player_balra_2_tamad_axe", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/player_jobbra_1_tamad_axe", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/player_jobbra_2_tamad_axe", gp.tileSize*2, gp.tileSize);
        }
        
    }
    
    @Override
    public void update() {
        
        if(attacking) {
            attacking();
        } else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
            if(keyH.upPressed) {
                direction = "up";
            }
            if(keyH.downPressed) {
                direction = "down";
            }
            if(keyH.leftPressed) {
                direction = "left";
            }
            if(keyH.rightPressed) {
                direction = "right";
            }
            if(keyH.shiftPressed) {
                speed = 10;
                spriteCounter++;
                if(spriteCounter > 20) {
                    if(spriteNum == 1) {
                        spriteNum = 2;
                    } else if(spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }
            if(!keyH.shiftPressed) {
                speed = 3;
            }
            
            collisionOn = false;
            gp.cChecker.checkTile(this);
            
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
            
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);
            
            gp.eHandler.checkEvent();
            
            if(!collisionOn && !keyH.enterPressed) {
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
            
            if(keyH.enterPressed && !attackCanceled) {
                if(gp.keyH.SEOnOff) {
                    gp.playSE(9);
                }
                attacking = true;
                spriteCounter = 0;
            }
            
            attackCanceled = false;
            
            gp.keyH.enterPressed = false;
            
            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                } else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        
        if(gp.keyH.shotKeyPressed && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveResource(this)) {
            projectile.set(worldX, worldY, direction, true, this);
            projectile.substractResource(this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
            gp.playSE(13);
        }
        
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        
        if(cannotPickUp == true) {
            cannotPickUpCounter++;
            if(cannotPickUpCounter > 60) {
                cannotPickUp = false;
                cannotPickUpCounter = 0;
            }
        }
        
        if(life > maxLife) {
            life = maxLife;
        }
        if(mana > maxMana) {
            mana = maxMana;
        }
    }
    
    public void pickUpObject(int i) {
        if(i != 999) {
            if(gp.obj[i].type == type_pickUpOnly) {
                gp.obj[i].use(this);
                gp.obj[i] = null;
            } else {
                String text = "";
                if(inventory.size() != inventorySize) {
                    inventory.add(gp.obj[i]);
                    if(gp.keyH.SEOnOff) {
                        gp.playSE(1);
                    }
                    if(keyH.languageChange) {
                        text = "Picked up: [" + gp.obj[i].name + "]";
                    } else if(!keyH.languageChange) {
                        text = "Megszerezve: [" + gp.obj[i].name2 + "]";
                    }
                    gp.obj[i] = null;
                } else {
                    if(!cannotPickUp) {
                        if(keyH.languageChange) {
                            text = "You cannot carry more items!";
                        } else if(!keyH.languageChange) {
                            text = "Nem tudsz több dolgot cipelni!";
                        }
                        cannotPickUp = true;
                    }
                }
                gp.ui.addMessage(text);
            }
            
        }
    }
    
    @Override
    public void draw(Graphics2D g2) {
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;
        int tmpScreenX = screenX, tmpScreenY = screenY;
        
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
        
        switch(direction) {
        case "up":
            if(!attacking) {
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
            }
            if(attacking) {
                y -= gp.tileSize;
                if(spriteNum == 1) {
                    image = attackUp1;
                }
                if(spriteNum == 2) {
                    image = attackUp2;
                }
            }
            break;
        case "down":
            if(!attacking) {
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
            }
            if(attacking) {
                if(spriteNum == 1) {
                    image = attackDown1;
                }
                if(spriteNum == 2) {
                    image = attackDown2;
                }
            }
            break;
        case "left":
            if(!attacking) {
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
            }
            if(attacking) {
                x -= gp.tileSize;
                if(spriteNum == 1) {
                    image = attackLeft1;
                }
                if(spriteNum == 2) {
                    image = attackLeft2;
                }
            }
            break;
        case "right":
            if(!attacking) {
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
            }
            if(attacking) {
                if(spriteNum == 1) {
                    image = attackRight1;
                }
                if(spriteNum == 2) {
                    image = attackRight2;
                }
            }
            break;
        }
        
        
            
        if(invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, x, y, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        // DEBUG
        /*g2.setFont(new Font("Arial", Font.PLAIN, 40));
        g2.setColor(Color.white);
        g2.drawString("Invincible: " + invincibleCounter, 10, 400);*/
    }

    private void interactNPC(int i) {
        if(gp.keyH.enterPressed) {
            if(i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    private void contactMonster(int i) {
        if(i != 999) {
            if(!invincible && !gp.monster[i].dying) {
                if(gp.keyH.SEOnOff) {
                    gp.playSE(8);
                }
                int damage = gp.monster[i].attack - defense;
                if(damage < 0) {
                    damage = 0;
                }
                if(life <= 0) {
                    life = 0;
                } else {
                    life -= damage;
                }
                invincible = true;
            }
        }
    }

    private void attacking() {
        spriteCounter++;
        if(spriteCounter <= 10) {
            spriteNum = 1;
        }
        if(spriteCounter > 10 && spriteCounter <= 20) {
            spriteNum = 2;
            
            // Hitbox kipozícionáláshoz
            int currentWorldX = (int) worldX;
            int currentWorldY = (int) worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            
            switch(direction) {
            case "up": 
                worldY -= attackArea.height;
                break;
            case "down": 
                worldY += attackArea.height;
                break;
            case "left": 
                worldX -= attackArea.width;
                break;
            case "right": 
                worldX += attackArea.width;
                break;
            }
            
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);
            
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 20) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void damageMonster(int i, int attack) {
        if(i != 999) {
            if(!gp.monster[i].invincible) {
                if(gp.keyH.SEOnOff) {
                    gp.playSE(6);
                }
                int damage = attack - gp.monster[i].defense;
                if(damage < 0) {
                    damage = 0;
                }
                gp.monster[i].life -= damage;
                if(keyH.languageChange) {
                    gp.ui.addMessage(damage + " damage!");
                } else if(!keyH.languageChange) {
                    gp.ui.addMessage(damage + " sebzés!");
                }
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                if(gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                    if(keyH.languageChange) {
                        gp.ui.addMessage("Killed a(n) " + gp.monster[i].name + "!");
                        gp.ui.addMessage(gp.monster[i].exp + " experience gained!");
                    } else if(!keyH.languageChange) {
                        gp.ui.addMessage("Megöltél egy " + gp.monster[i].name2 + "!");
                        gp.ui.addMessage(gp.monster[i].exp + " tapasztalatot szereztél!");
                    }
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    private int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength + currentWeapon.attackValue;
    }

    private int getDefense() {
        return defense = dexterity + currentShield.defenseValue;
    }

    private void checkLevelUp() {
        if(exp >= nextLevelExp) {
            level++;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            nextLevelExp *= 2;
            if(gp.keyH.SEOnOff) {
                gp.playSE(7);
            }
            gp.gameState = gp.dialogueState;
            if(level == 5 || level == 10) {
                if(keyH.languageChange) {
                    gp.ui.currentDialogue = "You level upped to level " + level + "!\nYou gained 1 health!";
                } else if(!keyH.languageChange) {
                    gp.ui.currentDialogue = "Szintet léptél, új szinted " + level + "!\nKaptál 1 életet!";
                }
                maxLife += 2;
            } else {
                if(keyH.languageChange) {
                    gp.ui.currentDialogue = "You level upped to level " + level + "!";
                } else if(!keyH.languageChange) {
                    gp.ui.currentDialogue = "Szintet léptél, új szinted " + level + "!";
                }
            }

        }
    }
    
}
