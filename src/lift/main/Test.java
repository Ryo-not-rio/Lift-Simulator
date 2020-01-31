package lift.main;

import java.util.ArrayList;
import java.util.Random;

public class Test {
    private int steps;
    private int floorsNum, peopleNum;
    private int maxPeopleOnFloor;
    private int maxPeople;
    private int maxFloors;
    private int liftNum;
    private int maxLiftNum;
    private int testNum;
    private int peopleCount;
    
    private Building building;
    private LiftBrain liftBrain;    
    private MainLoop mainLoop;
    private long startTime = System.currentTimeMillis();
    private int count = 0;
    
    private Random r = new Random();
    
    public Test(MainLoop mainLoop) {
        this.mainLoop = mainLoop;
    }
    
    
    private void initialise() {
        /* Adjust floorsNum and peopleNum to change the range of parameters*/
        this.liftBrain = new ImprovedBrain2();
        
        if(mainLoop.testing) {
            this.floorsNum = maxFloors > 0 ? r.nextInt(maxFloors / 2) * 2 + 5 : floorsNum;
            this.peopleNum = maxPeople > 0 ? r.nextInt((maxPeople / 5) - 5) * 5 + 5 : peopleNum;
            this.liftNum = maxLiftNum > 0 ? r.nextInt(maxLiftNum - 1) + 1 : liftNum;
            this.testNum = (int) (Math.max(maxFloors, floorsNum)/2 * Math.max(maxPeople, peopleNum)/5);
        } else {
            this.testNum = 1;
        }
        
        this.building = new Building(this.floorsNum, this);
        
        this.steps = 0;
        this.peopleCount = 0;
        this.building = new Building(floorsNum, this);
        
        for(int i = 0; i < this.liftNum; i++) {
            building.addLift(new Lift(Building.liftX + (i * Lift.width), 0, building.floorsNum, liftBrain, building, this));
        }
            
        this.maxPeopleOnFloor = getMaxPeople();
    }
    
    private int getMaxPeople() {
        int max = 0;
        ArrayList<Floor> floors = new ArrayList<Floor>(building.getFloors()); 
        
        for(int i = 0; i < floors.size(); i++) {
            int size = floors.get(i).getPeople().size();
            if(size > max){
                max = size;
            }
        }
        
        return max;
    }
    
    public void addStep() {
        if(!building.testAdded) {
            steps += building.getWaitingPeople().size();
            building.testAdded = true;
        }
        
        if(((System.currentTimeMillis() - startTime)/1000 > 90) && mainLoop.testing){
            mainLoop.setTesting(false);
            mainLoop.setDelaySeconds(1);
            //finish(false);
        }
    }
    
    public void finish(boolean finished) {
        startTime = System.currentTimeMillis();
        mainLoop.setDelaySeconds(0f);
        
        if(finished) ExcelWriter.write(this);
        else System.out.println("Timed out");
        
        if(!mainLoop.testing) {
            mainLoop.setRunningState(MainLoop.RUN_STATE.Finished);
        }else {            
            if(count > testNum) {
                mainLoop.setRunningState(MainLoop.RUN_STATE.Finished);
                mainLoop.setTesting(false);
            } else {
                count ++;
                initialise();
                mainLoop.setBuilding(building);
            }
        }
    }
    
    public int getPeopleCount() {
        return peopleCount;
    }
    
    public boolean needMorePeople() {
        return peopleCount < peopleNum;
    }
    
    public void addPeopleCount() {
        peopleCount++;
    }
    
    public LiftBrain getBrain() {
        return liftBrain;
    }
    
    public Building getBuilding() {
        return building;
    }
    
    public int getSteps() {
        return steps;
    }
    
    public int getFloorNum() {
        return floorsNum;
    }
    
    public int getPeopleNum() {
        return peopleNum;
    }
    
    public int getMaxPeopleOnFloor() {
        return maxPeopleOnFloor;
    }
    
    public int getLiftNum() {
        return liftNum;
    }
    
    public void setFloorNum(int number) {
        this.floorsNum = number;
        initialise();
    }
    
    public void setPeopleNum(int number) {
        this.peopleNum = number;
        initialise();
    }
    
    public void setLiftNum(int number) {
        this.liftNum = number;
        initialise();
    }
    
    public void setMaxFloorNum(int number) {
        this.maxFloors = number;
        initialise();
    }
    
    public void setMaxPeopleNum(int number) {
        this.maxPeople = number;
        initialise();
    }
    
    public void setMaxLiftNum(int number) {
        this.maxLiftNum = number;
        initialise();
    }
}
