package lift.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Floor class provides a single floor within the building.
 * 
 * All the people on the floor is contained in an array where each person
 * is rendered and ticked every time this Floor object renders and ticks.
 * 
 * @author R.S.
 *
 */

public class Floor extends Object{
    /*
     * floorNum indicates which floor this object is for.
     */
    private ArrayList<Person> people = new ArrayList<Person>();
    public final int floorNum;
    @SuppressWarnings("unused")
    private int x, y;
    private Building building;
    private Random r = new Random();
    
    public Floor(int floorNumber, int x, int y, Building building) {
        super(x, y);
        this.floorNum = floorNumber;
        this.building = building;
    }
    
    public void render(Graphics g) {
        int size;
        int stringX = 0, stringY = 0;
        int stringSize;
        
        stringSize = Math.min(20, building.floorHeight);
        Font font = new Font("arial", 1, stringSize);
        g.setFont(font);
        g.setColor(Color.gray);
        g.drawRect(0, floorNum * building.floorHeight, MainLoop.WIDTH, building.floorHeight);
        
        String string = Integer.toString(building.floorsNum - floorNum - 1);
        stringX = Methods.getStringX(0, stringSize, font, string, g);
        stringY = Methods.getStringY(floorNum * building.floorHeight, stringSize, font, string, g, building.floorHeight);
        g.drawString(Integer.toString(building.floorsNum - floorNum - 1), stringX, stringY);
        
        /* Rendering the people */
        int peopleSize = people.size();
        
        if(peopleSize == 0) size = 1;
        else size = Math.min(((Building.liftX - 20) / peopleSize), building.floorHeight);
        
        for(int i = 0; i < peopleSize; i++) {
            people.get(i).renderProper(g, 
                    Building.liftX - ((i + 1) * size),size);
        }
    }
    
    public void tick() {
        
    }
    
    public void tick(Test test) {
        if(test.getPeopleCount() < test.getPeopleNum()) {
            int chance = r.nextInt(100);
            if(chance < 2 || building.getWaitingPeople().size() == 0) {
                addPerson(new Person(floorNum, building));
                test.addPeopleCount();
            }
        }    
            
        for(int i = 0; i < people.size(); i++) {
            people.get(i).tick();
        }
    }
    
    public ArrayList<Person> getPeople(){
        return people;
    }
    
    public void addPerson(Person person) {
        people.add(person);
    }

    public void removePerson(Person person) {
        people.remove(person);
    }
}
