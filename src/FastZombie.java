/**
 *
 *
 */
public class FastZombie extends Zombie {

    public FastZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 20.0, ZombieType.FAST, ZombieState.WANDERING, 2.0,20.0, 0);
    }

    @Override
    public void step(SimulationController controller) {

    }

}
