package excalibur;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Sound {
    GamePanel gp;
    Clip clip;
    URL soundURL[] = new URL[20];
    
    public Sound(GamePanel gp) {
        
        this.gp = gp;
        
        soundURL[0] = getClass().getResource("/sound/gameplay.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/fanfare.wav");
        soundURL[3] = getClass().getResource("/sound/powerup.wav");
        soundURL[4] = getClass().getResource("/sound/unlock.wav");
        soundURL[5] = getClass().getResource("/sound/cursor.wav");
        soundURL[6] = getClass().getResource("/sound/hitmonster.wav");
        soundURL[7] = getClass().getResource("/sound/levelup.wav");
        soundURL[8] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[9] = getClass().getResource("/sound/swinging.wav");
        soundURL[10] = getClass().getResource("/sound/equip1.wav");
        soundURL[11] = getClass().getResource("/sound/mainMenuMusic.wav");
        soundURL[12] = getClass().getResource("/sound/pauseScreenMusic.wav");
        soundURL[13] = getClass().getResource("/sound/burning.wav");
        
    }
    
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    
    public void soundControl(float asd) {
        clip.stop();
        FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(asd);
        clip.start();
    }
    
    public void play() {
        FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        if(gp.ui.volumeNum == 0) {
            gainControl.setValue(-80.0f);
        } else if(gp.ui.volumeNum == 1) {
            gainControl.setValue(-15.0f);
        } else if(gp.ui.volumeNum == 1) {
            gainControl.setValue(-10.0f);
        } else if(gp.ui.volumeNum == 1) {
            gainControl.setValue(-5.0f);
        } else if(gp.ui.volumeNum == 1) {
            gainControl.setValue(0.0f);
        } else if(gp.ui.volumeNum == 1) {
            gainControl.setValue(1.0f);
        } else if(gp.ui.volumeNum == 1) {
            gainControl.setValue(2.0f);
        } else if(gp.ui.volumeNum == 1) {
            gainControl.setValue(3.0f);
        } else if(gp.ui.volumeNum == 1) {
            gainControl.setValue(4.0f);
        } else if(gp.ui.volumeNum == 1) {
            gainControl.setValue(5.0f);
        } else if(gp.ui.volumeNum == 1) {
            gainControl.setValue(6.0f);
        }
        clip.start();
    }
    
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop() {
        clip.stop();
    }
}
