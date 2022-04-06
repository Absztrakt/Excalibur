package excalibur;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean shiftPressed;
    public boolean enterPressed;
    public boolean shotKeyPressed;
    public boolean cKnight;
    public boolean cAssasin;
    public boolean cBarbarian;
    public boolean languageChange;
    public boolean audioOnOff = true;
    public boolean SEOnOff = true;
    public boolean inGameSaveError = false;
    public boolean inGameSaveAndQuitError = false;
    public int dificulity = 0;
    
    // DEBUG
    public boolean zPressed;
    
    // DEBUG
    //public boolean asd;
    
    // DEBUG
    //boolean checkDrawTime = false;
    
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Nem használjuk...
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getExtendedKeyCode();
        
        if(gp.gameState == gp.titleState) {
            titleState(code);
        } else if(gp.gameState == gp.playState) {
            playState(code);
        } else if(gp.gameState == gp.pauseState) {
            pauseState(code); 
        } else if(gp.gameState == gp.inGameOptionScreen) {
            inGameOptionScreen(code);
        } else if(gp.gameState == gp.inGameControlScreen) {
            inGameControlScreen(code); 
        } else if(gp.gameState == gp.inGameSave) {
            inGameSave(code);
        } else if(gp.gameState == gp.inGameNotSavable) {
            inGameNotSavable(code);
        } else if(gp.gameState == gp.inGameSaveAndQuit) {
            inGameSaveAndQuit(code);
        } else if(gp.gameState == gp.RUSure) {
            RUSure(code);
        } else if(gp.gameState == gp.inGameIsSaved) {
            inGameisSaved(code);
        } else if(gp.gameState == gp.dialogueState) {
            dialogueState(code);
        } else if(gp.gameState == gp.characterState) {
            characterState(code);
        }
       
    }
    
    public void titleState(int code) {
        if(gp.ui.titleScreenState == 0) {
            if(code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 5;                    
                }
            }
            if(code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 5) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.player.newGameStatus = true;
                    gp.ui.titleScreenState = 1;
                }
                if(gp.ui.commandNum == 1) {
                    gp.ui.commandNum = 0;
                    gp.slg.loadGame();
                    gp.ui.titleScreenState = 5;
                }
                if(gp.ui.commandNum == 2) {
                    gp.ui.commandNum = 0;
                    gp.ui.titleScreenState = 2;
                }
                if(gp.ui.commandNum == 3) {
                    gp.ui.commandNum = 0;
                    gp.ui.titleScreenState = 3;
                }
                if(gp.ui.commandNum == 4) {
                    gp.ui.commandNum = 0;
                    gp.ui.titleScreenState = 4;
                }
                if(gp.ui.commandNum == 5) {
                    System.exit(0);
                }
            }
            if(code == KeyEvent.VK_L) {
                if(languageChange) {
                    languageChange = false;
                } else if(!languageChange) {
                    languageChange = true;
                }
            }
        } else if(gp.ui.titleScreenState == 1) {
            if(code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }
            if(code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                //Ez itt problémás
                gp.setupEntities();
                if(gp.ui.commandNum == 0) {
                    gp.player.newGameStatus = true;
                    if(audioOnOff) {
                        gp.stopMusic();
                        gp.player.characterType = 1;
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    } else if(!audioOnOff) {
                        gp.player.characterType = 1;
                        gp.gameState = gp.playState;
                    }
                }
                if(gp.ui.commandNum == 1) {
                    gp.player.newGameStatus = true;
                    if(audioOnOff) {
                        gp.stopMusic();
                        gp.player.characterType = 2;
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    } else if(!audioOnOff) {
                        gp.player.characterType = 2;
                        gp.gameState = gp.playState;
                    }
                }
                if(gp.ui.commandNum == 2) {
                    gp.player.newGameStatus = true;
                    if(audioOnOff) {
                        gp.stopMusic();
                        gp.player.characterType = 3;
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    } else if(!audioOnOff) {
                        gp.player.characterType = 3;
                        gp.gameState = gp.playState;
                    }
                }
                if(gp.ui.commandNum == 3) {
                    gp.ui.commandNum = 0;
                    gp.player.newGameStatus = false;
                    gp.ui.titleScreenState = 0;
                }
            }
            if(code == KeyEvent.VK_L) {
                if(languageChange) {
                    languageChange = false;
                } else if(!languageChange) {
                    languageChange = true;
                }
            }
            gp.player.getPlayerImage();
        } else if(gp.ui.titleScreenState == 2) {
            if(code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 4;
                }
            }
            if(code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 4) {
                    gp.ui.commandNum = 0;
                }
            }
            if(gp.ui.commandNum == 0) {
                if(code == KeyEvent.VK_ENTER) {
                    if(audioOnOff) {
                        gp.stopMusic();
                        audioOnOff = false;
                    } else if(!audioOnOff) {
                        gp.playMusic(11);
                        audioOnOff = true;
                    }
                }
            }
            if(gp.ui.commandNum == 1) {
                if(code == KeyEvent.VK_ENTER) {
                    if(SEOnOff) {
                        SEOnOff = false;
                    } else if(!SEOnOff) {
                        gp.playSE(3);
                        SEOnOff = true;
                    }
                }
            }
            if(gp.ui.commandNum == 2) {
                if(audioOnOff) {
                    if(code == KeyEvent.VK_LEFT) {
                        if(gp.ui.volumeNum == 0) {

                        } else if(gp.ui.volumeNum == 1) {
                            gp.soundControl(-80.0f);
                            gp.ui.volumeNum = 0;
                        } else if(gp.ui.volumeNum == 2) {
                            gp.soundControl(-15.0f);
                            gp.ui.volumeNum = 1;
                        } else if(gp.ui.volumeNum == 3) {
                            gp.soundControl(-10.0f);
                            gp.ui.volumeNum = 2;
                        } else if(gp.ui.volumeNum == 4) {
                            gp.soundControl(-5.0f);
                            gp.ui.volumeNum = 3;
                        } else if(gp.ui.volumeNum == 5) {
                            gp.soundControl(0.0f);
                            gp.ui.volumeNum = 4;
                        } else if(gp.ui.volumeNum == 6) {
                            gp.soundControl(1.0f);
                            gp.ui.volumeNum = 5;
                        } else if(gp.ui.volumeNum == 7) {
                            gp.soundControl(2.0f);
                            gp.ui.volumeNum = 6;
                        } else if(gp.ui.volumeNum == 8) {
                          gp.soundControl(3.0f);
                            gp.ui.volumeNum = 7;
                        } else if(gp.ui.volumeNum == 9) {
                            gp.soundControl(4.0f);
                            gp.ui.volumeNum = 8;
                        } else if(gp.ui.volumeNum == 10) {
                            gp.soundControl(5.0f);
                            gp.ui.volumeNum = 9;
                        }
                    }
                    if(code == KeyEvent.VK_RIGHT) {
                        if(gp.ui.volumeNum == 0) {
                            gp.soundControl(-15.0f);
                            gp.ui.volumeNum = 1;
                        } else if(gp.ui.volumeNum == 1) {
                            gp.soundControl(-10.0f);
                            gp.ui.volumeNum = 2;
                        } else if(gp.ui.volumeNum == 2) {
                            gp.soundControl(-5.0f);
                            gp.ui.volumeNum = 3;
                        } else if(gp.ui.volumeNum == 3) {
                            gp.soundControl(0.0f);
                            gp.ui.volumeNum = 4;
                        } else if(gp.ui.volumeNum == 4) {
                            gp.soundControl(1.0f);
                            gp.ui.volumeNum = 5;
                        } else if(gp.ui.volumeNum == 5) {
                            gp.soundControl(2.0f);
                            gp.ui.volumeNum = 6;
                        } else if(gp.ui.volumeNum == 6) {
                            gp.soundControl(3.0f);
                            gp.ui.volumeNum = 7;
                        } else if(gp.ui.volumeNum == 7) {
                            gp.soundControl(4.0f);
                            gp.ui.volumeNum = 8;
                        } else if(gp.ui.volumeNum == 8) {
                            gp.soundControl(5.0f);
                            gp.ui.volumeNum = 9;
                        } else if(gp.ui.volumeNum == 9) {
                            gp.soundControl(6.0f);
                            gp.ui.volumeNum = 10;
                        } else if(gp.ui.volumeNum == 10) {

                        }
                    }
                }
            }
            if(gp.ui.commandNum == 3) {
                if(code == KeyEvent.VK_LEFT) {
                    dificulity--;
                    if(dificulity < 0) {
                        dificulity = 2;
                    }
                }
                if(code == KeyEvent.VK_RIGHT) {
                    dificulity++;
                    if(dificulity > 2) {
                        dificulity = 0;
                    }
                }
            }
            if(code == KeyEvent.VK_ENTER && gp.ui.commandNum == 4) {
                gp.ui.commandNum = 0;
                gp.ui.titleScreenState = 0;
            }
            if(code == KeyEvent.VK_L) {
                if(languageChange) {
                    languageChange = false;
                } else if(!languageChange) {
                    languageChange = true;
                }
            }
        } else if(gp.ui.titleScreenState == 3) {
            if(code == KeyEvent.VK_ENTER) {
                gp.ui.titleScreenState = 0;
            }
            if(code == KeyEvent.VK_L) {
                if(languageChange) {
                    languageChange = false;
                } else if(!languageChange) {
                    languageChange = true;
                }
            }
        } else if(gp.ui.titleScreenState == 4) {
            if(code == KeyEvent.VK_ENTER) {
                gp.ui.commandNum = 0;
                gp.ui.titleScreenState = 0;
            }
            if(code == KeyEvent.VK_L) {
                if(languageChange) {
                    languageChange = false;
                } else if(!languageChange) {
                    languageChange = true;
                }
            }
        } else if(gp.ui.titleScreenState == 5) {
            if(code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = gp.ui.savedGamesAL.size();
                }
            }
            if(code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > gp.ui.savedGamesAL.size()) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum != gp.ui.savedGamesAL.size()) {
                    gp.player.newGameStatus = false;
                    gp.stopMusic();
                    if(audioOnOff) {
                        gp.playMusic(12);
                    }
                    gp.slg.loadGameStats(gp.ui.commandNum);
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandNum == gp.ui.savedGamesAL.size()) {
                    gp.ui.commandNum = 0;
                    gp.ui.titleScreenState = 0;
                }
            }
            if(code == KeyEvent.VK_L) {
                if(languageChange) {
                    languageChange = false;
                } else if(!languageChange) {
                    languageChange = true;
                }
            }
        }
    }
    
    public void playState(int code) {
        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_I) {
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_L) {
            if(languageChange) {
                languageChange = false;
            } else if(!languageChange) {
                languageChange = true;
            }
        }
        if(code == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.stopMusic();
            if(audioOnOff) {
                gp.playMusic(12);
            }
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_Z) {
            if(zPressed) {
                zPressed = false;
            } else {
                zPressed = true;
            }
        }

        //DEBUG
        /*if(code == KeyEvent.VK_T) {
            if(!checkDrawTime) {
                checkDrawTime = true;
            } else if(checkDrawTime) {
                checkDrawTime = false;
            }
        }
        if(code == KeyEvent.VK_G) {
            asd = true;
        }*/
        if(code == KeyEvent.VK_R) {
            gp.tileM.loadMap("/maps/map2.txt");
        }
    }
    public void pauseState(int code) {
        if(code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 5;                    
            }
        }
        if(code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 5) {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.stopMusic();
                if(audioOnOff) {
                    gp.playMusic(0);
                }
                gp.gameState = gp.playState;
            }
            if(gp.ui.commandNum == 1) {
                gp.ui.commandNum = 0;
                gp.gameState = gp.inGameOptionScreen;
            }
            if(gp.ui.commandNum == 2) {
                gp.gameState = gp.inGameControlScreen;
            }
            if(gp.ui.commandNum == 3) {
                if(gp.ui.savedGamesAL.size() < 5) {
                    gp.gameState = gp.inGameSave;
                } else {
                    inGameSaveError = true;
                    gp.gameState = gp.inGameSave;
                }
            }
            if(gp.ui.commandNum == 4) {
                if(gp.ui.savedGamesAL.size() < 5) {
                    gp.gameState = gp.inGameSaveAndQuit;
                } else {
                    inGameSaveAndQuitError = true;
                    gp.gameState = gp.inGameSaveAndQuit;
                }
            }
            if(gp.ui.commandNum == 5) {
                gp.ui.commandNum = 0;
                gp.gameState = gp.RUSure;
            }
        }
        if(code == KeyEvent.VK_L) {
            if(languageChange) {
                languageChange = false;
            } else if(!languageChange) {
                languageChange = true;
            }
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.ui.commandNum = 0;
            gp.stopMusic();
            if(audioOnOff) {
                gp.playMusic(0);
            }
            gp.gameState = gp.playState;
        }
    }
        
    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_L) {
            if(languageChange) {
                languageChange = false;
            } else if(!languageChange) {
                languageChange = true;
            }
        }
    }
    public void characterState(int code) {
        if(code == KeyEvent.VK_UP) {
            if(gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
                if(gp.keyH.SEOnOff) {
                    gp.playSE(5);
                }
            }
        }
        if(code == KeyEvent.VK_DOWN) {
            if(gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
                if(gp.keyH.SEOnOff) {
                    gp.playSE(5);
                }
            }
        }
        if(code == KeyEvent.VK_LEFT) {
            if(gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
                if(gp.keyH.SEOnOff) {
                    gp.playSE(5);
                }
            }
        }
        if(code == KeyEvent.VK_RIGHT) {
            if(gp.ui.slotCol != 6) {
                gp.ui.slotCol++;
                if(gp.keyH.SEOnOff) {
                    gp.playSE(5);
                }
            }
        }
        if(code == KeyEvent.VK_I || code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
        if(code == KeyEvent.VK_L) {
            if(languageChange) {
                languageChange = false;
            } else if(!languageChange) {
                languageChange = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getExtendedKeyCode();
        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
        if(code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
    }

    private void inGameSaveAndQuit(int code) {
        if(code == KeyEvent.VK_ENTER) {
            if(!inGameSaveAndQuitError) {
                gp.slg.saveGame();
                gp.stopMusic();
                if(audioOnOff) {
                    gp.playMusic(11);
                }
                gp.ui.commandNum = 0;
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;
            } else {
                gp.ui.commandNum = 0;
                gp.gameState = gp.inGameNotSavable;
            }
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.ui.commandNum = 0;
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_L) {
            if(languageChange) {
                languageChange = false;
            } else if(!languageChange) {
                languageChange = true;
            }
        }
    }

    private void inGameSave(int code) {
        if(!inGameSaveError) {
            gp.slg.saveGame();
            if(code == KeyEvent.VK_ENTER) {
                gp.ui.commandNum = 0;
                gp.gameState = gp.pauseState;
            }
        } else {
            gp.ui.commandNum = 0;
            gp.gameState = gp.inGameNotSavable;
        }
        if(code == KeyEvent.VK_L) {
            if(languageChange) {
                languageChange = false;
            } else if(!languageChange) {
                languageChange = true;
            }
        }
    }

    private void inGameControlScreen(int code) {
        if(code == KeyEvent.VK_ENTER) {
            gp.ui.commandNum = 0;
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_L) {
            if(languageChange) {
                languageChange = false;
            } else if(!languageChange) {
                languageChange = true;
            }
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
        }
    }

    private void inGameOptionScreen(int code) {
        if(code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 3;
            }
        }
        if(code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 3) {
                gp.ui.commandNum = 0;
            }
        }
        if(gp.ui.commandNum == 0) {
            if(code == KeyEvent.VK_ENTER) {
                if(audioOnOff) {
                    gp.stopMusic();
                    audioOnOff = false;
                } else if(!audioOnOff) {
                    gp.playMusic(12);
                    audioOnOff = true;
                }
            }
        }
        if(gp.ui.commandNum == 1) {
            if(code == KeyEvent.VK_ENTER) {
                if(SEOnOff) {
                    SEOnOff = false;
                } else if(!SEOnOff) {
                    gp.playSE(3);
                    SEOnOff = true;
                }
            }
        }
        if(gp.ui.commandNum == 2) {
            if(audioOnOff) {
                if(code == KeyEvent.VK_LEFT) {
                    if(gp.ui.volumeNum == 0) {

                    } else if(gp.ui.volumeNum == 1) {
                        gp.soundControl(-80.0f);
                        gp.ui.volumeNum = 0;
                    } else if(gp.ui.volumeNum == 2) {
                        gp.soundControl(-15.0f);
                        gp.ui.volumeNum = 1;
                    } else if(gp.ui.volumeNum == 3) {
                        gp.soundControl(-10.0f);
                        gp.ui.volumeNum = 2;
                    } else if(gp.ui.volumeNum == 4) {
                        gp.soundControl(-5.0f);
                        gp.ui.volumeNum = 3;
                    } else if(gp.ui.volumeNum == 5) {
                        gp.soundControl(0.0f);
                        gp.ui.volumeNum = 4;
                    } else if(gp.ui.volumeNum == 6) {
                        gp.soundControl(1.0f);
                        gp.ui.volumeNum = 5;
                    } else if(gp.ui.volumeNum == 7) {
                        gp.soundControl(2.0f);
                        gp.ui.volumeNum = 6;
                    } else if(gp.ui.volumeNum == 8) {
                      gp.soundControl(3.0f);
                        gp.ui.volumeNum = 7;
                    } else if(gp.ui.volumeNum == 9) {
                        gp.soundControl(4.0f);
                        gp.ui.volumeNum = 8;
                    } else if(gp.ui.volumeNum == 10) {
                        gp.soundControl(5.0f);
                        gp.ui.volumeNum = 9;
                    }
                }
                if(code == KeyEvent.VK_RIGHT) {
                    if(gp.ui.volumeNum == 0) {
                        gp.soundControl(-15.0f);
                        gp.ui.volumeNum = 1;
                    } else if(gp.ui.volumeNum == 1) {
                        gp.soundControl(-10.0f);
                        gp.ui.volumeNum = 2;
                    } else if(gp.ui.volumeNum == 2) {
                        gp.soundControl(-5.0f);
                        gp.ui.volumeNum = 3;
                    } else if(gp.ui.volumeNum == 3) {
                        gp.soundControl(0.0f);
                        gp.ui.volumeNum = 4;
                    } else if(gp.ui.volumeNum == 4) {
                        gp.soundControl(1.0f);
                        gp.ui.volumeNum = 5;
                    } else if(gp.ui.volumeNum == 5) {
                        gp.soundControl(2.0f);
                        gp.ui.volumeNum = 6;
                    } else if(gp.ui.volumeNum == 6) {
                        gp.soundControl(3.0f);
                        gp.ui.volumeNum = 7;
                    } else if(gp.ui.volumeNum == 7) {
                        gp.soundControl(4.0f);
                        gp.ui.volumeNum = 8;
                    } else if(gp.ui.volumeNum == 8) {
                      gp.soundControl(5.0f);
                        gp.ui.volumeNum = 9;
                    } else if(gp.ui.volumeNum == 9) {
                        gp.soundControl(6.0f);
                        gp.ui.volumeNum = 10;
                    } else if(gp.ui.volumeNum == 10) {

                    }
                }
            }
        }
        if(gp.ui.commandNum == 3) {
            if(code == KeyEvent.VK_ENTER) {
                gp.ui.commandNum = 0;
                gp.gameState = gp.pauseState;
            }
        }
        if(code == KeyEvent.VK_L) {
            if(languageChange) {
                languageChange = false;
            } else if(!languageChange) {
                languageChange = true;
            }
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.ui.commandNum = 0;
            gp.gameState = gp.pauseState;
        }
    }

    private void inGameNotSavable(int code) {
        if(code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = gp.ui.savedGamesFull.size();
            }
        }
        if(code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > gp.ui.savedGamesFull.size()) {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum != gp.ui.savedGamesFull.size()) {
                gp.slg.deleteSave(gp.ui.savedGamesFull.get(gp.ui.commandNum), gp.ui.commandNum);
                gp.slg.saveGame();
                gp.slg.loadGame();
                gp.gameState = gp.inGameIsSaved;
            }
            if(gp.ui.commandNum == gp.ui.savedGamesFull.size()) {
                gp.gameState = gp.pauseState;
            }
        }
        if(code == KeyEvent.VK_L) {
            if(languageChange) {
                languageChange = false;
            } else if(!languageChange) {
                languageChange = true;
            }
        }
    }

    private void inGameisSaved(int code) {
        if(code == KeyEvent.VK_ENTER) {
            if(inGameSaveError) {
                gp.ui.commandNum = 0;
                inGameSaveError = false;
                gp.gameState = gp.pauseState;
            } else if(inGameSaveAndQuitError) {
                gp.stopMusic();
                if(audioOnOff) {
                    gp.playMusic(11);
                }
                gp.ui.commandNum = 0;
                gp.ui.titleScreenState = 0;
                inGameSaveAndQuitError = false;
                gp.gameState = gp.titleState;
            }
        }
    }

    private void RUSure(int code) {
        if(code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }
        if(code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.stopMusic();
                if(audioOnOff) {
                    gp.playMusic(11);
                }
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;
            }
            if(gp.ui.commandNum == 1) {
                gp.ui.commandNum = 0;
                gp.gameState = gp.pauseState;
            }
        }
    }

    
}
