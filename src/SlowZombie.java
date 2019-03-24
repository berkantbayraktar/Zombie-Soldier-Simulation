/**
 *
 *
 */
public class SlowZombie extends  Zombie{

    public SlowZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name,position, 2.0, ZombieType.SLOW, ZombieState.WANDERING, 1.0, 40.0, 0);
    }

    @Override
    public void step(SimulationController controller) {

    }
}
