package entity;

import excalibur.GamePanel;


public class Projectile extends Entity {
    
    Entity user;
    
    public Projectile(GamePanel gp) {
        super(gp);
    }
    
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }
    
    @Override
    public void update() {
        
        if(user == gp.player) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if(monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, attack);
                alive = false;
            }
        }
        if(user != gp.player) {
            boolean contactPlayer =gp.cChecker.checkPlayer(this);
            if(!gp.player.invincible && contactPlayer) {
                damagePlayer(attack);
                alive = false;
            }
        }
        
        switch(direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }
        
        life--;
        if(life <= 0) {
            alive = false;
        }
        
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
    
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        return haveResource;
    }
    
    public void substractResource(Entity user) { }
    
}
