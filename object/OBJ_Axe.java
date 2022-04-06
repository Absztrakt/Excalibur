package object;

import entity.Entity;
import excalibur.GamePanel;


public class OBJ_Axe extends Entity{
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        
        name = "Axe";
        name2 = "Fejsze";
        type = type_axe;
        attackValue = 3;
        attackArea.width = 35;
        attackArea.height = 35;
        description = "[" + name + "]" + "\nA large axe in good condition.\nIt does 3 attack damage.";
        description2 = "[" + name2 + "]" + "\nEgy jó állapotú, nagy fejsze.\n3 sebzést okoz.";
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
    }
}
