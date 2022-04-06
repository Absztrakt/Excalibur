package excalibur;

import entity.ANIMAL_Cat;
import entity.NPC_OldMan;
import monterC.MON_GreenSlime;
import monterC.MON_OrangeSpider;
import object.OBJ_Axe;
import object.OBJ_BronzeCoin;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Potion_Purple;
import object.OBJ_Potion_Red;
import object.OBJ_Potion_Water;
import object.OBJ_Sword_Long;


public class AssetSetter {

    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int i = 0;
        gp.obj[i] = new OBJ_BronzeCoin(gp);
        gp.obj[i].worldX = gp.tileSize*3;
        gp.obj[i].worldY = gp.tileSize*10;
        i++;
        
        gp.obj[i] = new OBJ_BronzeCoin(gp);
        gp.obj[i].worldX = gp.tileSize*4;
        gp.obj[i].worldY = gp.tileSize*16;
        i++;
        
        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = gp.tileSize*3;
        gp.obj[i].worldY = gp.tileSize*48;
        i++;
        
        gp.obj[i] = new OBJ_Potion_Purple(gp);
        gp.obj[i].worldX = gp.tileSize*4;
        gp.obj[i].worldY = gp.tileSize*48;
        i++;
        
        gp.obj[i] = new OBJ_Potion_Water(gp);
        gp.obj[i].worldX = gp.tileSize*5;
        gp.obj[i].worldY = gp.tileSize*48;
        i++;
        
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize*7;
        gp.obj[i].worldY = gp.tileSize*48;
        i++;
        
        gp.obj[i] = new OBJ_Chest(gp);
        gp.obj[i].worldX = gp.tileSize*30;
        gp.obj[i].worldY = gp.tileSize*39;
        i++;
        
        gp.obj[i] = new OBJ_Sword_Long(gp);
        gp.obj[i].worldX = gp.tileSize*29;
        gp.obj[i].worldY = gp.tileSize*39;
        i++;
        
        gp.obj[i] = new OBJ_Door(gp);
        gp.obj[i].worldX = gp.tileSize*24;
        gp.obj[i].worldY = gp.tileSize*46;
        i++;

    }
    
    public void setNPC() {
        
        if(gp.player.newGameStatus) {
            int i = 0;
            gp.npc[i] = new NPC_OldMan(gp);
            gp.npc[i].worldX = gp.tileSize*6;
            gp.npc[i].worldY = gp.tileSize*6;
            i++;
            
            gp.npc[i] = new ANIMAL_Cat(gp);
            gp.npc[i].worldX = gp.tileSize*5;
            gp.npc[i].worldY = gp.tileSize*5;
            i++;
        } else if(!gp.player.newGameStatus) {
            
            for(int i = 0; i < gp.slg.mapNPCs.size(); i++) {
                String[] splitNPCs = gp.slg.mapNPCs.get(i).split(" ");
                if(splitNPCs[0].equals("Oldman")) {
                    gp.npc[i] = new NPC_OldMan(gp);
                } else if(splitNPCs[0].equals("Cat")) {
                    gp.npc[i] = new ANIMAL_Cat(gp);
                }
                
                gp.npc[i].worldX = Integer.parseInt(splitNPCs[1]);
                gp.npc[i].worldY = Integer.parseInt(splitNPCs[2]);
            }

        }
    }

    public void setMonster() {
        if(gp.keyH.dificulity == 0) {
            int i = 0;
            gp.monster[i] = new MON_GreenSlime(gp);
            gp.monster[i].worldX = gp.tileSize*3;
            gp.monster[i].worldY = gp.tileSize*15;
            i++;

            gp.monster[i] = new MON_GreenSlime(gp);
            gp.monster[i].worldX = gp.tileSize*4;
            gp.monster[i].worldY = gp.tileSize*17;
            i++;
        }
        
        if(gp.keyH.dificulity == 1) {
            int i = 0;
            gp.monster[i] = new MON_GreenSlime(gp);
            gp.monster[i].worldX = gp.tileSize*3;
            gp.monster[i].worldY = gp.tileSize*15;
            i++;

            gp.monster[i] = new MON_GreenSlime(gp);
            gp.monster[i].worldX = gp.tileSize*4;
            gp.monster[i].worldY = gp.tileSize*17;
            i++;
            
            gp.monster[i] = new MON_GreenSlime(gp);
            gp.monster[i].worldX = gp.tileSize*5;
            gp.monster[i].worldY = gp.tileSize*20;
            i++;

            gp.monster[i] = new MON_GreenSlime(gp);
            gp.monster[i].worldX = gp.tileSize*3;
            gp.monster[i].worldY = gp.tileSize*23;
            i++;
        }
        
        if(gp.keyH.dificulity == 2) {
           int i = 0;
            gp.monster[i] = new MON_GreenSlime(gp);
            gp.monster[i].worldX = gp.tileSize*3;
            gp.monster[i].worldY = gp.tileSize*15;
            i++;

            gp.monster[i] = new MON_GreenSlime(gp);
            gp.monster[i].worldX = gp.tileSize*4;
            gp.monster[i].worldY = gp.tileSize*17;
            i++;
            
            gp.monster[i] = new MON_GreenSlime(gp);
            gp.monster[i].worldX = gp.tileSize*5;
            gp.monster[i].worldY = gp.tileSize*20;
            i++;

            gp.monster[i] = new MON_GreenSlime(gp);
            gp.monster[i].worldX = gp.tileSize*3;
            gp.monster[i].worldY = gp.tileSize*23;
            i++;
            
            gp.monster[i] = new MON_OrangeSpider(gp);
            gp.monster[i].worldX = gp.tileSize*6;
            gp.monster[i].worldY = gp.tileSize*12;
            i++;

            gp.monster[i] = new MON_OrangeSpider(gp);
            gp.monster[i].worldX = gp.tileSize*30;
            gp.monster[i].worldY = gp.tileSize*41;
            i++;
            
        }

    }
    
}
