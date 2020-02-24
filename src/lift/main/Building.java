package lift.main;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * The Building class contains an array for floors within the building and
 * an array for lifts in the building. Each render and tick renders and ticks
 * all objects in each array.
 * 
 * @author R.S.
 *
 */

public class Building{
    
    /*
     * Change floorsNum to change the number of floors in the building.
     * liftX indicates the x coordinate to render the lift from.
     * floorHeight is the height of each floor in pixels.
     */
    private Test test;
    public static int liftX = MainLoop.WIDTH/2;
    
    public int floorsNum;
    private double floorHeightFloat;
    
    public int floorHeight;
    public boolean testAdded = false;
    
    private ArrayList<Floor> floorsArray = new ArrayList<Floor>();
    private ArrayList<Lift> liftsArray = new ArrayList<Lift>();
        
    public Building(int floors, Test test) {
        this.floorsNum = floors;
        this.floorHeightFloat = MainLoop.actualHeight/floorsNum;
        this.floorHeight = Math.round(MainLoop.actualHeight/floorsNum);
        this.test = test;

        for(int i = 0; i < floorsNum; i++) {
            floorsArray.add(new Floor(i, 0, (int) Math.round(i * floorHeightFloat), this));
        }
    }
    
    public void render(Graphics g) {
        for(int i = 0; i < floorsNum; i++) {
            floorsArray.get(i).render(g);
        }
        for(int i = 0; i < liftsArray.size(); i++) {
            liftsArray.get(i).render(g);
        }
    }
    
    public void tick() {
        this.testAdded = false;
        for(int i = 0; i < floorsNum; i++) {
            floorsArray.get(i).tick(test);
        }
        for(int i = 0; i < liftsArray.size(); i++) {
            liftsArray.get(i).tick();
        }
        if ((getWaitingPeople().size() == 0) && 
                getBoardedPeople().size() == 0 &&
                !test.needMorePeople()) test.finish(true);
    }
    
    public ArrayList<Floor> getFloors(){
        return floorsArray;
    }
    
    public void addPerson(int floor, Person person) {
        floorsArray.get(floor).addPerson(person);
    }
    
    public void removePerson(int floor, Person person) {
        floorsArray.get(floor).removePerson(person);
    }
    
    public void addLift(Lift lift) {
        liftsArray.add(lift);
    }
    
    public int[] getWaits() {
        int[] maxes = new int[floorsArray.size()];
        for(int i=0; i<floorsArray.size(); i++) {
            maxes[i] = floorsArray.get(i).getLastVisited();
        }
        return maxes;
    }
    
    /**
     * Return all people that is waiting.
     * 
     * @return array list containing every waiting Person object.
     */
    
    public ArrayList<Person> getWaitingPeople() {
        ArrayList<Person> returnList = new ArrayList<Person>();
        for(int i = 0; i < floorsArray.size(); i++) {
            returnList.addAll(floorsArray.get(i).getPeople());
        }
        return returnList;
    }
    
    public ArrayList<Person> getBoardedPeople() {
        ArrayList<Person> returnList = new ArrayList<Person>();
        for(int i = 0; i < floorsArray.size(); i++) {
            returnList.addAll(floorsArray.get(i).getPeople());
        }
        return returnList;
    }
    
    public void setFloorsNum(int floors) {
        this.floorsNum = floors;
    }
    
    public boolean isLastFloor() {
        int count = 0;
        for(int i = 0; i < floorsArray.size(); i++) {
            if(count > 1) return false;
            if(!(floorsArray.get(i).getPeople().isEmpty())) count ++;
        }
        return true;
    }
    
}
