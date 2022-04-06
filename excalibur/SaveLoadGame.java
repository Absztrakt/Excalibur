package excalibur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class SaveLoadGame {
    
    GamePanel gp;
    public long count = 0;
    public int pWorldX, pWorldY, pLevel, pLife, pMaxLife, pExp, pNextLevelExp, pStrength, pDexterity, pCoin, pCT;
    public String pDirection, pCW, pCS, tmpS;
    public ArrayList<String> invItems = new ArrayList();
    public ArrayList<String> mapObjects = new ArrayList();
    public ArrayList<String> mapNPCs = new ArrayList();
    public ArrayList<String> mapMonsters = new ArrayList();
            
    public SaveLoadGame(GamePanel gp) {
        this.gp = gp;
        
    }

    public void saveGame() {
        try (Stream<Path> files = Files.list(Paths.get("./savedGames/"))) {
            count = files.count();
        } catch (IOException ex) {
            Logger.getLogger(SaveLoadGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(count != 5) {
            String currentDateAndTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
            try {
                FileWriter fw = new FileWriter("./savedGames/" + currentDateAndTime + ".txt");
                // Player stats
                fw.write(gp.player.worldX + " ");
                fw.write(gp.player.worldY + " ");
                fw.write(gp.player.direction + " ");
                fw.write(gp.player.level + " ");
                fw.write(gp.player.life + " ");
                fw.write(gp.player.maxLife + " ");
                fw.write(gp.player.exp + " ");
                fw.write(gp.player.nextLevelExp + " ");
                fw.write(gp.player.strength + " ");
                fw.write(gp.player.dexterity + " ");
                fw.write(gp.player.coin + " ");
                fw.write(gp.player.characterType + "\r\n");
                fw.write(gp.player.currentWeapon.name + "\r\n");
                fw.write(gp.player.currentShield.name + "\r\n");

                // Inventory
                for(int i = 0; i < gp.player.inventory.size(); i++) {
                    if(!gp.player.currentWeapon.equals(gp.player.inventory.get(i)) || !gp.player.currentShield.equals(gp.player.inventory.get(i))) {
                        fw.write(gp.player.inventory.get(i).name + "\r\n");
                    }
                }
                
                // Splitting inventory items and map objects
                fw.write("split\r\n");

                // Remaining objects on map
                for(int i = 0; i < gp.obj.length; i++) {
                    if(gp.obj[i] != null) {
                        if(gp.obj[i].type == 13 || gp.obj[i].type == 14) {
                            fw.write(gp.obj[i].name + "_" + gp.obj[i].collision + " " + gp.obj[i].worldX + " " + gp.obj[i].worldY + "\r\n");
                        } else {
                            fw.write(gp.obj[i].name + " " + gp.obj[i].worldX + " " + gp.obj[i].worldY + "\r\n");
                        }
                    }
                }
                
                // Splitting remaining objects on map and entities
                fw.write("split\r\n");
                
                // NPCs
                for(int i = 0; i < gp.npc.length; i++) {
                    if(gp.npc[i] != null) {
                        fw.write(gp.npc[i].name + " " + gp.npc[i].worldX + " " + gp.npc[i].worldY + "\r\n");
                    }
                }
                
                // Splitting NPCs and Monsters
                fw.write("split\r\n");
                
                // Monsters
                for(int i = 0; i < gp.monster.length; i++) {
                    if(gp.monster[i] != null) {
                        fw.write(gp.monster[i].name + " " + gp.monster[i].worldX + " " + gp.monster[i].worldY + "\r\n");
                    }
                }
                
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(SaveLoadGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void loadGame() {
        File directory = new File("./savedGames/");
        FilenameFilter textFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        };
        String tmpFilePath = "";
        File[] files = directory.listFiles(textFilter);
        for (File file : files) {
            tmpFilePath = file.toString().substring(13, 32);
            if(!gp.ui.savedGamesAL.contains(tmpFilePath)) {
                gp.ui.savedGamesAL.add(tmpFilePath);
            }
            if(!gp.ui.savedGamesFull.contains(file.toString())) {
                gp.ui.savedGamesFull.add(file.toString());
            }
        }
        //for(int i = 0; i < gp.ui.savedGamesFull.size(); i++) {
        //    System.out.println(gp.ui.savedGamesFull.get(i));
        //}
    }

    public void loadGameStats(int i) {
        
        try {
            FileInputStream fis = new FileInputStream(gp.ui.savedGamesFull.get(i));
            //System.out.println(gp.ui.savedGamesFull.get(i));
            if(fis != null) {
                //System.out.println("Bejutott az elágazásba.");
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                //System.out.println("Beolvasta a BufferReader.");
                String firstLine = br.readLine();
                //System.out.println("Beolvasta az első sort.");
                String[] fl = firstLine.split(" ");
                pWorldX = Integer.parseInt(fl[0]);
                pWorldY = Integer.parseInt(fl[1]);
                pDirection = fl[2];
                pLevel= Integer.parseInt(fl[3]);
                pLife = Integer.parseInt(fl[4]);
                pMaxLife = Integer.parseInt(fl[5]);
                pExp = Integer.parseInt(fl[6]);
                pNextLevelExp = Integer.parseInt(fl[7]);
                pStrength = Integer.parseInt(fl[8]);
                pDexterity = Integer.parseInt(fl[9]);
                pCoin = Integer.parseInt(fl[10]);
                pCT = Integer.parseInt(fl[11]);
                pCW = br.readLine();
                pCS = br.readLine();
                tmpS = br.readLine();
                while(!tmpS.equals("split")) {
                    invItems.add(tmpS);
                    tmpS = br.readLine();
                }
                tmpS = br.readLine();
                while(!tmpS.equals("split")) {
                    mapObjects.add(tmpS);
                    tmpS = br.readLine();
                }
                tmpS = br.readLine();
                while(!tmpS.equals("split")) {
                    mapNPCs.add(tmpS);
                    tmpS = br.readLine();
                }
                tmpS = br.readLine();
                while(tmpS != null) {
                    mapMonsters.add(tmpS);
                    tmpS = br.readLine();
                }
                br.close();
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        //System.out.println(pWorldX);
        //System.out.println(pWorldY);
        gp.player.setSavedValues(pWorldX, pWorldY, pDirection, pLevel, pLife, pMaxLife, pStrength, pDexterity, pExp, pNextLevelExp, pCoin, pCT, pCW, pCS);
        gp.player.setSavedItems(invItems);
        gp.player.getPlayerImage();
        gp.player.getPlayerAttackImages();
        gp.player.update();

    }
    
    public void deleteSave(String filePath, int i) {
        File del = new File(filePath);
        del.delete();
        gp.ui.savedGamesAL.remove(i);
        gp.ui.savedGamesFull.remove(i);
    }
    
    
}
