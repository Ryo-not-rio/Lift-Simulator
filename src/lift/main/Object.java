package lift.main;

import java.awt.Graphics;

/**
 * The Object class provides the basic frame for every object in the program.
 * 
 * @author R.S.
 *
 */

public abstract class Object {
    protected int x, y, width, height;
    protected ID id;
    
    public Object(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
