package lift.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TickButton extends Button{
    private boolean ticked;
    
    public TickButton(int x, int y, int width, int height) {
        super("", x, y, width, height);
        this.ticked = false;
    }

    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(x, y, width, height);

        int size = (int) (height * 0.6);
        Font font = new Font("ariel", 1, size);
        g.setFont(font);
        
        int stringX = Methods.getStringX(x + width/2, size, font, name, g);
        int stringY = Methods.getStringY(y, height, font, Integer.toString(value), g, height);
        g.drawString(name, stringX, stringY);
    }
    
    public void select() {
        this.ticked = !ticked;
        this.name = ticked ? "✓" : "";
    }
    
    public boolean getTicked() {
        return ticked;
    }
}
