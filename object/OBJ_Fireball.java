package object;

import entity.Entity;
import entity.Projectile;
import excalibur.GamePanel;


public class OBJ_Fireball extends Projectile {

    GamePanel gp;

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        name = "Fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    private void getImage() {
        up1 = setup("/projectileP/fireball_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/projectileP/fireball_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/projectileP/fireball_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/projectileP/fireball_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/projectileP/fireball_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/projectileP/fireball_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/projectileP/fireball_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/projectileP/fireball_right_2", gp.tileSize, gp.tileSize);
    }
    
    @Override
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if(user.mana >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
    
    @Override
    public void substractResource(Entity user) {
        user.mana -= useCost;
    }
    
}
