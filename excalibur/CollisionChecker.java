package excalibur;

import entity.Entity;


public class CollisionChecker {
    
    GamePanel gp;
    boolean hasNormalKey = false;
    int tmp = 0;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    
    public void checkTile(Entity entity) {
        int entityLeftWorldX = (int) (entity.worldX + entity.solidArea.x);
        int entityRightWorldX = (int) (entity.worldX + entity.solidArea.x + entity.solidArea.width);
        int entityTopWorldY = (int) (entity.worldY + entity.solidArea.y);
        int entityBottomWorldY = (int) (entity.worldY + entity.solidArea.y + entity.solidArea.height);
        
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;
        
        int tileNum1, tileNum2;
        
        switch(entity.direction) {
        case "up":
            entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "down":
            entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "left":
            entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "right":
            entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        }
    }
    
    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        
        for(int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] != null) {
                // Entitás szilárd részének helyzete
                entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
                entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);
                
                // Tárgy szilárd részének helyzete
                gp.obj[i].solidArea.x = (int) (gp.obj[i].worldX + gp.obj[i].solidArea.x);
                gp.obj[i].solidArea.y = (int) (gp.obj[i].worldY + gp.obj[i].solidArea.y);
                
                switch(entity.direction) {
                case "up" -> entity.solidArea.y -= entity.speed;
                case "down" -> entity.solidArea.y += entity.speed;
                case "left" -> entity.solidArea.x -= entity.speed;
                case "right" -> entity.solidArea.x += entity.speed;
                }
                
                if(gp.obj[i].type == 13 || gp.obj[i].type == 14) {
                    for (int j = 0; j < gp.player.inventory.size(); j++) {
                        if(gp.player.inventory.get(j).type == 8) {
                            hasNormalKey = true;
                            tmp = j;
                            break;
                        }
                    }
                    if(entity.solidArea.intersects(gp.obj[i].solidArea) && hasNormalKey && gp.obj[i].type == 13) {
                        gp.obj[i].down1 = gp.obj[i].setup("/objects/door_normal_opened", gp.tileSize, gp.tileSize);
                        gp.obj[i].collision = false;
                        gp.obj[i].solidArea.width = 0;
                        gp.obj[i].solidArea.height = 0;
                        gp.player.inventory.remove(tmp);
                    }
                    if(entity.solidArea.intersects(gp.obj[i].solidArea) && hasNormalKey && gp.obj[i].type == 14) {
                        gp.obj[i].down1 = gp.obj[i].setup("/objects/chest1_opened", gp.tileSize, gp.tileSize);
                        gp.obj[i].collision = false;
                        gp.obj[i].solidArea.width = 0;
                        gp.obj[i].solidArea.height = 0;
                        gp.player.inventory.remove(tmp);
                    }
                }
                if(gp.obj[i] != null) {
                    if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
                        if(gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if(player && gp.obj[i].type != 13 && gp.obj[i].type != 14) {
                            index = i;
                        }
                    }
                }
                
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                if(gp.obj[i] != null) {
                    gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                    gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
                }
                
                hasNormalKey = false;
            }
        }
        
        return index;
    }
    
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;
        
        for(int i = 0; i < target.length; i++) {
            if(target[i] != null) {
                // Entitás szilárd részének helyzete
                entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
                entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);
                
                // Tárgy szilárd részének helyzete
                target[i].solidArea.x = (int) (target[i].worldX + target[i].solidArea.x);
                target[i].solidArea.y = (int) (target[i].worldY + target[i].solidArea.y);
                
                switch(entity.direction) {
                case "up" -> entity.solidArea.y -= entity.speed;
                case "down" -> entity.solidArea.y += entity.speed;
                case "left" -> entity.solidArea.x -= entity.speed;
                case "right" -> entity.solidArea.x += entity.speed;
                }
                if(entity.solidArea.intersects(target[i].solidArea)) {
                    if(target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        
        return index;
    }
    
    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;
        
        // Entitás szilárd részének helyzete
        entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
        entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);
               
        // Tárgy szilárd részének helyzete
        gp.player.solidArea.x = (int) (gp.player.worldX + gp.player.solidArea.x);
        gp.player.solidArea.y = (int) (gp.player.worldY + gp.player.solidArea.y);
             
        switch(entity.direction) {
        case "up" -> entity.solidArea.y -= entity.speed;
        case "down" -> entity.solidArea.y += entity.speed;
        case "left" -> entity.solidArea.x -= entity.speed;
        case "right" -> entity.solidArea.x += entity.speed;
        }
        if(entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        
        return contactPlayer;
    }
    
}
