package object;

import entity.Entity;
import excalibur.GamePanel;

public class OBJ_Door extends Entity{
    
    GamePanel gp;
    
    public OBJ_Door(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        name = "Door";
        down1 = setup("/objects/door_normal_closed", gp.tileSize, gp.tileSize);
        collision = true;
        type = type_door_normal;
        
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    
    @Override
    public void setAction() {
      
    }
}
