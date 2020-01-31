package lift.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Lift class provides a lift object that can be rendered, a method for
 * people to board the lift, get off the lift and the lift to go up, down and stop.
 * 
 * This class does not implement the algorithm to control the lift but moves the lift
 * according to the instructions given by the LiftBrain object.
 * 
 * The main algorithm this class is concerned with is when the lift has stopped. 
 * When the lift is on a floor, first, everyone that needs to get off gets off. 
 * Next, all the people on that floor will get in the lift in the order they were
 * created until the lift is full. Then, it will go up or down depending on the 
 * command given by the lift control algorithm(LiftBrain) used.
 * 
 * @author R.S.
 *
 */

public class Lift extends Object{
    /*
     * The array list people contains all Person objects that are
     * currently on the lift. Change the delaySeconds to change the speed
     * of the elevator.
     */
    public static enum STATE {
        Stop, Up, Down
    };
    
    private ReentrantLock lock = new ReentrantLock();
    
    public final int CAPACITY = 5;
    
    @SuppressWarnings("unused")
    private ID id;
    private LiftBrain liftBrain;
    private Building building;
    private Test test;
    private int floors, currentFloor = 0;
    public static int width = 30;
    
    private ArrayList<Person> people = new ArrayList<Person>();
    
    private STATE state = STATE.Stop;
    private STATE previousState = STATE.Up;
    private int targetFloor = -1; //Indicates the program just started
    
    /**
     * 
     * @param x: x coordinate where the elevator starts
     * @param y: y coordinate where the elevator starts
     * @param floors: number of floors
     * @param liftBrain: LiftBrain object for the algorithm used
     * @param building: Building object which this lift is a part of
     */
    public Lift(int x, int y, int floors, LiftBrain liftBrain, Building building, Test test) {
        super(x, y);
        this.id = ID.Lift;
        this.floors = floors;
        this.liftBrain = liftBrain;
        this.building = building;
        this.test = test;
    }

    public void tick() {
        if(this.state == STATE.Up) {
            up();
        } else if(this.state == STATE.Down) {
            down();
        } else {
            stop();
        }
    }
    
    public void render(Graphics g) {
        int stringX = 0, stringY = 0;
        
        for(int i = 0; i < floors; i++) {
            g.setColor(Color.red);
            g.drawRect(x, building.floorHeight * i, width, building.floorHeight);
        }
        
        if(state == STATE.Stop) g.setColor(Color.blue);
        else g.setColor(Color.orange);
        
        int currentY = (floors - currentFloor - 1) * building.floorHeight;
        g.fillRect(x, currentY, width, building.floorHeight);
        
        Font font = new Font("ariel", 1, Math.min(building.floorHeight, width));        
        String showString = Integer.toString(targetFloor);
        stringX = Methods.getStringX(x, width, font, showString, g);
        stringY = Methods.getStringY(currentY, building.floorHeight, 
                font, showString, g, building.floorHeight);
        
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString(Integer.toString(targetFloor), stringX, stringY);
    }
    
    /**
     * Go up to the next floor after a given delay, and ask the liftBrain whether
     * it should go up, down or stop next.
     */
    private void up() {
        currentFloor++;
        
        test.addStep();
        state = liftBrain.getNextMove(this, building);        
    }
    
    /**
     * Same as going up but going down.
     */
    private void down() {
        currentFloor--;
        test.addStep();
        state = liftBrain.getNextMove(this, building);
    }
    
    /**
     * Get everyone that needs to get off the lift off, get people on to the lift,
     * then get whether it should go up, down or keep stopped from the liftBrain.
     */
    private void stop() {
        if ((people.size() > 0) || building.getWaitingPeople().size() > 0) {
            getOff();
            board();
            STATE tempState = liftBrain.getNextMove(this, building);
            this.targetFloor = liftBrain.getNextFloor(this, tempState, building);
            this.state = tempState;
        } 
    }
    
    /* Get the next person that needs to get off */
    public Person needToGetOff() {
        for(int i = 0; i < people.size(); i++) {
            if(people.get(i).getTargetFloor() == currentFloor) return people.get(i);
        }
        return null;
    }
    
    /**
     * Keep boarding people on the floor until either the lift is full or
     * there is no more people to board.
     */
    private void board() {
        ArrayList<Person> waitingPeople = building.getFloors().get(currentFloor).getPeople();
        
        int personIndex = liftBrain.needToBoard(waitingPeople, 0, this, building);
        while((0 <= personIndex) && (personIndex < waitingPeople.size()) && (this.people.size() < CAPACITY)){
            Person person = waitingPeople.get(personIndex);
            this.people.add(person);
            building.removePerson(currentFloor, person);
            person.setBoarded(true);
            personIndex = liftBrain.needToBoard(waitingPeople, personIndex, this, building);
        }
    }
    
    private void getOff() {
        while(needToGetOff() != null) {
            Person person = needToGetOff();
            people.remove(person);
        }
    }
    
    public STATE getState() {
        return state;
    }
    
    public STATE getPreviousState() {
        return previousState;
    }
    
    public void setState(STATE state) {
        this.state = state;
    }
    
    public void setPreviousState(STATE state) {
        this.previousState = state;
    }
    
    public int getTargetFloor() {
        return targetFloor;
    }
    
    public int getCurrentFloor() {
        return currentFloor;
    }
    
    public ArrayList<Person> getPeople(){
        return people;
    }
    
}
