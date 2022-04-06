package object;

import entity.Entity;
import excalibur.GamePanel;


public class OBJ_BronzeCoin extends Entity{
    
    GamePanel gp;
    
    public OBJ_BronzeCoin(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_pickUpOnly;
        name = "Bronze Coin";
        value = 5;
        down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
        
    }
    
    @Override
    public void use(Entity entity) {
        gp.playSE(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coin += value;
    }
}
