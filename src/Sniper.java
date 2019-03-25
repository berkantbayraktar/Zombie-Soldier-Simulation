/**
 *
 *
 */
public class Sniper extends  Soldier{

    public Sniper(String name, Position position) {// DO NOT CHANGE PARAMETERS
        super(name, position, 2.0,SoldierType.SNIPER, 5.0, 40.0, SoldierState.SEARCHING);
        
    }

    @Override
    public void step(SimulationController controller) {
        switch(this.getSoldier_state()){
            case SEARCHING:

                break;
            case AIMING:
                break;
            case SHOOTING:
                break;
             default:
                 System.out.println("Invalid State");
        }
    }
}
