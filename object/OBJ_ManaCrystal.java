package object;

import entity.Entity;
import excalibur.GamePanel;


public class OBJ_ManaCrystal extends Entity{
    
    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        
        name = "Mana Crystal";
        image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
    }
    
    @Override
    public void use(Entity entity) {
        
    }
    
}
