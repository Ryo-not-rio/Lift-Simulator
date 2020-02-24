package lift.main;

import java.util.ArrayList;
import java.util.Arrays;

public class BrainWithWeights extends ImprovedBrain2{
    int floor;
    
    @Override
    public int getNextFloor(Lift lift, Lift.STATE state, Building building) {
        ArrayList<Person> people = new ArrayList<Person>(lift.getPeople());
        if(people.size() < lift.CAPACITY){
            people.addAll(building.getWaitingPeople());
        }
            
        if(people.size() == 0) return 0; // No more people left so go to bottom floor.
        
        int currentFloor = lift.getCurrentFloor();
        
        if(lift.getPeople().size() == 0) {
            if(lift.getState() == Lift.STATE.Stop) {
                int[] weights = calculateWeight(building, currentFloor);
                floor = Methods.getMaxIndex(weights);
            
                //Floor currentFloorObj = building.getFloors().get(currentFloor);

                return floor;
                //return usualFloor(people, lift, currentFloor, state);
            }            
            return floor;
        }
        return usualFloor(people, lift, currentFloor, state);
    }
    
    private int usualFloor(ArrayList<Person> people, Lift lift, int currentFloor, Lift.STATE state) {
        int[] floors = new int[people.size() + 1];
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
        floor = floors[floorIndex];
        return floor;
    }
    
    private int[] getDistances(int currentFloor, Building building) {
        int[] returnArray = new int[building.floorsNum];
        for(int i=0; i < building.floorsNum; i++) {
            returnArray[i] = building.floorsNum - Math.abs(currentFloor - i);
        }
        return returnArray;
    }
    
    private int[] calculateWeight(Building building, int currentFloor) {
        int[] visited = building.getWaits();
        int[] distances = getDistances(currentFloor, building);
        
        for(int i=0; i<visited.length; i++) {
            visited[i] *= distances[i];
        }
        
        return visited;
    }
}
