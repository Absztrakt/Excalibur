package excalibur;

import entity.Entity;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JSlider;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;


public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    JSlider slider;
    Font maruMonica;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
    public boolean messageOn = false, gameFinished = false, mmm = true;
    ArrayList<String> messages = new ArrayList();
    ArrayList<String> savedGamesAL = new ArrayList();
    ArrayList<Integer> messageCounter = new ArrayList();
    public String currentDialogue = "";
    public int commandNum = 0, titleScreenState = 0; // 0: az első képernyő, 1: a második képernyő
    public int slotCol = 0, slotRow = 0;
    public int volumeNum = 5;
    public ArrayList<String> savedItems;
    public ArrayList<String> savedGamesFull = new ArrayList();
    
    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream in = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch(FontFormatException | IOException e) {
            e.printStackTrace();
        }
        
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        
        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
    }
    
    public void addMessage(String text) {
        messages.add(text);
        messageCounter.add(0);
    }
    
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        
        // TITLE SCREEN
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if(gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMana();
            drawMessage();
        }
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        if(gp.gameState == gp.inGameOptionScreen) {
            drawOptionScreenInGame();
        }
        if(gp.gameState == gp.inGameControlScreen) {
            drawControlScreen();
        }
        if(gp.gameState == gp.inGameSave) {
            drawSavedInGame();
        }
        if(gp.gameState == gp.inGameSaveAndQuit) {
            drawSavedAndQuitInGame();
        }
        if(gp.gameState == gp.RUSure) {
            drawRUSure();
        }
        if(gp.gameState == gp.inGameIsSaved) {
            drawTheGameHasBeenSaved();
        }
        if(gp.gameState == gp.inGameNotSavable) {
            drawGameNotSavable();
        }
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        if(gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory();
        }
    }
    
    public void drawPauseScreen() {
        
        String text = "asd";
        if(gp.keyH.languageChange) {
            text = "PAUSED";
        } else if(!gp.keyH.languageChange) {
            text = "MEGÁLLÍTVA";
        }
        
        int x = gp.tileSize*5, y = gp.tileSize*3, width = gp.screenWidth - (gp.tileSize*10), height = gp.tileSize*13;
        drawSubWindow(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 120F));
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.setColor(Color.black);
        g2.drawString(text, x + 10, y  + gp.tileSize*2+10);
        g2.setColor(Color.white);
        g2.drawString(text, x, y + gp.tileSize*2);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        if(gp.keyH.languageChange) {
            text = "Continue";
        } else if(!gp.keyH.languageChange) {
            text = "Játék folytatása";
        }
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.setColor(Color.black);
        g2.drawString(text, getXforCenteredText(text)+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, getXforCenteredText(text), y);
        if(commandNum == 0) {
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
        
        if(gp.keyH.languageChange) {
            text = "Options";
        } else if(!gp.keyH.languageChange) {
            text = "Beállítások";
        }
        x = getXforCenteredText(text);
        y += gp.tileSize+20;
        g2.setColor(Color.black);
        g2.drawString(text, getXforCenteredText(text)+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, getXforCenteredText(text), y);
        if(commandNum == 1) {
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
        
        if(gp.keyH.languageChange) {
            text = "Controls";
        } else if (!gp.keyH.languageChange) {
            text = "Irányítás";
        }
        x = getXforCenteredText(text);
        y += gp.tileSize+20;
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
        
        if(gp.keyH.languageChange) {
            text = "Save";
        } else if (!gp.keyH.languageChange) {
            text = "Mentés";
        }
        x = getXforCenteredText(text);
        y += gp.tileSize+20;
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if(commandNum == 3) {
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
        
        if(gp.keyH.languageChange) {
            text = "Save and quit";
        } else if (!gp.keyH.languageChange) {
            text = "Mentés és kilépés";
        }
        x = getXforCenteredText(text);
        y += gp.tileSize+20;
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if(commandNum == 4) {
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
        
        if(gp.keyH.languageChange) {
            text = "Quit";
        } else if (!gp.keyH.languageChange) {
            text = "Kilépés";
        }
        x = getXforCenteredText(text);
        y += gp.tileSize+20;
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if(commandNum == 5) {
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
    }
    
    public int getItemIndex() {
        int itemIndex = slotCol + (slotRow*7);
        return itemIndex;
    }
    
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    private void drawDialogueScreen() {
        // kommunikációs ablak
        int x = gp.tileSize*5, y = gp.tileSize*2, width = gp.screenWidth - (gp.tileSize*10), height = gp.tileSize*4;
        drawSubWindow(x, y, width, height);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        x += gp.tileSize;
        y += gp.tileSize;
        
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
        
        String text = "";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            
        if(gp.keyH.languageChange) {
            text = "Press [ENTER] to continue...";
        } else if (!gp.keyH.languageChange) {
            text = "Nyomj [ENTER]-t a folytatáshoz...";
        }
        x = gp.tileSize*20;
        y = gp.tileSize*16;
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }
    
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    private void drawTitleScreen() {
        
        if(titleScreenState == 0) {
            drawMainMenuScreen();
        } else if(titleScreenState == 1) {
            drawNewGameScreen();
        } else if(titleScreenState == 2) {
            drawOptionScreen();
        } else if(titleScreenState == 3) {
            drawControlScreen();
        } else if(titleScreenState == 4) {
            drawCreditScreen();
        } else if(titleScreenState == 5) {
            drawSavedGamesScreen();
        }
        
    }

    private void drawPlayerLife() {
        int x = gp.tileSize/2, y = gp.tileSize/2, i = 0;
        while(i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        
        while(i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
        
    }
    
    private void drawMana() {
        
        int x = gp.tileSize*27;
        int y = gp.tileSize/2;
        int i = 0;
        while(i < gp.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        
        x = gp.tileSize*27;
        y = gp.tileSize/2;
        i = 0;
        while(i < gp.player.mana) {
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += gp.tileSize;
        }
        
    }

    private void drawCharacterScreen() {
        final int frameX = gp.tileSize, frameY = gp.tileSize*2, frameWidth = gp.tileSize*8, frameHeight = gp.tileSize*15;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int textX = frameX + gp.tileSize, textY = frameY + gp.tileSize + 20;
        final int lineHeight = 54;
        
        if(gp.keyH.languageChange) {
            g2.drawString("Life:", textX, textY);
            textY += lineHeight;
            g2.drawString("Mana:", textX, textY);
            textY += lineHeight;
            g2.drawString("Level:", textX, textY);
            textY += lineHeight;
            g2.drawString("Experience:", textX, textY);
            textY += lineHeight;
            g2.drawString("Next level:", textX, textY);
            textY += lineHeight;
            g2.drawString("Strength:", textX, textY);
            textY += lineHeight;
            g2.drawString("Dexterity:", textX, textY);
            textY += lineHeight;
            g2.drawString("Attack:", textX, textY);
            textY += lineHeight;
            g2.drawString("Defense:", textX, textY);
            textY += lineHeight;
            g2.drawString("Coins:", textX, textY);
            textY += lineHeight;
            g2.drawString("Weapon:", textX, textY);
            textY += lineHeight;
            g2.drawString("Shield:", textX, textY);
        } else if(!gp.keyH.languageChange) {
            g2.drawString("Élet:", textX, textY);
            textY += lineHeight;
            g2.drawString("Mana:", textX, textY);
            textY += lineHeight;
            g2.drawString("Szint:", textX, textY);
            textY += lineHeight;
            g2.drawString("Tapasztalat:", textX, textY);
            textY += lineHeight;
            g2.drawString("Következő szint:", textX, textY);
            textY += lineHeight;
            g2.drawString("Erő:", textX, textY);
            textY += lineHeight;
            g2.drawString("Kitérés:", textX, textY);
            textY += lineHeight;
            g2.drawString("Támadás:", textX, textY);
            textY += lineHeight;
            g2.drawString("Védekezés:", textX, textY);
            textY += lineHeight;
            g2.drawString("Arany:", textX, textY);
            textY += lineHeight;
            g2.drawString("Fegyver:", textX, textY);
            textY += lineHeight;
            g2.drawString("Pajzs:", textX, textY);
        }
        
        int tailX = (frameX + frameWidth) - gp.tileSize;
        textY = frameY + gp.tileSize + 20;
        String value;
        
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX - gp.tileSize, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX - gp.tileSize, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX - gp.tileSize, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX - gp.tileSize, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX - gp.tileSize, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX - gp.tileSize, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX - gp.tileSize, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX - gp.tileSize, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX - gp.tileSize, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX - gp.tileSize, textY);
        textY += lineHeight;
        
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize - 20, textY - 35, null);
        textY += lineHeight;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize - 20, textY - 35, null);
    }
    
    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

    private void drawMessage() {
        int messageX = gp.tileSize, messageY = gp.tileSize * 12;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        for(int i = 0; i < messages.size(); i++) {
            if(messages.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(messages.get(i), messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(messages.get(i), messageX, messageY);
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;
                
                if(messageCounter.get(i) > 180) {
                    messages.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    private void drawInventory() {
        int frameX = gp.tileSize*12, frameY = gp.tileSize*9 + 10, frameWidth = gp.tileSize*9, frameHeight = gp.tileSize*5 + 20;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        final int slotXStart = frameX + 20, slotYStart = frameY + 20;
        int slotX = slotXStart, slotY = slotYStart, slotSize = gp.tileSize + 9;
        
        for(int i = 0; i < gp.player.inventory.size(); i++) {
            if(gp.player.inventory.get(i).name.equals(gp.player.currentWeapon.name) || gp.player.inventory.get(i).name.equals(gp.player.currentShield.name)) {
                g2.setColor(Color.orange);
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if(i == 6 || i == 13 || i == 20) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }
        
        int cursorX = slotXStart + (slotSize * slotCol), cursorY = slotYStart + (slotSize * slotRow), cursorWidth = gp.tileSize, cursorHeight = gp.tileSize;
        
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        
        int DFrameX = gp.tileSize*21 + 10, DFrameY = gp.tileSize*9 + 10, DFrameWidth = gp.tileSize*9, DFrameHeight = gp.tileSize*5 + 20;
        int textX = DFrameX + 20, textY = DFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));
        int itemIndex = getItemIndex();
        if(gp.keyH.languageChange) {
            if(itemIndex < gp.player.inventory.size()) {
                drawSubWindow(DFrameX, DFrameY, DFrameWidth, DFrameHeight);
                for(String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        } else if(!gp.keyH.languageChange) {
            if(itemIndex < gp.player.inventory.size()) {
                drawSubWindow(DFrameX, DFrameY, DFrameWidth, DFrameHeight);
                for(String line : gp.player.inventory.get(itemIndex).description2.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
        
        int HFrameX = gp.tileSize*12, HFrameY = gp.tileSize*14 + 40, HFrameWidth = gp.tileSize*18 + 10, HFrameHeight = gp.tileSize*3 - 40;
        int HtextX = HFrameX + 20, HtextY = HFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));
        drawSubWindow(HFrameX, HFrameY, HFrameWidth, HFrameHeight);
        textY = gp.tileSize*15 + 35;
        if(gp.keyH.languageChange) {
            textX = gp.tileSize*13 - 20;
            g2.drawString("Use the [UP]/[DOWN]/[LEFT]/[RIGHT] arrows to move around in your inventory.", textX, textY);
        } else if(!gp.keyH.languageChange) {
            g2.setFont(g2.getFont().deriveFont(26F));
            textX = gp.tileSize*13 - 20;
            g2.drawString("Használd a [FEL]/[LE]/[BALRA]/[JOBBRA] nyilakat az eszköztárban való léptetéshez.", textX, textY);
        }
        
        g2.setFont(g2.getFont().deriveFont(28F));
        textY += 32;
        if(gp.keyH.languageChange) {
            textX += gp.tileSize*4 + 10;
            g2.drawString("Press the [ENTER] key to select an item.", textX, textY);
        } else if(!gp.keyH.languageChange) {
            g2.setFont(g2.getFont().deriveFont(26F));
            textX = gp.tileSize*15;
            g2.drawString("Nyomd meg az [ENTER] gombot egy eszköz kiválasztásához.", textX, textY);
        }
        
        
    }

    private void drawMainMenuScreen() {
            try {
                BufferedImage bg = ImageIO.read(getClass().getResourceAsStream("/pics/titleBG.jpg"));
                g2.drawImage(bg, 0, 0, gp.tileSize*32, gp.tileSize*18, null);
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(mmm) {
                gp.playMusic(11);
                mmm = false;
            }
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 112F));
            String text = "EXCALIBUR";
            int x = getXforCenteredText(text), y = gp.tileSize*3;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
            if(gp.keyH.languageChange) {
                text = "New game";
            } else if (!gp.keyH.languageChange) {
                text = "Új Játék";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize*3 + 20;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }

            if(gp.keyH.languageChange) {
                text = "Load game";
            } else if (!gp.keyH.languageChange) {
                text = "Játék Betöltése";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            if(gp.keyH.languageChange) {
                text = "Options";
            } else if (!gp.keyH.languageChange) {
                text = "Beállítások";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            if(gp.keyH.languageChange) {
                text = "Controls";
            } else if (!gp.keyH.languageChange) {
                text = "Irányítás";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            if(gp.keyH.languageChange) {
                text = "Credit";
            } else if (!gp.keyH.languageChange) {
                text = "Készítő";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 4) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }

            if(gp.keyH.languageChange) {
                text = "Quit";
            } else if (!gp.keyH.languageChange) {
                text = "Kilépés";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 5) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
    }

    private void drawCreditScreen() {
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/pics/setBG.jpeg"));
                g2.drawImage(image, 0, 0, 1536, 864, null);
            } catch(IOException e) {
                e.printStackTrace();
            }
            
            String text = "";
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
            
            if(gp.keyH.languageChange) {
                text = "Credit:";
            } else if (!gp.keyH.languageChange) {
                text = "Készítő:";
            }
            int x = getXforCenteredText(text), y = gp.tileSize*3;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            y += gp.tileSize;
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
            if(gp.keyH.languageChange) {
                text = "Hi! My name is Bence Garami and I'm a student at ELTE,";
            } else if (!gp.keyH.languageChange) {
                text = "Üdv! Garami Bence vagyok, az ELTE programtervező";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            if(gp.keyH.languageChange) {
                text = "studying software engineering. This is a project of mine that";
            } else if (!gp.keyH.languageChange) {
                text = "informatikus szakos hallgatója. Ez az egyik projektem,";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            if(gp.keyH.languageChange) {
                text = "I started working on on the 2nd of February, 2022. I made";
            } else if (!gp.keyH.languageChange) {
                text = "melyen 2022 február 2-án kezdtem el dolgozni. Mindezt";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            if(gp.keyH.languageChange) {
                text = "everything within 3 months. Having this game running on";
            } else if (!gp.keyH.languageChange) {
                text = "3 hónap leforgása alatt hoztam létre. Nagyon jó érzéssel";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            if(gp.keyH.languageChange) {
                text = "someone else's computer is a dream coming true.";
            } else if (!gp.keyH.languageChange) {
                text = "tölt el, hogy valaki más számítógépén fut az általam";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            if(gp.keyH.languageChange) {
                text = "I just want to thank you for playing my game";
            } else if (!gp.keyH.languageChange) {
                text = "készített játék. Remélem örömöt tudok okozni vele.";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            if(gp.keyH.languageChange) {
                text = "and wish you good luck on your adventure!";
            } else if (!gp.keyH.languageChange) {
                text = "Sok szerencsét a kalandhoz!";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            if(gp.keyH.languageChange) {
                text = "Take care,";
            } else if (!gp.keyH.languageChange) {
                text = "Köszönettel,";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            text = "Bence";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
            if(gp.keyH.languageChange) {
                text = "Back";
            } else if (!gp.keyH.languageChange) {
                text = "Vissza";
            }
            x =  getXforCenteredText(text);
            y = gp.tileSize*16;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
            
    }

    private void drawControlScreen() {
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/pics/ccBG.jpeg"));
                g2.drawImage(image, 0, 0, 1536, 864, null);
            } catch(IOException e) {
                e.printStackTrace();
            }
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
            String text = "";
            if(gp.keyH.languageChange) {
                text = "Controls:";
            } else if (!gp.keyH.languageChange) {
                text = "Irányítás:";
            }
            int x = getXforCenteredText(text), y = gp.tileSize*3;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            x = gp.tileSize*2;
            y = gp.tileSize*5;
            if(gp.keyH.languageChange) {
                g2.setFont(g2.getFont().deriveFont(40F));
                text = "Character controls:";
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                y += 30;
                text = "---------------------";
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                g2.setFont(g2.getFont().deriveFont(32F));
                x += gp.tileSize;
                y += gp.tileSize;
                text = "Go up -> [W]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Go down -> [S]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Go left -> [A]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Go right -> [D]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Run -> [SHIFT]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Attack -> [ENTER]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                g2.setFont(g2.getFont().deriveFont(40F));
                text = "Gameplay controls:";
                x = getXforCenteredText(text);
                y = gp.tileSize*5;
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                y += 30;
                text = "---------------------";
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                g2.setFont(g2.getFont().deriveFont(32F));
                y += gp.tileSize;
                text = "Status/Inventory -> [I]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Select/Interact -> [ENTER]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Hungarian language -> [L]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Close dialogue -> [ENTER]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Pause game -> [ESC]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                g2.setFont(g2.getFont().deriveFont(40F));
                text = "Menu controls:";
                x = gp.tileSize*24;
                y = gp.tileSize*5;
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                y += 30;
                text = "-----------------";
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                g2.setFont(g2.getFont().deriveFont(32F));
                y += gp.tileSize;
                text = "Go up -> [UP]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Go down -> [DOWN]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Hungarian language -> [L]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Select -> [ENTER]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
            } else if(!gp.keyH.languageChange) {
                
                g2.setFont(g2.getFont().deriveFont(40F));
                text = "Karakter irányítás:";
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                y += 30;
                text = "--------------------";
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                g2.setFont(g2.getFont().deriveFont(32F));
                x += 20;
                y += gp.tileSize;
                text = "Mozgás előre -> [W]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Mozgás hátra -> [S]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Mozgás jobbra -> [A]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Mozgás balra -> [D]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Futás -> [SHIFT]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Támadás -> [ENTER]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                g2.setFont(g2.getFont().deriveFont(40F));
                text = "Játékmenet irányítás:";
                x = getXforCenteredText(text);
                y = gp.tileSize*5;
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                y += 30;
                text = "-----------------------";
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                g2.setFont(g2.getFont().deriveFont(32F));
                x = getXforCenteredText(text) - 50;
                y += gp.tileSize;
                text = "Kiválasztás/Interakció -> [ENTER]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Tulajdonságok/Eszköztár -> [I]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Angol nyelv -> [L]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Párbeszéd bezárás -> [ENTER]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Játékmegállítás -> [ESC]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                g2.setFont(g2.getFont().deriveFont(40F));
                text = "Menü irányítás:";
                x = gp.tileSize*24;
                y = gp.tileSize*5;
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                y += 30;
                text = "-----------------";
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                g2.setFont(g2.getFont().deriveFont(32F));
                x += 20;
                y += gp.tileSize;
                text = "Fel -> [FEL]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Le -> [LE]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Angol nyelv -> [L]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                
                y += gp.tileSize;
                text = "Kiválasztás -> [ENTER]";
                g2.setColor(Color.black);
                g2.drawString(text, x+4, y+4);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            }
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
            if(gp.keyH.languageChange) {
                text = "Back";
            } else if (!gp.keyH.languageChange) {
                text = "Vissza";
            }
            x = getXforCenteredText(text);
            y = gp.tileSize*16;
            
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
    }

    private void drawNewGameScreen() {
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/pics/sunsBG.jpeg"));
                g2.drawImage(image, 0, 0, 1536, 864, null);
            } catch(IOException e) {
                e.printStackTrace();
            }
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
            String text = "";
            if(gp.keyH.languageChange) {
                text = "Character customization:";
            } else if (!gp.keyH.languageChange) {
                text = "Karakter kiválasztása:";
            }
            int x = getXforCenteredText(text), y = gp.tileSize*3;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            if(gp.keyH.languageChange) {
                text = "Blue";
            } else if (!gp.keyH.languageChange) {
                text = "Kék";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            if(gp.keyH.languageChange) {
                text = "Red";
            } else if (!gp.keyH.languageChange) {
                text = "Piros";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            if(gp.keyH.languageChange) {
                text = "Yellow";
            } else if (!gp.keyH.languageChange) {
                text = "Sárga";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            if(gp.keyH.languageChange) {
                text = "Back";
            } else if (!gp.keyH.languageChange) {
                text = "Vissza";
            }
            x = getXforCenteredText(text);
            y = gp.tileSize*16;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
    }

    private void drawOptionScreen() {
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/pics/setBG.jpeg"));
                g2.drawImage(image, 0, 0, 1536, 864, null);
                BufferedImage imageHang = null;
                
                if(gp.keyH.audioOnOff) {
                    switch(volumeNum) {
                        case 0 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_0.png"));
                        case 1 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_10.png"));
                        case 2 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_20.png"));
                        case 3 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_30.png"));
                        case 4 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_40.png"));
                        case 5 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_50.png"));
                        case 6 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_60.png"));
                        case 7 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_70.png"));
                        case 8 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_80.png"));
                        case 9 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_90.png"));
                        case 10 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_100.png"));
                    }
                } else if(!gp.keyH.audioOnOff) {
                    switch(volumeNum) {
                        case 0 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_0_off.png"));
                        case 1 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_10_off.png"));
                        case 2 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_20_off.png"));
                        case 3 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_30_off.png"));
                        case 4 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_40_off.png"));
                        case 5 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_50_off.png"));
                        case 6 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_60_off.png"));
                        case 7 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_70_off.png"));
                        case 8 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_80_off.png"));
                        case 9 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_90_off.png"));
                        case 10 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_100_off.png"));
                    }
                }
                g2.drawImage(imageHang, gp.tileSize*16, gp.tileSize*8 + 30, 600, 100, null);
            } catch(IOException e) {
                e.printStackTrace();
            }
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
            String text = "";
            if(gp.keyH.languageChange) {
                text = "Options:";
            } else if (!gp.keyH.languageChange) {
                text = "Beállítások:";
            }
            int x = getXforCenteredText(text), y = gp.tileSize*3;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
            if(gp.keyH.languageChange) {
                text = "Music";
            } else if (!gp.keyH.languageChange) {
                text = "Zene";
            }
            x = gp.tileSize*5;
            y = gp.tileSize*6;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            y = gp.tileSize*6;
            if(gp.keyH.audioOnOff) {
                if(gp.keyH.languageChange) {
                    x = gp.tileSize*22;
                    text = "ON";
                } else if (!gp.keyH.languageChange) {
                    x = gp.tileSize*20;
                    text = "BEKAPCSOLVA";
                }
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            } else if(!gp.keyH.audioOnOff) {
                if(gp.keyH.languageChange) {
                    x = gp.tileSize*22;
                    text = "OFF";
                } else if (!gp.keyH.languageChange) {
                    x = gp.tileSize*20;
                    text = "KIKAPCSOLVA";
                }
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            }
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
            if(gp.keyH.languageChange) {
                text = "Sounds";
            } else if (!gp.keyH.languageChange) {
                text = "Hangok";
            }
            x = gp.tileSize*5;
            y = gp.tileSize*8;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            y = gp.tileSize*8;
            if(gp.keyH.SEOnOff) {
                if(gp.keyH.languageChange) {
                    x = gp.tileSize*22;
                    text = "ON";
                } else if (!gp.keyH.languageChange) {
                    x = gp.tileSize*20;
                    text = "BEKAPCSOLVA";
                }
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            } else if(!gp.keyH.SEOnOff) {
                if(gp.keyH.languageChange) {
                    x = gp.tileSize*22;
                    text = "OFF";
                } else if (!gp.keyH.languageChange) {
                    x = gp.tileSize*20;
                    text = "KIKAPCSOLVA";
                }
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            }
            
            if(gp.keyH.languageChange) {
                text = "Volume";
            } else if (!gp.keyH.languageChange) {
                text = "Hangerő";
            }
            x = gp.tileSize*5;
            y += gp.tileSize*2;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            if(gp.keyH.languageChange) {
                text = "Dificulity";
            } else if (!gp.keyH.languageChange) {
                text = "Játék nehézsége";
            }
            x = gp.tileSize*5;
            y += gp.tileSize*2;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            y = gp.tileSize*12;
            if(gp.keyH.dificulity == 0) {
                if(gp.keyH.languageChange) {
                    x = gp.tileSize*22;
                    text = "Easy";
                } else if (!gp.keyH.languageChange) {
                    x = gp.tileSize*20;
                    text = "Könnyű";
                }
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            } else if(gp.keyH.dificulity == 1) {
                if(gp.keyH.languageChange) {
                    x = gp.tileSize*22;
                    text = "Normal";
                } else if (!gp.keyH.languageChange) {
                    x = gp.tileSize*20;
                    text = "Közepes";
                }
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            } else if(gp.keyH.dificulity == 2) {
                if(gp.keyH.languageChange) {
                    x = gp.tileSize*22;
                    text = "Hard";
                } else if (!gp.keyH.languageChange) {
                    x = gp.tileSize*20;
                    text = "Nehéz";
                }
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
            if(gp.keyH.languageChange) {
                text = "Back";
            } else if (!gp.keyH.languageChange) {
                text = "Vissza";
            }
            x = getXforCenteredText(text);
            y = gp.tileSize*16;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 4) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
    }

    private void drawOptionScreenInGame() {
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/pics/setBG.jpeg"));
                g2.drawImage(image, 0, 0, 1536, 864, null);
                BufferedImage imageHang = null;
                if(gp.keyH.audioOnOff) {
                    switch(volumeNum) {
                        case 0 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_0.png"));
                        case 1 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_10.png"));
                        case 2 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_20.png"));
                        case 3 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_30.png"));
                        case 4 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_40.png"));
                        case 5 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_50.png"));
                        case 6 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_60.png"));
                        case 7 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_70.png"));
                        case 8 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_80.png"));
                        case 9 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_90.png"));
                        case 10 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_100.png"));
                    }
                } else if(!gp.keyH.audioOnOff) {
                    switch(volumeNum) {
                        case 0 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_0_off.png"));
                        case 1 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_10_off.png"));
                        case 2 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_20_off.png"));
                        case 3 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_30_off.png"));
                        case 4 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_40_off.png"));
                        case 5 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_50_off.png"));
                        case 6 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_60_off.png"));
                        case 7 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_70_off.png"));
                        case 8 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_80_off.png"));
                        case 9 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_90_off.png"));
                        case 10 -> imageHang = ImageIO.read(getClass().getResourceAsStream("/pics/hangero_100_off.png"));
                    }
                }
                g2.drawImage(imageHang, gp.tileSize*16, gp.tileSize*8 + 30, 600, 100, null);
            } catch(IOException e) {
                e.printStackTrace();
            }
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
            String text = "asd";
            if(gp.keyH.languageChange) {
                text = "Options:";
            } else if (!gp.keyH.languageChange) {
                text = "Beállítások:";
            }
            int x = getXforCenteredText(text), y = gp.tileSize*3;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
            if(gp.keyH.languageChange) {
                text = "Music";
            } else if (!gp.keyH.languageChange) {
                text = "Zene";
            }
            x = gp.tileSize*5;
            y = gp.tileSize*6;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            y = gp.tileSize*6;
            if(gp.keyH.audioOnOff) {
                if(gp.keyH.languageChange) {
                    x = gp.tileSize*22;
                    text = "ON";
                } else if (!gp.keyH.languageChange) {
                    x = gp.tileSize*20;
                    text = "BEKAPCSOLVA";
                }
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            } else if(!gp.keyH.audioOnOff) {
                if(gp.keyH.languageChange) {
                    x = gp.tileSize*22;
                    text = "OFF";
                } else if (!gp.keyH.languageChange) {
                    x = gp.tileSize*20;
                    text = "KIKAPCSOLVA";
                }
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            }
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
            if(gp.keyH.languageChange) {
                text = "Sounds";
            } else if (!gp.keyH.languageChange) {
                text = "Hangok";
            }
            x = gp.tileSize*5;
            y = gp.tileSize*8;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
            
            y = gp.tileSize*8;
            if(gp.keyH.SEOnOff) {
                if(gp.keyH.languageChange) {
                    x = gp.tileSize*22;
                    text = "ON";
                } else if (!gp.keyH.languageChange) {
                    x = gp.tileSize*20;
                    text = "BEKAPCSOLVA";
                }
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            } else if(!gp.keyH.SEOnOff) {
                if(gp.keyH.languageChange) {
                    x = gp.tileSize*22;
                    text = "OFF";
                } else if (!gp.keyH.languageChange) {
                    x = gp.tileSize*20;
                    text = "KIKAPCSOLVA";
                }
                g2.setColor(Color.black);
                g2.drawString(text, x+5, y+5);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            }
            
            if(gp.keyH.languageChange) {
                text = "Volume";
            } else if (!gp.keyH.languageChange) {
                text = "Hangerő";
            }
            x = gp.tileSize*5;
            y += gp.tileSize*2;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
            if(gp.keyH.languageChange) {
                text = "Back";
            } else if (!gp.keyH.languageChange) {
                text = "Vissza";
            }
            x = getXforCenteredText(text);
            y = gp.tileSize*16;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
    }

    private void drawSavedGamesScreen() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/pics/ccBG.jpeg"));
            g2.drawImage(image, 0, 0, 1536, 864, null);
        } catch(IOException e) {
            e.printStackTrace();
        }

        String text = "";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));

        if(gp.keyH.languageChange) {
            text = "Saved Games:";
        } else if (!gp.keyH.languageChange) {
            text = "Mentett Játékok:";
        }
        int x = getXforCenteredText(text), y = gp.tileSize*3;
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        y += gp.tileSize*2;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
        for(int i = 0; i < savedGamesAL.size(); i++) {
            String[] splitFilePaths = savedGamesAL.get(i).split("-");
            text = splitFilePaths[0].concat(".").concat(splitFilePaths[1])
                    .concat(".").concat(splitFilePaths[2])
                    .concat("   ").concat(splitFilePaths[3])
                    .concat(":").concat(splitFilePaths[4])
                    .concat(":").concat(splitFilePaths[5]);
            x = getXforCenteredText(text);
            y += gp.tileSize + 20;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == i) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
        }
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        if(gp.keyH.languageChange) {
            text = "Back";
        } else if (!gp.keyH.languageChange) {
            text = "Vissza";
        }
        x = getXforCenteredText(text);
        y = gp.tileSize*16;
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if(commandNum == savedGamesAL.size()) {
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
        
    }

    private void drawSavedInGame() {
        if(gp.ui.savedGamesAL.size() < 5) {
            drawTheGameHasBeenSaved();
        } else if(gp.ui.savedGamesAL.size() > 4) {
            String text = "asd";
            if(gp.keyH.languageChange) {
                text = "The game cannot be saved!";
            } else if(!gp.keyH.languageChange) {
                text = "A játékot nem sikerült menteni!";
            }

            int x = gp.tileSize*6, y = gp.tileSize*5, width = gp.screenWidth - (gp.tileSize*12), height = gp.tileSize*7;
            drawSubWindow(x, y, width, height);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.black);
            g2.drawString(text, x + 10, y  + gp.tileSize*2+10);
            g2.setColor(Color.white);
            g2.drawString(text, x, y + gp.tileSize*2);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
            if(gp.keyH.languageChange) {
                text = "OK";
            } else if(!gp.keyH.languageChange) {
                text = "Rendben";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize*4 + 20;
            g2.setColor(Color.black);
            g2.drawString(text, getXforCenteredText(text)+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, getXforCenteredText(text), y);
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
        
    }

    private void drawSavedAndQuitInGame() {
        if(savedGamesAL.size() < 5) {
            drawTheGameHasBeenSaved();
        } else if(savedGamesAL.size() > 4) {
            String text = "asd";
            if(gp.keyH.languageChange) {
                text = "The game cannot be saved!";
            } else if(!gp.keyH.languageChange) {
                text = "A játékot nem sikerült menteni!";
            }

            int x = gp.tileSize*6, y = gp.tileSize*5, width = gp.screenWidth - (gp.tileSize*12), height = gp.tileSize*7;
            drawSubWindow(x, y, width, height);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.black);
            g2.drawString(text, x + 10, y  + gp.tileSize*2+10);
            g2.setColor(Color.white);
            g2.drawString(text, x, y + gp.tileSize*2);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
            if(gp.keyH.languageChange) {
                text = "OK";
            } else if(!gp.keyH.languageChange) {
                text = "Rendben";
            }
            x = getXforCenteredText(text);
            y += gp.tileSize*4 + 20;
            g2.setColor(Color.black);
            g2.drawString(text, getXforCenteredText(text)+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, getXforCenteredText(text), y);
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
    }

    private void drawGameNotSavable() {
        String text = "asd";
        if(gp.keyH.languageChange) {
            text = "Which save should we overwrite?";
        } else if(!gp.keyH.languageChange) {
            text = "Melyik mentést írjuk felül a jelenlegivel?";
        }

        int x = gp.tileSize*3, y = gp.tileSize*2, width = gp.screenWidth - (gp.tileSize*6), height = gp.tileSize*14;
        drawSubWindow(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        x = getXforCenteredText(text);
        y += gp.tileSize - 30;
        g2.setColor(Color.black);
        g2.drawString(text, x + 10, y  + gp.tileSize*2+10);
        g2.setColor(Color.white);
        g2.drawString(text, x, y + gp.tileSize*2);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
        y += gp.tileSize*3;
        for(int i = 0; i < savedGamesAL.size(); i++) {
            String[] splitFilePaths = savedGamesAL.get(i).split("-");
            text = splitFilePaths[0].concat(".").concat(splitFilePaths[1])
                    .concat(".").concat(splitFilePaths[2])
                    .concat("   ").concat(splitFilePaths[3])
                    .concat(":").concat(splitFilePaths[4])
                    .concat(":").concat(splitFilePaths[5]);
            x = getXforCenteredText(text);
            y += gp.tileSize + 20;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(commandNum == i) {
                g2.setColor(Color.black);
                g2.drawString(">", x-gp.tileSize*2+5, y+2);
                g2.setColor(Color.white);
                g2.drawString(">", x-gp.tileSize*2, y);
            }
        }
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        if(gp.keyH.languageChange) {
            text = "Back";
        } else if (!gp.keyH.languageChange) {
            text = "Vissza";
        }
        x = getXforCenteredText(text);
        y = gp.tileSize*15 - 20;
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if(commandNum == savedGamesAL.size()) {
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
        
    }

    private void drawTheGameHasBeenSaved() {
        String text = "asd";
        if(gp.keyH.languageChange) {
            text = "The game has been saved!";
        } else if(!gp.keyH.languageChange) {
            text = "A játék mentésre került!";
        }

        int x = gp.tileSize*6, y = gp.tileSize*5, width = gp.screenWidth - (gp.tileSize*12), height = gp.tileSize*7;
        drawSubWindow(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.setColor(Color.black);
        g2.drawString(text, x + 10, y  + gp.tileSize*2+10);
        g2.setColor(Color.white);
        g2.drawString(text, x, y + gp.tileSize*2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        if(gp.keyH.languageChange) {
            text = "OK";
        } else if(!gp.keyH.languageChange) {
            text = "Rendben";
        }
        x = getXforCenteredText(text);
        y += gp.tileSize*4 + 20;
        g2.setColor(Color.black);
        g2.drawString(text, getXforCenteredText(text)+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, getXforCenteredText(text), y);
        g2.setColor(Color.black);
        g2.drawString(">", x-gp.tileSize*2+5, y+2);
        g2.setColor(Color.white);
        g2.drawString(">", x-gp.tileSize*2, y);
    }

    private void drawRUSure() {
        String text = "asd";
        if(gp.keyH.languageChange) {
            text = "Are you sure you want to quit?";
        } else if(!gp.keyH.languageChange) {
            text = "Biztosan ki szeretne lépni?";
        }

        int x = gp.tileSize*6, y = gp.tileSize*5, width = gp.screenWidth - (gp.tileSize*12), height = gp.tileSize*8;
        drawSubWindow(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.setColor(Color.black);
        g2.drawString(text, x + 10, y  + gp.tileSize*2+10);
        g2.setColor(Color.white);
        g2.drawString(text, x, y + gp.tileSize*2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        if(gp.keyH.languageChange) {
            text = "YES";
        } else if(!gp.keyH.languageChange) {
            text = "IGEN";
        }
        x = getXforCenteredText(text);
        y += gp.tileSize*4 + 20;
        g2.setColor(Color.black);
        g2.drawString(text, getXforCenteredText(text)+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, getXforCenteredText(text), y);
        if(commandNum == 0) {
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        if(gp.keyH.languageChange) {
            text = "NO";
        } else if(!gp.keyH.languageChange) {
            text = "NEM";
        }
        x = getXforCenteredText(text);
        y += gp.tileSize + 20;
        g2.setColor(Color.black);
        g2.drawString(text, getXforCenteredText(text)+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(text, getXforCenteredText(text), y);
        if(commandNum == 1) {
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize*2+5, y+2);
            g2.setColor(Color.white);
            g2.drawString(">", x-gp.tileSize*2, y);
        }
    }
    
}
