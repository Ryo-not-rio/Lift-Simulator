package lift.main;

import java.util.ArrayList;
import java.util.Arrays;

import lift.main.Lift.STATE;

public class ImprovedBrain2 extends LiftBrain{
    @Override
    public Lift.STATE getNextMove(Lift lift, Building building) {
        int targetFloor = lift.getTargetFloor();
        int currentFloor = lift.getCurrentFloor();
        STATE state = lift.getState();
        STATE previousState = lift.getPreviousState();
        
        /*
         * If the program has just started, find the next floor the lift needs
         * to go to.
         */
        if(targetFloor <= -1) {
            targetFloor = getNextFloor(lift, state, building);
        }
        
        if(targetFloor == currentFloor) {
            if(lift.getState() != Lift.STATE.Stop && 
                    (needToStop(building.getFloors().get(currentFloor), lift.getState(), building) || needToGetOff(lift.getPeople(), currentFloor))) {
                return Lift.STATE.Stop;
            }
            else {
                targetFloor = getNextFloor(lift, state, building);
            }
        }

        if(targetFloor > currentFloor) {
            return Lift.STATE.Up;
        }
        if(targetFloor < currentFloor) {
            return Lift.STATE.Down;
        }
        
        return Lift.STATE.Stop;
    }

    @Override
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
        
        if(state == Lift.STATE.Stop) state = lift.getPreviousState();
        
        if((state == Lift.STATE.Up && indexOfFloor == floors.length - 1) || 
                (state == Lift.STATE.Down && indexOfFloor > 0)) {
            floorIndex = indexOfFloor;
            while (floorIndex > 0 && floors[floorIndex] == currentFloor) {
                floorIndex--;
            }
        }else {
            floorIndex = indexOfFloor;
            while (floorIndex < floors.length - 1 && floors[floorIndex] == currentFloor) {
                floorIndex++;
            }
        }
        return floors[floorIndex];
    }
    
    @Override
    /* Get the next person to get on board */
    public int needToBoard(ArrayList<Person> waitingPeople, int startIndex, Lift lift, Building building) {

        for(int i = startIndex; i < waitingPeople.size(); i++) {
            Person person = waitingPeople.get(i);
            int targetFloor = person.getTargetFloor();
            
            if(building.isLastFloor()) return i;
            if(lift.getPreviousState() == STATE.Up) {
                if(getNextFloor(lift, lift.getPreviousState(), building) <= lift.getCurrentFloor()) { //Going up and at top
                    return i;
                }else {
                    if(targetFloor > lift.getCurrentFloor()) return i;
                }
            } else {
                if(getNextFloor(lift, lift.getPreviousState(), building) >= lift.getCurrentFloor()) { //Going down and at bottom
                    return i;
                }else {
                    if(targetFloor < lift.getCurrentFloor()) return i;
                }
            }
        }
        return -1;
    }
}
