package lift.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;

public class Slider {
    private int x, y, width, height, min, max, value;
    private int currentX = 0;
    private int absoluteX;
    private boolean moveSlider = false;
    private String name;
    
    public Slider(String name, int x, int y, int width, int height, int min, int max) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.min = min;
        this.max = max;
        this.value = min;
        this.name = name;
    }
    
    public boolean clickedSlider(int mx, int my) {
        if (mx > x + currentX && mx < currentX + x + 30) {
            if (my > y && my < y + height) {
                this.absoluteX = (int) MouseInfo.getPointerInfo().getLocation().getX();
                return true;
            } else
                return false;
        } else
            return false;
    }
    
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(x, y, width, height);
        g.setColor(Color.GRAY);
        g.fillRect(x + currentX, y, 30, height);
        
        int size = (int) (height * 0.6);
        Font font = new Font("ariel", 1, size);
        g.setFont(font);
        
        int stringX = Methods.getStringX(x - 150, size, font, name, g);
        int stringY = Methods.getStringY(y, height, font, Integer.toString(value), g, size);
        g.drawString(name, stringX, stringY);        
        g.drawString(Integer.toString(value), x + width + 10, stringY);
        
    }
    
    public void setX() {
        int mouseX = (int) Math.round(MouseInfo.getPointerInfo().getLocation().getX());
        int newCoordinate = currentX + mouseX - this.absoluteX;
        this.currentX = Methods.fixLimit(newCoordinate, width - 30, 0);

        this.value = (int) ((this.currentX * ((float) (max - min)/(float) (width-30))) + min);

        if(newCoordinate >= 0 && newCoordinate <= width - 30) {
            this.absoluteX = mouseX;
        }
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
