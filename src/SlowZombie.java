/**
 * <p> <b>SlowZombie </b>, derived from Zombie Class, represents the slow zombies in the simulation. </p>
 * @author Berkant Bayraktar
 * @version 1.0
 */
public class SlowZombie extends  Zombie{
    /**
     * <p> Creates SlowZombie object with the given parameters. </p>
     * <p> Name and position of the zombies are given to the parent class's constructor as parameter.</p>
     * <p> Other values which specific to the Slow Zombie type is also given to parent class's constructor as parameter. </p>
     * <p> Values specific to the Slow Zombie type are given below</p>
     * <ul>
     *     <li> {@link ZombieState}: SEARCHING </li>
     *     <li> {@link ZombieType}: REGULAR</li>
     *     <li> collision range of the Zombie: 1.0</li>
     *     <li> detection range of the Zombie: 40.0</li>
     *     <li> speed of the Zombie: 2.0 </li>
     * </ul>
     * @param name Name of the Slow Zombie
     * @param position Position of the Slow Zombie
     */

    public SlowZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name,position, 2.0, ZombieType.SLOW, ZombieState.WANDERING, 1.0, 40.0);
    }

    /**
     * <p> SlowZombie object uses this method to be able to act in the simulation. </p>
     * <p> SlowZombie object can be in two different state which are {@link ZombieState#WANDERING}, {@link ZombieState#FOLLOWING}  </p>
     * <p> The instructions according to the states are given below.</p>
     * <p> State: WANDERING </p>
     * <ul>
     *     <li> If this is the first step call, selects zombies direction randomly.</li>
     *     <li> Calculates the euclidean distance to the closest soldier.</li>
     *     <li> If the distance is shorter than or equal to the detection range of the zombie, changes state to
     * FOLLOWING and return.</li>
     *     <li> If not calculates the next position of the zombie </li>
     *     <li> If the position is out of bounds, changes direction to random value. </li>
     *     <li> If the position is not out of bounds, changes zombie position to the new_position. </li>
     * </ul>
     * <p> State: FOLLOWING </p>
     * <ul>
     *     <li> Calculates the euclidean distance to the closest soldier. </li>
     *     <li> If the distance is shorter than or equal to the detection range of the zombie, changes direction to soldier.</li>
     *     <li> Calculates the next position of the zombie with formula: new_position = position + direction * speed </li>
     *     <li> If the position is out of bounds, changes direction to random value. </li>
     *     <li> If the position is not out of bounds, changes zombie position to the new_position. </li>
     *     <li> Uses the calculated distance to the closest soldier in the first step. If the distance is shorter
     * than or equal to the detection range of the zombie, changes state to WANDERING.</li>
     * </ul>
     *
     * @param controller Simulation Controller
     */

    @Override
    public void step(SimulationController controller) {
        double distance = calculateDistanceToEnemy(controller);
        SimulationObject enemy = nearestEnemy(controller);

        if(this.tryToKill(controller,enemy,distance))
            return;

        switch (this.getZombie_state()){
            case WANDERING:

                if(this.isFirstStep()){  // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL
                    this.setRandomDirection();
                    this.setFirstStep(false);
                }

                if(distance <= this.getDetection_range()){
                    this.setZombie_state(ZombieState.FOLLOWING);
                    System.out.println(this.getName() + " changed state to " + this.getZombie_state() + ".");
                    return;
                }
                else{
                    this.moveOrChangeDirection(controller);
                }
                break;

            case FOLLOWING:
                if(distance <= this.getDetection_range()){
                    this.turnToEnemy(enemy);
                }
                this.moveOrChangeDirection(controller);

                if(distance <= this.getDetection_range()){
                    this.setZombie_state(ZombieState.WANDERING);
                    System.out.println(this.getName() + " changed state to " + this.getZombie_state() + ".");
                }


                break;

            default:
                System.out.println("Invalid State");
        }


    }
}
