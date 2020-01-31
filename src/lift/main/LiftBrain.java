package lift.main;

import java.util.ArrayList;
import java.util.Arrays;

import lift.main.Lift.STATE;

/**
 * The LiftBrain implements the baseline algorithm for the lift.
 * It starts on the 0th floor, goes up, stopping at floors where people
 * need to either board or get off. If the lift is full, it will not stop
 * at floors where people are waiting. When it hits the top, the lift starts
 * going down, loading and unloading people. The lift continues going up and down,
 * changing direction only when it hits the top or the bottom floor.
 * 
 * If no more people are left, goes to bottom floor.
 * 
 * @author R.S.
 *
 */

public class LiftBrain {
    
    /**
     * Tells the lift what to do next.
     * If at top floor, tell it to go down, if at bottom floor, go up,
     * if at a floor the lift needs to stop at, stop.
     * 
     * @param lift: Lift object asking for direction
     * @param building: Building object the lift is a part of
     * @return a state indicating the next move of lift
     */
    public Lift.STATE getNextMove(Lift lift, Building building) {
        int targetFloor = lift.getTargetFloor();
        int currentFloor = lift.getCurrentFloor();
        STATE previousState = lift.getPreviousState();
        
        /*
         * If the program has just started, find the next floor the lift needs
         * to go to.
         */
        if(targetFloor <= -1) {
            targetFloor = getNextFloor(lift, previousState, building);
        }
        
        if(targetFloor == lift.getCurrentFloor()) {
            if(lift.getState() != Lift.STATE.Stop)  return Lift.STATE.Stop;
        }
        
        if(previousState == Lift.STATE.Up) {
            if(currentFloor >= building.floorsNum -1) { // Reached the top
                lift.setPreviousState(Lift.STATE.Down);
                return Lift.STATE.Down;
            }
            return Lift.STATE.Up;
        }
            
        if(currentFloor <= 0) {
            lift.setPreviousState(Lift.STATE.Up); // Reached the bottom
            return Lift.STATE.Up;
        }
        return Lift.STATE.Down;
    }
    
    /**
     * Method to get the next floor the lift needs to go to.
     * 
     * Goes through all the people both on and not on the lift,
     * looks at where people on the lift is going and where people are
     * waiting to board the lift and returns the nearest floor in the direction
     * the elevator is going. If the lift is full, does not look at the people 
     * waiting for the lift.
     * 
     * @param lift: The lift calling this method.
     * @param state: The current direction the lift is going.
     * @param building: The building the lift is in.
     * @return The floor the lift should go to next.
     */
    
    public int getNextFloor(Lift lift, Lift.STATE state, Building building) {
        ArrayList<Person> people = new ArrayList<Person>(lift.getPeople());
        if(people.size() < lift.CAPACITY){
            people.addAll(building.getWaitingPeople());
        }
            
        if(people.size() == 0) return 0; // No more people left so go to bottom floor.
        
        int[] floors = new int[people.size() + 1];
        int currentFloor = lift.getCurrentFloor();
        
        /* Create an array of the floors people are waiting on
         * and people on the lift need to go to.
         */
        for(int i = 0; i < people.size(); i++) {
            Person person = people.get(i);
            if(person.getBoarded()) {
                floors[i] = person.getTargetFloor();
            } else {
                floors[i] = person.getFloor();
            }
        }
        
        /* Add the current floor the lift is on to the array of
         * floors, sort the array, and look at the item before or
         * after the array depending on which way the lift is going.
         */
        floors[floors.length - 1] = lift.getCurrentFloor();
        Arrays.parallelSort(floors);
        
        int indexOfFloor = Arrays.binarySearch(floors, currentFloor);        
        
        int floorIndex = -2;

        if((state == Lift.STATE.Up && indexOfFloor == floors.length - 1) || 
                (state == Lift.STATE.Down && indexOfFloor > 0)) {
            floorIndex = indexOfFloor - 1;
            while (floors[floorIndex] == currentFloor && floorIndex > 0) {
                floorIndex--;
            }
        }else {
            floorIndex = indexOfFloor + 1;
            while (floors[floorIndex] == currentFloor && floorIndex < floors.length - 1 ) {
                floorIndex++;
            }
        }
        return floors[floorIndex];
    }
    
    public int getLiftNum(int floors) {
        return 1;
    }
    
    
    /* Get the next person to get on board */
    public int needToBoard(ArrayList<Person> waitingPeople, int startIndex, Lift lift, Building building) {

        for(int i = startIndex; i < waitingPeople.size(); i++) {
            Person person = waitingPeople.get(i);
            int targetFloor = person.getTargetFloor();
            
            if(lift.getPreviousState() == STATE.Up) {
                if(lift.getCurrentFloor() == building.floorsNum - 1) { //Going up and at top
                    if(targetFloor < lift.getCurrentFloor()) return i;
                }else {
                    if(targetFloor > lift.getCurrentFloor()) return i;
                }
            } else {
                if(lift.getCurrentFloor() == 0) { //Going down and at bottom
                    if(targetFloor > lift.getCurrentFloor()) return i;
                }else {
                    if(targetFloor < lift.getCurrentFloor()) return i;
                }
            }
        }
        return -1;
    }
}
