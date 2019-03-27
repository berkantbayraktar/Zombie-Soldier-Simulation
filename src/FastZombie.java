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
        double distance_to_closest_soldier = calculateDistanceToEnemy(controller);
        SimulationObject closest_soldier = nearestEnemy(controller);

        if(distance_to_closest_soldier <= this.getCollision_range() + ((Soldier)closest_soldier).getCollision_range()){
            System.out.println(this.getName() + " killed " + closest_soldier.getName() + ".");
            controller.removeSimulationObject(closest_soldier);
            return;
        }

        if(this.isFirstStep()){  // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL
            this.setRandomDirection();
            this.setFirstStep(false);
        }

    }

}
