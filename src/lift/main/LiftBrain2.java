package lift.main;

public class LiftBrain2 extends LiftBrain{
    @Override
    public int getLiftNum(int floors){
        return Methods.fixLimit(2.3 * Math.log(2.6 * (floors-4.8))+4.1, 15, 1);
    }
}
