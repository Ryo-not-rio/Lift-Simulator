package lift.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;

public class Button {
    protected int x, y, width, height,value;
    private boolean moveSlider = false;
    protected String name;
    
    public Button(String name, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
    }
    
    public boolean clicked(int mx, int my) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            } else
                return false;
        } else
            return false;
    }
    
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

    public int getValue() {
        return this.value;
    }
    
    public boolean getMove() {
        return moveSlider;
    }
    
    public void setMove(boolean move) {
        this.moveSlider = move;
    }
}
