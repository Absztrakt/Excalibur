package object;

import entity.Projectile;
import excalibur.GamePanel;


public class OBJ_Arrow extends Projectile {

    GamePanel gp;
    
    public OBJ_Arrow(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        name = "Arrow";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
    }
    
}
