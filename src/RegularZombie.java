/**
 * <p> <b>RegularZombie </b>, derived from Zombie Class, represents the regular zombies in the simulation. </p>
 * @author Berkant Bayraktar
 * @version 1.0
 */

public class RegularZombie extends  Zombie {
    /**
     * Keeps the number of step Regular Zombie has been in FOLLOWING state
     */
    private int step_count;

    /**
     * <p> Creates RegularZombie object with the given parameters. </p>
     * <p> Name and position of the zombies are given to the parent class's constructor as parameter.</p>
     * <p> Other values which specific to the Regular Zombie type is also given to parent class's constructor as parameter. </p>
     * <p> Values specific to the Regular Zombie type are given below</p>
     * <ul>
     *     <li> {@link ZombieState}: SEARCHING </li>
     *     <li> {@link ZombieType}: REGULAR</li>
     *     <li> collision range of the Zombie: 2.0</li>
     *     <li> detection range of the Zombie: 20.0</li>
     *     <li> speed of the Zombie: 5.0 </li>
     * </ul>
     * @param name Name of the Regular Zombie
     * @param position Position of the Regular Zombie
     */

    public RegularZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 5.0 , ZombieType.REGULAR, ZombieState.WANDERING, 2.0,20.0);
        this.step_count = 0;
    }

    /**
     * Gets the number of step Regular Zombie has been in FOLLOWING state
     * @return the number of step Regular Zombie has been in FOLLOWING state
     */

    public int getStep_count() {
        return step_count;
    }

    /**
     * Sets the number of step Regular Zombie has been in FOLLOWING state
     * @param step_count the number of step Regular Zombie has been in FOLLOWING state
     */

    public void setStep_count(int step_count) {
        this.step_count = step_count;
    }

    /**
     * <p> RegularZombie object uses this method to be able to act in the simulation. </p>
     * <p> RegularZombie object can be in two different state which are {@link ZombieState#WANDERING}, {@link ZombieState#FOLLOWING}  </p>
     * <p> The instructions according to the states are given below.</p>
     * <p> State: WANDERING </p>
     * <ul>
     *     <li> If this is the first step call, selects zombies direction randomly.</li>
     *     <li> Calculates the next position of the zombie with formula: new_position = position + direction * speed </li>
     *     <li> If the position is out of bounds, changes direction to random value. </li>
     *     <li> If the position is not out of bounds, changes zombie position to the new_position. </li>
     *     <li> Calculates the euclidean distance to the closest soldier.</li>
     *     <li> If the distance is shorter than or equal to the detection range of the zombie, changes state to
     * FOLLOWING. </li>
     * </ul>
     * <p> State: FOLLOWING </p>
     * <ul>
     *     <li> Calculates the next position of the zombie with formula: new_position = position + direction * speed </li>
     *     <li> If the position is out of bounds, changes direction to random value. </li>
     *     <li> If the position is not out of bounds, changes zombie position to the new_position. </li>
     *     <li> Counts the number of step zombie has been in FOLLOWING state.</li>
     *     <li> If the count is 4, changes state to WANDERING.</li>
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

        if(this.isFirstStep()){  // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL
            this.setRandomDirection();
            this.setFirstStep(false);
        }

        this.moveOrChangeDirection(controller); // CALCULATE NEXT POSITION AND TRY TO RUN , IF THE POSITION IS OUT OF BOUNDS CHANGE DIRECTION TO RARNDOM VALUE.

        switch (this.getZombie_state()){
            case WANDERING:
                distance = calculateDistanceToEnemy(controller); // CALCULATE THE DISTANCE TO NEAREST SOLDIER
                    if(distance <= this.getDetection_range()){  // IF THE CLOSEST DISTANCE < DETECTION RANGE , CHANGE THE STATE AS FOLLOWING
                        this.turnToEnemy(enemy);
                        this.setZombie_state(ZombieState.FOLLOWING);
                        System.out.println(this.getName() + " changed state to " + this.getZombie_state() + ".");
                }
                break;

            case FOLLOWING:
                this.setStep_count(this.getStep_count()+1);

                if(this.getStep_count() == 4){
                    this.setZombie_state(ZombieState.WANDERING);
                    this.setStep_count(0);
                    System.out.println(this.getName() + " changed state to " + this.getZombie_state() + ".");
                }

                break;
            default:
                System.out.println("Invalid State");
        }


    }
    
}
