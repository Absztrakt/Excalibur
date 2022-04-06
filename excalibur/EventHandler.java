package excalibur;


public class EventHandler {
    
    GamePanel gp;
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    
    public EventHandler(GamePanel gp) {
        this.gp = gp;
        
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        
        int col = 0, row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y; 
            
            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
        
    }
    
    public void checkEvent() {
        int xDistance = (int) Math.abs(gp.player.worldX - previousEventX);
        int yDistance = (int) Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }
        
        if(canTouchEvent) {
            if(hit(16, 12, "left")) {
                damagePit(16, 12, gp.dialogueState);
            }
            if(hit(17, 12, "any")) {
                healingPool(17, 12, gp.dialogueState);
            }
            if(hit(20, 12, "up")) {
                teleport(gp.dialogueState);
            }
        }
    }
    
    public void teleport(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport!";
        gp.player.worldX = gp.tileSize*42;
        gp.player.worldY = gp.tileSize*50;
    }
    
    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;
        gp.player.solidArea.x = (int) (gp.player.worldX + gp.player.solidArea.x);
        gp.player.solidArea.y = (int) (gp.player.worldY + gp.player.solidArea.y);
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;
        
        if(gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
                
                previousEventX = (int) gp.player.worldX;
                previousEventY = (int) gp.player.worldY;
            }
        }
        
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        
        return hit;
    }

    private void damagePit(int col, int row, int gameState) {
        gp.gameState = gameState;
        if(gp.keyH.SEOnOff) {
            gp.playSE(8);
        }
        gp.ui.currentDialogue = "You stepped in a cactus!";
        gp.player.life -= 1;
        //eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }
    
    public void healingPool(int col, int row, int gameState) {
        if(gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            if(gp.keyH.SEOnOff) {
                gp.playSE(3);
            }
            gp.aSetter.setMonster();
            if(gp.player.life == gp.player.maxLife) {
                gp.ui.currentDialogue = "You are not thirsty.";
            } else {
                gp.ui.currentDialogue = "You start to feel better!";
                gp.player.life++;
                gp.player.mana++;
            }
        }
    }
    
}
