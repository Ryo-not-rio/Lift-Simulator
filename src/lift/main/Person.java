package lift.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

/**
 * 
 * The Person Class provides a class for a single person where the floor
 * the person is on, the floor they want to go to and whether or not
 * they have boarded the lift is indicated.
 * 
 * @author R.S.
 *
 */
public class Person extends Object{

    private Random r = new Random();
    private int targetFloor;
    private int floor;
    private boolean boarded = false;
    private Building building;
    private Lift.STATE state;
    
    public Person(int floor, Building building) {
        super((int) (Building.liftX - (building.floorHeight/2)), (int) ((building.floorsNum - floor - 1) * building.floorHeight));

        this.id = ID.Person;
        this.floor = floor;
        this.building = building;
        this.targetFloor = chooseFloor();
        
        if(this.targetFloor - floor > 0) this.state = Lift.STATE.Up;
        else this.state = Lift.STATE.Down;
    }

    /**
     * Choose a random floor to go to which is not the current floor the 
     * person is on.
     * 
     * @return Integer indicating the target floor.
     */
    private int chooseFloor() {
        int tempFloor = r.nextInt(building.floorsNum);
        while(tempFloor == floor) {
            tempFloor = r.nextInt(building.floorsNum);
        }
        return tempFloor;
    }
    
    public void render(Graphics g) {
        
    }
    
    public void renderProper(Graphics g, int stringX, int size) {
        int stringY;
        
        Font font = new Font("arial", 1, size * 2 / 3);
        String showString = Integer.toString(floor) + " ";
        
        stringX  = Methods.getStringX(stringX, size, font, showString, g);
        stringY = Methods.getStringY(y, size, font, showString, g, building.floorHeight);
        
        g.setFont(font);
        g.setColor(Color.cyan);
        g.drawString(Integer.toString(targetFloor),  stringX, stringY);
    }
    
    public void tick() {
        
    }
    
    public int getTargetFloor() {
        return targetFloor;
    }
    
    public int getFloor() {
        return floor;
    }
    
    public boolean getBoarded(){
        return boarded;
    }
    
    public void setBoarded(boolean boarded) {
        this.boarded = boarded;
    }
    
    public Lift.STATE getState(){
        return state;
    }

}
