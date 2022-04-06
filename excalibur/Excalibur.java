package excalibur;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Excalibur {

    
    public static void main(String[] args) {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Excalibur");
        window.setResizable(false);
        
        try {
            InputStream imgStream = Excalibur.class.getResourceAsStream("/pics/icon2.png");
            BufferedImage icon = ImageIO.read(imgStream);
            window.setIconImage(icon);
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();
        
    }
    
}
