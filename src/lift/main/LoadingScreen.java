package lift.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class LoadingScreen {
    public LoadingScreen(){
        
    }
    
    public void render(Graphics g) {
        int size = 50;
        Font font = new Font("ariel", 1, size);
        g.setFont(font);
        g.setColor(Color.WHITE);
        String title = "testing...";
        int stringX = Methods.getStringX(MainLoop.WIDTH/2, size, font, title, g);
        g.drawString(title, stringX, 100);
    }
}
