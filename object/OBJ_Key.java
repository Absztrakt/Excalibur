package object;

import entity.Entity;
import excalibur.GamePanel;

public class OBJ_Key extends Entity{
    
    public OBJ_Key(GamePanel gp) {
        super(gp);
        
        name = "Key";
        name2 = "Kulcs";
        type = type_key_normal;
        description = "[" + name + "]" + "\nA small shiny golden key.\nProbably for doors or chests.";
        description2 = "[" + name2 + "]" + "\nEgy apró, csillogó aranykulcs.\nValószínűleg ajtókhoz és ládákhoz.";
        down1 = setup("/objects/key1", gp.tileSize, gp.tileSize);
    }
    
}
