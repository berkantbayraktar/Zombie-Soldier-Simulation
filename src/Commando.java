/**
 *
 *
 */
public class Commando extends Soldier{

    public Commando(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 10.0,SoldierType.COMMANDO, 2.0, 10.0, SoldierState.SEARCHING);
    }

    @Override
    public void step(SimulationController controller) {

    }
}
