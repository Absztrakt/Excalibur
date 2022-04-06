package excalibur;

import entity.Entity;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable {
    
    // DEBUG
    /*long maxDrawTime = 0, minDrawTime = 9999999;
    int tmp = 0;*/
    
    // Ablak beállítások
    final int originalTileSize = 16; // 16*16 tile-ok
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48*48 tile-ok
    public final int maxScreenCol = 32;
    public final int maxScreenRow = 18;
    public final int screenWidth = tileSize * maxScreenCol; // 1536px
    public final int screenHeight = tileSize * maxScreenRow; // 960px
    
    // Világ beállítások
    public final int maxWorldCol = 110, maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    // FPS
    int FPS = 60;
    
    // Rendszer
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound(this);
    Sound se = new Sound(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public SaveLoadGame slg = new SaveLoadGame(this);
    Thread gameThread;
    
    // Entitások és tárgyak
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[40];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList();
    public ArrayList<Entity> projectileList = new ArrayList();
    
    // Játék állapota
    public int gameState;
    public final int titleState = 0, playState = 1, pauseState = 2, dialogueState = 3, characterState = 4, controlScreen = 5, inGameOptionScreen = 6, inGameControlScreen = 7, inGameSave = 8, inGameSaveAndQuit = 9, inGameNotSavable = 10, inGameIsSaved = 11, RUSure = 12;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void setupGame() {
        gameState = titleState;
    }
    
    public void setupEntities() {
        slg.loadGame();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS; // 0.01666... másodperc
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while(gameThread != null) {
            //Informaciók frissítése
            update(); // update();
            
            // Képernyőre rajzolás a frissített információkkal
            repaint(); //paintComponent();
            
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                
                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                
                Thread.sleep((long)remainingTime);
                
                nextDrawTime += drawInterval;
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void update() {
        if(gameState == playState) {
            player.update();
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
            for(int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    if(monster[i].alive && !monster[i].dying) {
                        monster[i].update();
                    }
                    if(!monster[i].alive) {
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    if(projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        // DEBUG
        /*long drawStart = 0;
        if(keyH.checkDrawTime) {
        drawStart = System.nanoTime();
        }*/
        
        // TITLE SCREEN
        if(gameState == titleState) {
            ui.draw(g2);
        } else {
            // TILE
            tileM.draw(g2);

            // Entities & Objects
            entityList.add(player);
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }
            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }
            for(int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }
            
            // Sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result;
                    result = Integer.compare((int)e1.worldY, (int)e2.worldY);
                    return result;
                }
            });
            
            // Draw entities
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            
            // Empty entity list
            entityList.clear();
            
            // UI
            ui.draw(g2);
        }
        
        if(keyH.zPressed) {
            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            int x = 10, y = 400, lineHeight = 40;
            g2.drawString("worldX: " + player.worldX, x, y);
            y += lineHeight;
            g2.drawString("worldY: " + player.worldY, x, y);
            y += lineHeight;
            g2.drawString("screenX: " + player.screenX, x, y);
            y += lineHeight;
            g2.drawString("ScreenY: " + player.screenY, x, y);
            y += lineHeight;
            g2.drawString("Sor: " + (player.worldX + player.solidArea.x)/tileSize, x, y);
            y += lineHeight;
            g2.drawString("Oszlop: " + (player.worldY + player.solidArea.y)/tileSize, x, y);
        }
        
        // DEBUG
        /*if(keyH.checkDrawTime) {
        
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        if(passed > maxDrawTime) {
        maxDrawTime = passed;
        }
        if(passed < minDrawTime) {
        minDrawTime = passed;
        }
        if(tmp == 0) {
        String text = "Calculating draw time...";
        int x = tileSize*5, y = tileSize*6;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 38F));
        g2.setColor(Color.black);
        g2.drawString(text, ui.getXforCenteredText(text) + 5, y  + tileSize*2 + 30);
        g2.setColor(Color.white);
        g2.drawString(text, ui.getXforCenteredText(text), y + tileSize*2 + 25);
        }
        }
        
        if(keyH.asd) {
        asd();
        keyH.asd = false;
        }*/
        
        g2.dispose();
    }
    
    // DEBUG
    /*public void asd() {
    System.out.println(minDrawTime);
    System.out.println(maxDrawTime);
    }
    */
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    
    public void soundControl(float f) {
        music.soundControl(f);
    }
    
    public void stopMusic() {
        music.stop();
    }
    
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
    
}
