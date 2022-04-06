package object;

import entity.Entity;
import excalibur.GamePanel;
import java.io.IOException;
import javax.imageio.ImageIO;


public class OBJ_Chest extends Entity{
    
    GamePanel gp;
    
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        name = "Chest";
        type = type_chest_normal;
        down1 = setup("/objects/chest1_closed", gp.tileSize, gp.tileSize);
        collision = true;
    }
    
    @Override
    public void setAction() {
        
        if(unlocked) {
            down1 = setup("/objects/chest1_opened", gp.tileSize, gp.tileSize);
        }
                
    }
}
